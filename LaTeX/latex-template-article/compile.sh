#!/bin/sh

latexmk --pdf main.tex && open main.pdf
./clean.sh
