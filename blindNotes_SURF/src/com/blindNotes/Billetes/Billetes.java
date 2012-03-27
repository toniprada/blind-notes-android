package com.blindNotes.Billetes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.blindNotes.Billetes.SurfLib.SurfInfo;
import com.theveganrobot.OpenASURF.swig.IpPairVector;
import com.theveganrobot.OpenASURF.swig.IpointVector;
import com.theveganrobot.OpenASURF.swig.surfjnimodule;

public class Billetes extends Activity {

	private static final String TAG = Util.TAG;
	private static final long DOUBLE_CLICK_MS = 500;
	private static final boolean FLASH_ENABLED = true;
	private static final String TTS_LANGUAGE = "spa";
	private static final String DIR = "notes";
	
	private static final int SURF_PROGRESS_BAR_WRITE = 1;
	private static final int SURF_PROGRESS_BAR_READ = 2;
	private static final int SURF_PROGRESS_BAR_POINTS = 3;
	private static final int SURF_PROGRESS_BAR_MATCHES = 4;
	public static final int SURF_INTERRUPTED = 5;

	private Context mContext;
	private Preview preview;
	private TextToSpeech mTts = null;
	private boolean mTtsOk = false;
	private Camera camera;
	private boolean inCamera;
	private long lastClick = 0;
	private Note mNotes[] = { new Note("5 Euros", 5, R.drawable.e5),
			new Note("5 Euros r", 5, R.drawable.e5r),
			new Note("10 Euros", 10, R.drawable.e10),
			new Note("10 Euros r", 10, R.drawable.e10r),
			new Note("20 Euros", 20, R.drawable.e20),
			new Note("20 Euros r", 20, R.drawable.e20r),
			new Note("50 Euros", 50, R.drawable.e50),
			new Note("50 Euros r", 50, R.drawable.e50r) };

