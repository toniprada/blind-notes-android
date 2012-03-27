/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.theveganrobot.OpenASURF.swig;

public class surfjnimoduleJNI {
	public final static native long new_IpointVector__SWIG_0();

	public final static native long new_IpointVector__SWIG_1(long jarg1);

	public final static native long IpointVector_size(long jarg1,
			IpointVector jarg1_);

	public final static native long IpointVector_capacity(long jarg1,
			IpointVector jarg1_);

	public final static native void IpointVector_reserve(long jarg1,
			IpointVector jarg1_, long jarg2);

	public final static native boolean IpointVector_isEmpty(long jarg1,
			IpointVector jarg1_);

	public final static native void IpointVector_clear(long jarg1,
			IpointVector jarg1_);

	public final static native void IpointVector_add(long jarg1,
			IpointVector jarg1_, long jarg2, Ipoint jarg2_);

	public final static native long IpointVector_get(long jarg1,
			IpointVector jarg1_, int jarg2);

	public final static native void IpointVector_set(long jarg1,
			IpointVector jarg1_, int jarg2, long jarg3, Ipoint jarg3_);

	public final static native void delete_IpointVector(long jarg1);

	public final static native long new_IpPairVector__SWIG_0();

	public final static native long new_IpPairVector__SWIG_1(long jarg1);

	public final static native long IpPairVector_size(long jarg1,
			IpPairVector jarg1_);

	public final static native long IpPairVector_capacity(long jarg1,
			IpPairVector jarg1_);

	public final static native void IpPairVector_reserve(long jarg1,
			IpPairVector jarg1_, long jarg2);

	public final static native boolean IpPairVector_isEmpty(long jarg1,
			IpPairVector jarg1_);

	public final static native void IpPairVector_clear(long jarg1,
			IpPairVector jarg1_);

	public final static native void IpPairVector_add(long jarg1,
			IpPairVector jarg1_, long jarg2, IpPair jarg2_);

	public final static native long IpPairVector_get(long jarg1,
			IpPairVector jarg1_, int jarg2);

	public final static native void IpPairVector_set(long jarg1,
			IpPairVector jarg1_, int jarg2, long jarg3, IpPair jarg3_);

	public final static native void delete_IpPairVector(long jarg1);

	public final static native long new_IpPair__SWIG_0();

	public final static native long new_IpPair__SWIG_1(long jarg1,
			Ipoint jarg1_, long jarg2, Ipoint jarg2_);

	public final static native long new_IpPair__SWIG_2(long jarg1, IpPair jarg1_);

	public final static native void IpPair_first_set(long jarg1, IpPair jarg1_,
			long jarg2, Ipoint jarg2_);

	public final static native long IpPair_first_get(long jarg1, IpPair jarg1_);

	public final static native void IpPair_second_set(long jarg1,
			IpPair jarg1_, long jarg2, Ipoint jarg2_);

	public final static native long IpPair_second_get(long jarg1, IpPair jarg1_);

	public final static native void delete_IpPair(long jarg1);

	public final static native void CvPoint_x_set(long jarg1, CvPoint jarg1_,
			int jarg2);

	public final static native int CvPoint_x_get(long jarg1, CvPoint jarg1_);

	public final static native void CvPoint_y_set(long jarg1, CvPoint jarg1_,
			int jarg2);

	public final static native int CvPoint_y_get(long jarg1, CvPoint jarg1_);

	public final static native long new_CvPoint();

	public final static native void delete_CvPoint(long jarg1);

	public final static native long new_cvpointArray(int jarg1);

	public final static native void delete_cvpointArray(long jarg1,
			CvPoint jarg1_);

	public final static native long cvpointArray_getitem(long jarg1,
			CvPoint jarg1_, int jarg2);

	public final static native void cvpointArray_setitem(long jarg1,
			CvPoint jarg1_, int jarg2, long jarg3, CvPoint jarg3_);

	public final static native void getMatches(long jarg1, IpointVector jarg1_,
			long jarg2, IpointVector jarg2_, long jarg3, IpPairVector jarg3_);

