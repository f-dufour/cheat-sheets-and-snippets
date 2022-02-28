#!/bin/bash

mkdir output;

for f in *.flac;
  do ffmpeg -i "$f" -ab 320k -map_metadata 0 -id3v2_version 3 "output/${f%.*}.mp3";
done
