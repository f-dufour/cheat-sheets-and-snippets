#!/bin/bash

mkdir subtitles;

for f in *.mkv;
	do echo $f;
	ffmpeg -i $f -map 0:2 copy ${f}.srt
done
