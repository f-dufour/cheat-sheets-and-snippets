# Florent Dufour
# 2016 - 2021
# A LaTeX article project

ENGINE = latexmk
SRC    = main.tex 
TRGT   = out
FLAGS  = -silent -time -use-make

all: dvi ps pdf html	## Compile and render to all targets
pdf:					## Compile to PDF
	$(ENGINE) -$@ $(SRC) $(FLAGS) -outdir=$(TRGT)/$@
dvi:					## Compile to DVI
	$(ENGINE) -$@ $(SRC) $(FLAGS) -outdir=$(TRGT)/$@
ps:						## Compile to plain PostScript
	$(ENGINE) -$@ $(SRC) $(FLAGS) -outdir=$(TRGT)/$@
html:					## Render to HTML
	@echo "No HTML generated"
open:	 				## Open out directory with all targets
	open $(TRGT)^
serve:					## Compile to PDF, watch for changes, and refresh preview
	$(ENGINE) -pdf -pvc $(SRC) -outdir=/tmp
clean:					## Clean out directories
	-trash out/*/*
help:					## Print this help
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'


## Smart compile
#% :
#	$(ENGINE) -$@ $(SRC) $(FLAGS) -outdir=out/$@