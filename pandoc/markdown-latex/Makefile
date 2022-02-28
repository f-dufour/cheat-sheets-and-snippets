# Makefile - Pandoc with Markdown input
# Florent Dufour
# 2018 - 2021

SRC = main.md
TRGT = ./out/out.$@

all: pdf html docx	## Render the Markdown to all targets
pdf: $(SRC)			## Render the PDF
	pandoc $(SRC) -o $(TRGT)
html: $(SRC) 		## Render the HTML
	pandoc $(SRC) -o $(TRGT)
docx: $(SRC)		## Render the MS Word DOCX according to reference style
	pandoc --reference-doc=./resources/custom-reference.docx $(SRC) -o $(TRGT)
view: pdf			## Open the PDF in the default viewer
	open ./out/out.pdf
clean:				## Clean the output folder
	trash ./out/*
help: 				## Print this help
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
