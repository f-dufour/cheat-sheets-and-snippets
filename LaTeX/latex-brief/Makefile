# Makefile - LaTeX brief
# Florent Dufour
# 2019 - 2020

MAIN=brief.tex
BODY=body.tex
OPT=sender.lco
LATEX_ENGINE=pdflatex

pdf: $(MAIN) $(OPT) $(BODY)		## Compile to PDF
	latexmk --$@ $(MAIN) -quiet
clean:							## Delete files that can be regenerated
	latexmk -C
serve:							## Compile PDF, launch viewer, and watch for changes
	latexmk -pvc --pdf $(MAIN) -quiet
zip:
	zip -r archive.zip *