	public final static native int translateCorners(long jarg1,
			IpPairVector jarg1_, long jarg2, CvPoint jarg2_, long jarg3,
			CvPoint jarg3_);

	public final static native int Ipoint_GetIpVecSize(long jarg1,
			IpointVector jarg1_);

	public final static native long Ipoint_GetIpointAt(long jarg1,
			IpointVector jarg1_, int jarg2);

	public final static native void delete_Ipoint(long jarg1);

	public final static native long new_Ipoint();

	public final static native void Ipoint_x_set(long jarg1, Ipoint jarg1_,
			float jarg2);

	public final static native float Ipoint_x_get(long jarg1, Ipoint jarg1_);

	public final static native void Ipoint_y_set(long jarg1, Ipoint jarg1_,
			float jarg2);

	public final static native float Ipoint_y_get(long jarg1, Ipoint jarg1_);

	public final static native void Ipoint_scale_set(long jarg1, Ipoint jarg1_,
			float jarg2);

	public final static native float Ipoint_scale_get(long jarg1, Ipoint jarg1_);

	public final static native void Ipoint_orientation_set(long jarg1,
			Ipoint jarg1_, float jarg2);

	public final static native float Ipoint_orientation_get(long jarg1,
			Ipoint jarg1_);

	public final static native void Ipoint_laplacian_set(long jarg1,
			Ipoint jarg1_, int jarg2);

	public final static native int Ipoint_laplacian_get(long jarg1,
			Ipoint jarg1_);

	public final static native void Ipoint_descriptor_set(long jarg1,
			Ipoint jarg1_, float[] jarg2);

	public final static native float[] Ipoint_descriptor_get(long jarg1,
			Ipoint jarg1_);

	public final static native void Ipoint_dx_set(long jarg1, Ipoint jarg1_,
			float jarg2);

	public final static native float Ipoint_dx_get(long jarg1, Ipoint jarg1_);

	public final static native void Ipoint_dy_set(long jarg1, Ipoint jarg1_,
			float jarg2);

	public final static native float Ipoint_dy_get(long jarg1, Ipoint jarg1_);

	public final static native void Ipoint_clusterIndex_set(long jarg1,
			Ipoint jarg1_, int jarg2);

	public final static native int Ipoint_clusterIndex_get(long jarg1,
			Ipoint jarg1_);

	public final static native long loadPixels(int[] jarg1, int jarg2, int jarg3);

	public final static native long new_SURFjni(int[] jarg1, int jarg2,
			int jarg3);

	public final static native void delete_SURFjni(long jarg1);

	public final static native void SURFjni_surfDetDes__SWIG_0(long jarg1,
			SURFjni jarg1_, boolean jarg2, int jarg3, int jarg4, int jarg5,
			float jarg6);

	public final static native void SURFjni_surfDetDes__SWIG_1(long jarg1,
			SURFjni jarg1_, boolean jarg2, int jarg3, int jarg4, int jarg5);

	public final static native void SURFjni_surfDetDes__SWIG_2(long jarg1,
			SURFjni jarg1_, boolean jarg2, int jarg3, int jarg4);

	public final static native void SURFjni_surfDetDes__SWIG_3(long jarg1,
			SURFjni jarg1_, boolean jarg2, int jarg3);

	public final static native void SURFjni_surfDetDes__SWIG_4(long jarg1,
			SURFjni jarg1_, boolean jarg2);

	public final static native void SURFjni_surfDetDes__SWIG_5(long jarg1,
			SURFjni jarg1_);

	public final static native void SURFjni_ipts_set(long jarg1,
			SURFjni jarg1_, long jarg2, IpointVector jarg2_);

	public final static native long SURFjni_ipts_get(long jarg1, SURFjni jarg1_);

	public final static native void SURFjni_mImage_set(long jarg1,
			SURFjni jarg1_, long jarg2);

	public final static native long SURFjni_mImage_get(long jarg1,
			SURFjni jarg1_);
}