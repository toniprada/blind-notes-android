/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

#include "surflib.h"
#include "kmeans.h"

#include <jni.h>
#include <iostream>
#include <stdexcept>
#include <string>
#include <android/log.h>

#include <ctime>
#include <cstdio>
#include <sstream>

#include "surfjni.h"

#define LOGV(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
//ANDROID_LOG_UNKNOWN, ANDROID_LOG_DEFAULT, ANDROID_LOG_VERBOSE, ANDROID_LOG_DEBUG, ANDROID_LOG_INFO, ANDROID_LOG_WARN, ANDROID_LOG_ERROR, ANDROID_LOG_FATAL, ANDROID_LOG_SILENT
//LOGV(ANDROID_LOG_DEBUG, "JNI", "");

#define ANDROID_LOG_VERBOSE ANDROID_LOG_DEBUG
#define LOG_TAG "CVJNI"
#define INVALID_ARGUMENT -18456

#define IMAGE( i, x, y, n )   *(( unsigned char * )(( i )->imageData      \
                                    + ( x ) * sizeof( unsigned char ) * 3 \
                                    + ( y ) * ( i )->widthStep ) + ( n ))

IplImage *m_sourceImage = 0;

IplImage* loadPixels(int* pixels, int width, int height) {

	int x, y;
	IplImage *img = cvCreateImage(cvSize(width, height), IPL_DEPTH_8U, 3);

	for (y = 0; y < height; y++) {
		for (x = 0; x < width; x++) {
			// blue
			IMAGE( img, x, y, 0 ) = pixels[x + y * width] & 0xFF;
			// green
			IMAGE( img, x, y, 1 ) = pixels[x + y * width] >> 8 & 0xFF;
			// red
			IMAGE( img, x, y, 2 ) = pixels[x + y * width] >> 16 & 0xFF;
		}
	}

	return img;
}
// Given an integer array of image data, load an IplImage.
// It is the responsibility of the caller to release the IplImage.
IplImage* getIplImageFromIntArray(JNIEnv* env, jintArray array_data,
		jint width, jint height) {
	// Load Image
	int *pixels = env->GetIntArrayElements(array_data, 0);
	if (pixels == 0) {
		LOGE("Error getting int array of pixels.");
		return 0;
	}

	IplImage *image = loadPixels(pixels, width, height);
	env->ReleaseIntArrayElements(array_data, pixels, 0);
	if (image == 0) {
		LOGE("Error loading pixel array.");
		return 0;
	}

	return image;
}

// Set the source image and return true if successful or false otherwise.
extern "C"
JNIEXPORT jboolean
JNICALL Java_com_theveganrobot_OpenASURF_OpenSURF_setSourceImage(JNIEnv* env,
		jobject thiz, jintArray photo_data, jint width, jint height) {
	// Release the image if it hasn't already been released.
	if (m_sourceImage) {
		cvReleaseImage(&m_sourceImage);
		m_sourceImage = 0;
	}

	m_sourceImage = getIplImageFromIntArray(env, photo_data, width, height);
	if (m_sourceImage == 0) {
		LOGE("Error source image could not be created.");
		return false;
	}

	// Declare Ipoints and other stuff
	IpVec ipts;

	// Detect and describe interest points in the image
	clock_t start = clock();
	surfDetDes(m_sourceImage, ipts, false, 4, 4, 2, 0.0001f);
	clock_t end = clock();

	char buffer[256];
	std::sprintf(buffer, "Points found: %d\nTotal Time: %f", ipts.size(),
			float(end - start) / CLOCKS_PER_SEC);
	//std::ostringstream temp;
	//temp << "OpenSURF found: " << ipts.size() << " interest points\n" << " OpenSURF took: " << float(end - start) / CLOCKS_PER_SEC << " seconds";
	LOGV(buffer);

	// // Draw the detected points
	//  drawIpoints(img, ipts);

	// Display the result
	// showImage(img);
	return true;
}

SURFjni::SURFjni(int* pixels, int width, int height) :
	mImage(loadPixels(pixels, width, height)) {
}
;
SURFjni::~SURFjni() {
	cvReleaseImage(&mImage);
}

void SURFjni::surfDetDes(bool upright,
int octaves,
int intervals,
int init_sample,
float thres) {
	// Detect and describe interest points in the image
	clock_t start = clock();
	::surfDetDes(mImage, ipts, upright, octaves, intervals, init_sample, thres);
	clock_t end = clock();

	char buffer[256];
	std::sprintf(buffer, "Points found: %d Total Time: %f", ipts.size(),
			float(end - start) / CLOCKS_PER_SEC);

	LOGV(buffer);

}
