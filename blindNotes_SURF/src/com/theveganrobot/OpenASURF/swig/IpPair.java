/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.theveganrobot.OpenASURF.swig;

public class IpPair {
	private long swigCPtr;
	protected boolean swigCMemOwn;

	public IpPair(long cPtr, boolean cMemoryOwn) {
		swigCMemOwn = cMemoryOwn;
		swigCPtr = cPtr;
	}

	public static long getCPtr(IpPair obj) {
		return (obj == null) ? 0 : obj.swigCPtr;
	}

	protected void finalize() {
		delete();
	}

	public synchronized void delete() {
		if (swigCPtr != 0) {
			if (swigCMemOwn) {
				swigCMemOwn = false;
				surfjnimoduleJNI.delete_IpPair(swigCPtr);
			}
			swigCPtr = 0;
		}
	}

	public IpPair() {
		this(surfjnimoduleJNI.new_IpPair__SWIG_0(), true);
	}

	public IpPair(Ipoint first, Ipoint second) {
		this(surfjnimoduleJNI.new_IpPair__SWIG_1(Ipoint.getCPtr(first), first,
				Ipoint.getCPtr(second), second), true);
	}

	public IpPair(IpPair p) {
		this(surfjnimoduleJNI.new_IpPair__SWIG_2(IpPair.getCPtr(p), p), true);
	}

	public void setFirst(Ipoint value) {
		surfjnimoduleJNI.IpPair_first_set(swigCPtr, this,
				Ipoint.getCPtr(value), value);
	}

	public Ipoint getFirst() {
		long cPtr = surfjnimoduleJNI.IpPair_first_get(swigCPtr, this);
		return (cPtr == 0) ? null : new Ipoint(cPtr, false);
	}

	public void setSecond(Ipoint value) {
		surfjnimoduleJNI.IpPair_second_set(swigCPtr, this,
				Ipoint.getCPtr(value), value);
	}

	public Ipoint getSecond() {
		long cPtr = surfjnimoduleJNI.IpPair_second_get(swigCPtr, this);
		return (cPtr == 0) ? null : new Ipoint(cPtr, false);
	}

}
