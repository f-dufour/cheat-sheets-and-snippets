# Expand textually, no sorting
echo hi{AAA,CCC,EEE,DDD}there

# Expand and then match ch1, ch2, app1, app2
touch ch1 ch2 ch3 ch44
touch app1 app22 app2
ls {ch,app}?

# Expand to mv info -> info.old
touch info
mv info{,.old}

# Simple numeric expansion
echo 1 to 10 is {1..10}

# Numeric expansion with increment
echo 1 to 10 by 2 is {1..10..2}

# Numeric expansion with zero padding
echo 1 to 10 with zeros is {01..10}
