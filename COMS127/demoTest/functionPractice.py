# Jared Krug      9/12/2023
# Function practice

import math

def multiplyPlusTwo(a, b):
    answer = a * b + 2
    return answer
value = multiplyPlusTwo(2, 3)
print(value)

print()

def factorialPlusOne(a):
    answer = math.factorial(a)+1
    return answer
number = int(input("Enter an integer: "))
returnValue = factorialPlusOne(number)
print(returnValue)