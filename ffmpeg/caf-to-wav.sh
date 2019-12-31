#!/bin/bash

mkdir wav-converted;

for f in *.caf;
  do ffmpeg -i "$f" -ac 1 "wav-converted/${f%.*}.wav";
done
