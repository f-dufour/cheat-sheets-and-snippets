#!/bin/sh

latexmk -c
find . -type f -name "*eps-converted-to.pdf" -delete
find . -type f -name ".DS_Store" -delete
rm -v *.bbl *dvi *.pdf *.xml
