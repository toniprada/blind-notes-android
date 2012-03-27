LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)

APP_CPPFLAGS += -fexceptions
APP_STL := stlport_shared
APP_CPPFLAGS += -frtti

LOCAL_MODULE := OpenSURF

LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -L/home/fer/code/notes/OpenCV-Android/obj/local/armeabi -lopencv -lcxcore -lcv -lcvaux -lcvml -lcvhighgui \
				-llog 



OPENCV := /home/fer/code/notes/OpenCV-Android/jni/

LOCAL_C_INCLUDES += \
        $(OPENCV)/cv/src \
        $(OPENCV)/cv/include \
        $(OPENCV)/cxcore/include \
        $(OPENCV)/cvaux/src \
        $(OPENCV)/cvaux/include \
        $(OPENCV)/ml/include \
        $(OPENCV)/otherlibs/highgui \
        $(OPENCV)
        
LOCAL_CFLAGS += -fpic 
MY_SRCS += fasthessian.cpp  integral.cpp  ipoint.cpp \
			 surf.cpp surfutils.cpp surfjni.cpp surfjni_wrap.cpp

LOCAL_SRC_FILES := $(MY_SRCS)



include $(BUILD_SHARED_LIBRARY)
