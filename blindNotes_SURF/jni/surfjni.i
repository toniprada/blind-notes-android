/* File : example.i */



%module surfjnimodule
%{
/**/
#include "cv.h"
#include "surfjni.h"
%}


%include "arrays_java.i"
%apply int[] {int *};


%include "ipoint.i"

extern IplImage* loadPixels(int* pixels, int width, int height);

class SURFjni{
public:
	SURFjni(int* pixels, int width, int height);
	~SURFjni();


	void surfDetDes(
	bool upright = false, /* run in rotation invariant mode? */
	int octaves = OCTAVES, /* number of octaves to calculate */
	int intervals = INTERVALS, /* number of intervals per octave */
	int init_sample = INIT_SAMPLE, /* initial sampling step */
	float thres = THRES /* blob response threshold */);


	IpVec ipts;

	IplImage* mImage;



};
