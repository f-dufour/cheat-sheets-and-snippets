#!/bin/bash

for f in *.mp3;
  do ffmpeg -y -i "$f" -i art.jpg -map 0:0 -map 1:0 -c copy -id3v2_version 3 -metadata:s:v title="Album cover" -metadata:s:v comment="Cover (Front)" "$f";
done
