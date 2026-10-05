# Jared Krug        9/14/2023         Lab Number:6
# Taking input from user in the forms of 3 integers.
# Then find the sum of a random number(s) between 'a' and 'b' 'c' times

import random

def randomSum(a, b, c):
    sum = 0
    for i in range(c):
        randNum = random.randrange(a,b+1)
        print(randNum)
        sum = sum + randNum
    return sum

a = int(input("Integer 1: "))
b = int(input("Integer 2: "))
c = int(input("Times added together randomly: "))

returnResult = randomSum(a, b, c)
print(returnResult)