	static {
		try {
			System.loadLibrary("opencv");
			System.loadLibrary("OpenSURF");
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		if (mTts == null) {
			// We should do some checks before
			mTts = new TextToSpeech(getBaseContext(), new OnInitListener() {
				@Override
				public void onInit(int status) {
					mTtsOk = true;
				}
			});
			mTts.setLanguage(new Locale(TTS_LANGUAGE));
		}
		if (notesDirReadable()) {
			showDialog(SURF_PROGRESS_BAR_READ);
			new ReadPoints().execute(0);
		} else if (notesDirWritable()) {
			showDialog(SURF_PROGRESS_BAR_WRITE);
			new WritePoints().execute(0);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case SURF_PROGRESS_BAR_WRITE:
			return ProgressDialog.show(mContext, getText(R.string.please_wait),
					getText(R.string.writing_descriptors), true);
		case SURF_PROGRESS_BAR_READ:
			return ProgressDialog.show(mContext, getText(R.string.please_wait),
					getText(R.string.reading_descriptors), true);
		case SURF_INTERRUPTED: {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getText(R.string.interrupted))
					.setCancelable(false)
					.setPositiveButton(getText(R.string.sorry),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
								}
							});
			AlertDialog alert = builder.create();
			return alert;
		}
		case SURF_PROGRESS_BAR_POINTS:
			return ProgressDialog.show(mContext, getText(R.string.please_wait),
					 getText(R.string.calculating), true);
		case SURF_PROGRESS_BAR_MATCHES:
			return ProgressDialog.show(mContext, getText(R.string.please_wait),
					getText(R.string.checking), true);
		default:
			return super.onCreateDialog(id);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mTts != null) {
			mTts.shutdown();
		}
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	boolean notesDirWritable() {
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state))
			return false;
		File file = new File(getExternalFilesDir(null), DIR);
		if (file.exists() && !file.canWrite())
			return false;
		if (!file.exists())
			file.mkdir();
		return true;
	}

	boolean notesDirReadable() {
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)
				&& !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return false;
		}
		File file = new File(getExternalFilesDir(null), DIR);
		if (file.exists() && file.canRead())
			return true;
		return false;
	}


	public void startCapture() {
		this.inCamera = true;
		camera = Camera.open();
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setRotation(90);
		if (FLASH_ENABLED) {
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
		}
		List<Size> sizes = parameters.getSupportedPictureSizes();
		camera.setDisplayOrientation(90);
		
		Size s = sizes.get(sizes.size() - 1);

		parameters.setPictureSize(s.width, s.height);
		camera.setParameters(parameters);
		preview = new Preview(getBaseContext(), camera);
		setContentView(preview);
		preview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				long nowClick = new Date().getTime();
				if (nowClick - lastClick < DOUBLE_CLICK_MS) {
					Log.i(TAG, "Double click");
					lastClick = 0;
					camera.takePicture(null, null, new JpegPictureCallBack());
				} else {
					Log.i(TAG, "Single click");
					lastClick = nowClick;
				}
				inCamera = false;
			}
		});

	}

	private class JpegPictureCallBack implements Camera.PictureCallback {
		public void onPictureTaken(byte[] data, Camera camera) {
			gotCapture(data);
		}
	};

	public void gotCapture(byte data[]) {
		setContentView(R.layout.result);
		showDialog(SURF_PROGRESS_BAR_MATCHES);
		preview = null;
		Log.i(TAG, "Got image with size: " + data.length);
		System.gc();
		Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
		camera.release();
		// ImageView image1 = (ImageView) findViewById(R.id.imageView1);
		// image1.setImageBitmap(bmp);
		new Correspond().execute(bmp);
	}

	public void speak(String text) {
		if (mTtsOk) {
			mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	private class WritePoints extends AsyncTask<Integer, Integer, Boolean> {

		private SurfLib surflib = new SurfLib();

		protected Boolean doInBackground(Integer... id) {
			for (Note n : mNotes) {
				@SuppressWarnings("unused")
				SurfInfo info = surflib.surfify(n.getResourceId(),
						Billetes.this, false);
				// Workaround to the serialization problem with IpointVector
				IpointVector ipv = surflib.getIpts(n.getResourceId());
				n.setIptv(ipv);
				String path = new String("notes/" + n.getResourceId() + ".dsc");
				try {
					File file = new File(getExternalFilesDir(null), path);
					if (file.exists())
						file.delete();
					if (!file.createNewFile()) {
						Log.w(TAG, "Cannot create file " + path);
						continue;
					}
					ObjectOutputStream out = new ObjectOutputStream(
							new FileOutputStream(file));
					out.writeObject(n);
					out.flush();
					out.close();
				} catch (IOException e) {
					Log.w(TAG, "Error writing " + path, e);
				}
				Log.v(TAG, "File " + path + "written");
				// Don't keep a copy of SurfInfo in memory.
				surflib.remove(n.getResourceId());
			}
			return true;
		}

		@Override
		protected void onCancelled() {
			dismissDialog(SURF_PROGRESS_BAR_WRITE);
			showDialog(SURF_INTERRUPTED);
			super.onCancelled();
		}

		protected void onPostExecute(Boolean result) {
			if (result == false) {
				Log.v(TAG, "Cannot write pre calculated points file");
				finish();
			}
			dismissDialog(SURF_PROGRESS_BAR_WRITE);
			finish();
		}
	}
	
	/**/

	private class ReadPoints extends AsyncTask<Integer, Integer, Note[]> {

		protected Note[] doInBackground(Integer... id) {
			File dir = new File(getExternalFilesDir(null), "notes");
			File files[] = dir.listFiles();
			Note[] notes = new Note[files.length];
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				try {
					ObjectInputStream in = new ObjectInputStream(
							new FileInputStream(f));
					Note n = (Note) in.readObject();
					in.close();
					notes[i] = n;
				} catch (IOException e) {
					Log.w(TAG, "Error reading " + f, e);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			return notes;
		}

		protected void onProgressUpdate(Integer... progress) {
		}

		@Override
		protected void onCancelled() {
			dismissDialog(SURF_PROGRESS_BAR_READ);
			showDialog(SURF_INTERRUPTED);
			super.onCancelled();
		}

		protected void onPostExecute(Note[] result) {
			if (result == null) {
				Log.v(TAG, "Cannot open pre calculated points file");
				finish();
			}
			mNotes = result;
			dismissDialog(SURF_PROGRESS_BAR_READ);
			startCapture();
		}
	}

	private class Correspond extends AsyncTask<Bitmap, Integer, Note> {

		protected Note doInBackground(Bitmap... bmp) {
			SurfLib surflib = new SurfLib();
			SurfInfo capture1Info = surflib.surfify(bmp[0], 1, Billetes.this,
					false);
			IpointVector ipts1 = surflib.getIpts(capture1Info.id);

			Note bestMatchNote = null;
			long bestMatchCount = 0;

			for (Note n : mNotes) {
				IpointVector ipv = n.getIptv();
				IpPairVector matches = new IpPairVector();
				surfjnimodule.getMatches(ipv, ipts1, matches);
				Log.d(TAG,
						"Matches for note " + n.getName() + ": "
								+ matches.size());
				if (matches.size() > bestMatchCount) {
					bestMatchCount = matches.size();
					bestMatchNote = n;
					;
				} else if (matches.size() == bestMatchCount) {
					// No se puede distinguir entre varios
					bestMatchNote = null;
				}
				publishProgress(n.getResourceId(), (int) matches.size());
			}
			return bestMatchNote;
		}

		protected void onProgressUpdate(Integer... id) {
			// image2.setImageResource(id[0]);
			// text.setText(id[1].toString());
		}

		@Override
		protected void onCancelled() {
			dismissDialog(SURF_PROGRESS_BAR_MATCHES);
			showDialog(SURF_INTERRUPTED);
			super.onCancelled();
		}

		protected void onPostExecute(Note result) {
			TextView tv = (TextView) findViewById(R.id.textValue);
			tv.setTextSize(100);
			if (result != null) {
				// image2.setImageResource(result.res_id);
				// text.setText(result.name);
				Log.i(TAG, "Result: " + result.getName());
				speak(result.getName());
				tv.setText("" + result.getValue() + "Û");
			} else {
				Log.i(TAG, "Result: unknown");
				tv.setText("?");
			}
			dismissDialog(SURF_PROGRESS_BAR_MATCHES);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startCapture();

				}
			});
			// startCapture();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        // do something on back.
	    	if(this.inCamera==false){
	    		this.startCapture();
	    	}else{
	    		finish();
	    	}
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
