/* File : ipoint.i */

%{
/**/
#include "ipoint.h"
%}



%include std_vector.i
%include std_pair.i



typedef std::vector<Ipoint> IpVec;
typedef std::pair<Ipoint, Ipoint> IpPair;
typedef std::vector<IpPair > IpPairVec;

%typemap(javapackage)  std::vector<Ipoint>  "com.theveganrobot.OpenASURF.swig"
%typemap(javapackage)  std::vector<std::pair<Ipoint, Ipoint> >  "com.theveganrobot.OpenASURF.swig"

%template(IpointVector) std::vector<Ipoint>;
%template(IpPairVector) std::vector<IpPair >;
%template(IpPair) std::pair<Ipoint,Ipoint>;
typedef struct CvPoint
{
    int x;
    int y;
}
CvPoint;





%include "carrays.i"
%array_functions(CvPoint, cvpointArray);


//-------------------------------------------------------

//! Ipoint operations
void getMatches(IpVec &ipts1, IpVec &ipts2, IpPairVec &matches);
int translateCorners(IpPairVec &matches, const CvPoint* src_corners, CvPoint * dst_corners);


class Ipoint {

public:


	static int GetIpVecSize(const IpVec & vec);

	static const Ipoint &GetIpointAt(const IpVec & vec, int index);
	
  //! Destructor
  ~Ipoint() {};

  //! Constructor
  Ipoint() : orientation(0) {};


  //! Coordinates of the detected interest point
  float x, y;

  //! Detected scale
  float scale;

  //! Orientation measured anti-clockwise from +ve x-axis
  float orientation;

  //! Sign of laplacian for fast matching purposes
  int laplacian;

  //! Vector of descriptor components
  float descriptor[64];

  //! Placeholds for point motion (can be used for frame to frame motion analysis)
  float dx, dy;

  //! Used to store cluster index
  int clusterIndex;
};


