# Florent Dufour
# 2018 - 2021

ENGINE = groff
SRC    = main.ms
FLAGS  = -P-p4 -ms -e

all: ps pdf
ps: $(SRC)
	$(ENGINE) $(SRC) $(FLAGS) > out/main.$@
pdf: $(SRC)
	$(ENGINE) $(SRC) $(FLAGS) -T pdf > out/main.$@
clean:
	trash out/*
