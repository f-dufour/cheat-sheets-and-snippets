#!/bin/bash
# Usage: ./read_csv.bash sheet.csv

while IFS="," read -r  col1 col2; do
	echo "col1 is  $col1 and col2 is  $col2"
done < $1

