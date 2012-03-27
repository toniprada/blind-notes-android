#!/bin/bash
swig -java -c++ -package "com.theveganrobot.OpenASURF.swig" \
	-outdir ../src/com/theveganrobot/OpenASURF/swig \
	-o surfjni_wrap.cpp surfjni.i

