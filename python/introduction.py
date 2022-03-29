# Florent Dufour
# Python 2013 - 2022
# https://youtu.be/H1elmMBnykA?t=1263

# Imports
import sys
import math
import random
import threading
import time
from functools import reduce

# Output input
print("Hello World")
name = input("What is your name? ")
print("Hi", name)

# Multi lines
v1 = (
	1 + 2
	+ 3
)

v1 = 1 + 2 \
	+ 3

# One line
v1 = 5; v1 = v1 + 5

'''
Multi line comments
'''

# One line
v1,v2 = 3,4
v2 = v3 = 5

v1 = "Hello" # Type is dynamic
print(type(v1))
print(sys.maxsize)
print(sys.float_info.max)

# Complex numbers
c = 5 + 6j
print(c)

# Boolean
b1 = True
b2 = False

# Strings
str1 = "Bonjour"
str2 = "L'idéal"
str3 = '''Il dit "Bonjour" à l'idéal'''

# Cqst
print("Cast", type(int(51.1)))
print("Cast", type(str(51.1)))
print("Cast", type(chr(97)))
print("Cast", type(ord('a')))

# Output
print(1, 2, 3, sep="/")
print(1, 2, 3, sep="/", end="")
print("\n%04d %s %.2f %c" % (1, "ABC", 1.234, 'A'))

# Math
print("1 + 2 = ", 1 + 2)
print("1 - 2 = ", 1 - 2)
print("1 * 2 = ", 1 * 2)
print("1 / 2 = ", 1 / 2)
print("1 % 2 = ", 1 % 2)
print("1 ** 2 = ", 1 ** 2)
print("1 // 2 = ", 1 // 2)

# Math shorthand
i1 = 2
i1 += 1
i1 *= 1

print("Random", random.randint(1, 101))
print(math.inf > 0)
print(math.inf - math.inf)

# Conditionals
