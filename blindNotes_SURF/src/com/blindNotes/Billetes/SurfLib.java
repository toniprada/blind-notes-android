package com.blindNotes.Billetes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.theveganrobot.OpenASURF.swig.IpPairVector;
import com.theveganrobot.OpenASURF.swig.Ipoint;
import com.theveganrobot.OpenASURF.swig.IpointVector;
import com.theveganrobot.OpenASURF.swig.SURFjni;
import com.theveganrobot.OpenASURF.swig.surfjnimodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.util.Log;

public class SurfLib {

	public static class SurfInfo {
		public SURFjni surf;

		public Bitmap orignalBitmap;
		public Bitmap outputBitmap;
		public int id;

		public SurfInfo(SURFjni surf, Bitmap bitmap, int id) {
			this.surf = surf;
			this.orignalBitmap = bitmap;
			this.id = id;
		}
	}

	private HashMap<Integer, SurfInfo> surfmap;

	public SurfLib() {
		surfmap = new HashMap<Integer, SurfInfo>();
	}

	public SurfInfo getSurfInfo(int rid) {
		return surfmap.get(rid);
	}

	public static void DrawSurfPoints(SurfInfo surfinfo, int which,
			IpPairVector matches) {

		IpointVector points = surfinfo.surf.getIpts();

		surfinfo.outputBitmap = surfinfo.orignalBitmap.copy(Config.RGB_565,
				true);
		Canvas canvas = new Canvas(surfinfo.outputBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(2);
		paint.setStyle(Style.STROKE);

		Paint orientpaint = new Paint();
		orientpaint.setAntiAlias(true);
		orientpaint.setColor(Color.BLUE);
		orientpaint.setStrokeWidth(2);
		orientpaint.setStyle(Style.STROKE);

		Paint dxdypaint = new Paint();
		dxdypaint.setAntiAlias(true);
		dxdypaint.setColor(Color.YELLOW);
		dxdypaint.setStrokeWidth(2);
		dxdypaint.setStyle(Style.STROKE);

		Ipoint point = null;
		for (int i = 0; i < points.size(); i++) {

			point = points.get(i);

			float orientation = point.getOrientation();

			canvas.drawLine(
					point.getX(),
					point.getY(),
					point.getX() + (float) Math.cos(orientation)
							* point.getScale(),
					point.getY() + (float) Math.sin(orientation)
							* point.getScale(), orientpaint);
			canvas.drawCircle(point.getX(), point.getY(), point.getScale(),
					paint);

		}

		if (matches != null) {
			for (int i = 0; i < matches.size(); i++) {
				point = which == 1 ? matches.get(i).getSecond() : matches
						.get(i).getFirst();
				canvas.drawLine(point.getX(), point.getY(), point.getX()
						+ point.getDx(), point.getY() + point.getDy(),
						dxdypaint);
			}
		}

	}

	public IpointVector getIpts(int id) {
		return surfmap.get(id).surf.getIpts();
	}

	public IpPairVector findMatchs(int id1, int id2) {

		if (!surfmap.containsKey(id1) || !surfmap.containsKey(id2)) {
			return null;
		}
		IpointVector ipts1 = surfmap.get(id1).surf.getIpts();
		IpointVector ipts2 = surfmap.get(id2).surf.getIpts();
		IpPairVector matches = new IpPairVector();

		surfjnimodule.getMatches(ipts1, ipts2, matches);
		return matches;
	}

	public SurfInfo surfify(int rid, Context ctx) {
		return surfify(rid, ctx, true);
	}

	public SurfInfo surfify(int rid, Context ctx, boolean draw) {
		Bitmap bmp = loadBitmapFromResource(rid, ctx);
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width * height];

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);

		SURFjni surf = new SURFjni(pixels, width, height);
		// surf.surfDetDes();
		surf.surfDetDes(false, 4, 4, 2, 0.0001f);

		SurfInfo info = new SurfInfo(surf, bmp, rid);

		if (draw)
			DrawSurfPoints(info, 0, null);

		surfmap.put(rid, info);
		return info;

	}

	// FIXME: refactor these two methods
	public SurfInfo surfify(Bitmap bmp, int rid, Context ctx, boolean draw) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width * height];

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);

		SURFjni surf = new SURFjni(pixels, width, height);
		// surf.surfDetDes();
		surf.surfDetDes(false, 4, 4, 2, 0.0002f);

		SurfInfo info = new SurfInfo(surf, bmp, rid);

		if (draw)
			DrawSurfPoints(info, 0, null);

		surfmap.put(rid, info);
		return info;

	}

	public void remove(int rid) {
		surfmap.remove(rid);
	}

	public Bitmap loadBitmapFromResource(int resourceId, Context ctx) {

		BitmapFactory.Options ops = new BitmapFactory.Options();

		InputStream is = ctx.getResources().openRawResource(resourceId);
		Bitmap bitmap = loadBitmapFromStream(is, ops);
		return bitmap;
	}

	public static final Bitmap loadBitmapFromStream(InputStream stream,
			BitmapFactory.Options ops) {
		Bitmap bitmap = null;

		try {

			bitmap = BitmapFactory.decodeStream(stream, null, ops);

			stream.close();

		} catch (IOException e) {
			Log.e("bad file read", e.toString());
		}

		return bitmap;

	}

}
