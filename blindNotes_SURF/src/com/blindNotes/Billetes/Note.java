package com.blindNotes.Billetes;

import java.io.IOException;
import java.io.Serializable;

import android.util.Log;

import com.theveganrobot.OpenASURF.swig.Ipoint;
import com.theveganrobot.OpenASURF.swig.IpointVector;

public class Note implements Serializable {

	private static final long serialVersionUID = 1208263514901494728L;
	
	private static final String TAG = Util.TAG;
	private String name;
	private int value;
	private int resourceId;
	private IpointVector iptv;

	public Note(String name, int value, int resourceId) {
		this.name = name;
		this.value = value;
		this.resourceId = resourceId;
		iptv = null;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(name);
		out.writeInt(resourceId);
		out.writeInt(value);
		if (iptv == null) {
			// No POI vector
			out.writeInt(0);
			return;
		}
		long vectorSize = iptv.size();
		out.writeLong(vectorSize);
		Log.d(TAG, "Written vector size: " + vectorSize);
		for (int j = 0; j < vectorSize; j++) {
			Ipoint p = iptv.get(j);
			out.writeFloat(p.getX());
			out.writeFloat(p.getY());
			out.writeFloat(p.getScale());
			out.writeFloat(p.getOrientation());
			out.writeInt(p.getLaplacian());
			out.writeFloat(p.getDx());
			out.writeFloat(p.getDy());
			out.writeInt(p.getClusterIndex());
			out.writeObject(p.getDescriptor());
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		name = (String) in.readObject();
		Log.d(TAG, "Read name: " + name);
		resourceId = in.readInt();
		value = in.readInt();
		long vectorSize = in.readLong();
		if (vectorSize <= 0) {
			return;
		}
		IpointVector ipv = new IpointVector(vectorSize);
		Log.d(TAG, "VectorSize: " + vectorSize);
		for (int j = 0; j < vectorSize; j++) {
			Ipoint p = new Ipoint();
			p.setX(in.readFloat());
			p.setY(in.readFloat());
			p.setScale(in.readFloat());
			p.setOrientation(in.readFloat());
			p.setLaplacian(in.readInt());
			p.setDx(in.readFloat());
			p.setDy(in.readFloat());
			p.setClusterIndex(in.readInt());
			p.setDescriptor((float[]) in.readObject());
			ipv.add(p);
		}
		setIptv(ipv);
	}

	public void setIptv(IpointVector iptv) {
		this.iptv = iptv;
	}

	public IpointVector getIptv() {
		return iptv;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the res_id
	 */
	public int getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId
	 *            the res_id to set
	 */
	public void setRes_id(int resourceId) {
		this.resourceId = resourceId;
	}

}
