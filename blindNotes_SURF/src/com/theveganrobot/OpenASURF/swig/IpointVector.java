/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.theveganrobot.OpenASURF.swig;

public class IpointVector {
	private long swigCPtr;
	protected boolean swigCMemOwn;

	public IpointVector(long cPtr, boolean cMemoryOwn) {
		swigCMemOwn = cMemoryOwn;
		swigCPtr = cPtr;
	}

	public static long getCPtr(IpointVector obj) {
		return (obj == null) ? 0 : obj.swigCPtr;
	}

	protected void finalize() {
		delete();
	}

	public synchronized void delete() {
		if (swigCPtr != 0) {
			if (swigCMemOwn) {
				swigCMemOwn = false;
				surfjnimoduleJNI.delete_IpointVector(swigCPtr);
			}
			swigCPtr = 0;
		}
	}

	public IpointVector() {
		this(surfjnimoduleJNI.new_IpointVector__SWIG_0(), true);
	}

	public IpointVector(long n) {
		this(surfjnimoduleJNI.new_IpointVector__SWIG_1(n), true);
	}

	public long size() {
		return surfjnimoduleJNI.IpointVector_size(swigCPtr, this);
	}

	public long capacity() {
		return surfjnimoduleJNI.IpointVector_capacity(swigCPtr, this);
	}

	public void reserve(long n) {
		surfjnimoduleJNI.IpointVector_reserve(swigCPtr, this, n);
	}

	public boolean isEmpty() {
		return surfjnimoduleJNI.IpointVector_isEmpty(swigCPtr, this);
	}

	public void clear() {
		surfjnimoduleJNI.IpointVector_clear(swigCPtr, this);
	}

	public void add(Ipoint x) {
		surfjnimoduleJNI.IpointVector_add(swigCPtr, this, Ipoint.getCPtr(x), x);
	}

	public Ipoint get(int i) {
		return new Ipoint(surfjnimoduleJNI.IpointVector_get(swigCPtr, this, i),
				false);
	}

	public void set(int i, Ipoint val) {
		surfjnimoduleJNI.IpointVector_set(swigCPtr, this, i,
				Ipoint.getCPtr(val), val);
	}

}
