# Makefile - Pandoc with Markdown input
# Florent Dufour
# 2018 - 2021

SRC			= src/metadata.md src/main.md src/appendices.md src/references.md
FLAGS		= --verbose --filter pandoc-crossref --citeproc
TRGT		= ./out/out.$@
LINTER	= proselint

all: pdf html docx	## Render the Markdown to all targets
pdf: $(SRC)					## Render the PDF
	pandoc $(SRC) $(FLAGS) -o $(TRGT)
html: $(SRC) 				## Render the HTML
	pandoc $(SRC) $(FLAGS) -s -o $(TRGT)
docx: $(SRC)				## Render the MS Word DOCX according to reference style
	pandoc --reference-doc=./resources/custom-reference.docx $(SRC) $(FLAGS) -o $(TRGT)
tex:								## Write in TeX format for inspection
	pandoc $(SRC) --self-contained $(FLAGS) -o $(TRGT)
lint:								## Lint the source for prose and open in BBResults
	$(LINTER) $(SRC) | bbresults
open: ./out/out.pdf								## Open the PDF in the default viewer
	open ./out/out.pdfli
clean:							## Clean the output folder
	trash ./out/*
help: 							## Print this help
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
