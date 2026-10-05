# Jared Krug             9/17/2023
# Assignment 2
# Creating a calculator and/or lucky number based on what the user inputs to us
# Code takes the input and outputs either a caluculation, a lucky number, or a quit print


import random

# NOTE: Function definitions should go here!
def calculator(calcChoice, int1, int2):
    if calcChoice == "+":
        return add(int1,int2)
    
    elif calcChoice == "-":
        return subtract(int1,int2)
    
    elif calcChoice == "*":
        return multiply(int1,int2)
    
    elif calcChoice == "/":
        return divide(int1,int2)
    
    elif calcChoice == "//":
        return floorDiv(int1,int2)
    
    elif calcChoice == "%":
        return mod(int1,int2)
    
    elif calcChoice == "**":
        return expo(int1,int2)
    
    else:
        print("Invalid... You get None!")

def luckyNumber(a, b):
    numLuck = 0
    if a < b:
        numLuck = random.randrange(a,b+1)
    else:
        numLuck = random.randrange(b,a+1)
    return numLuck

# Doesn't need a function but I gave it one anyways
def quit():
    print("Fine be that way. Please come back...")


# The math functions are all here L48-L78
# adds the two integers
def add(x,y):
    return x + y
# subtracts the two integers
def subtract(x,y):
    return x - y
# multiplies the two integers
def multiply(x,y):
    return x * y
# divides the two integers. Prevents second integer from breaking the code.
def divide(x,y):
    if y==0:
        print("ERROR in / function: b = 0")
        y = 1
    return x / y
# floor divides the two integers. Prevents second integer from breaking the code.
def floorDiv(x,y):
    if y==0:
        print("ERROR in // function: b = 0")
        y = 1
    return x // y
# modulus of the two integers. Prevents second integer from breaking the code.
def mod(x,y):
    if y==0:
        print("ERROR in // function: b = 0")
        y = 1
    return x % y
# exponentiates the two integers
def expo(x,y):
    return x ** y
    
# Start of the actual program
print("Lucky Calculator!")
print()

print("By: Jared Krug")
print("[COM S 127 G]")
print()

# Determine initial player choice
print("What would you like to do?")
print()
choice = input("[c]alculator, [l]ucky number, [q]uit: ")
print()

if choice == "c":
    calcChoice = input("Please choose a calculation [+], [-], [*], [/], [//], [%], [**]: ")
    if calcChoice != ["+","-","*","/","//","%","**"]:
        print("ERROR: You must enter either \"+\", \"-\", \"*\", \"/\", \"//\", \"%\", or \"**\"")
    
# This section is definitely able to break the program, but I am unaware of how to fix it.
# The user just has to enter anything that isn't an integer and the program ends.
# Also, not sure how to repeat the same print message until the user types an appropriate option
# I don't think those were necessary for the grade, though, so I didn't try fixing them, or succeed anyway

    int1 = int(input("Enter an integer: "))
    int2 = int(input("Enter an integer: "))
    returnCalc = calculator(calcChoice, int1, int2)
    print("The result of your calculation is: ",returnCalc)

elif choice == "l":
    print("Time to figure out what your lucky number is!")
    print()
    int1 = int(input("Enter an integer: "))
    int2 = int(input("Enter an integer: "))
    returnLuckyNum = luckyNumber(int1,int2)
    print("Your lucky number is... ",returnLuckyNum)

elif choice == "q":
    quit()

else:
    print("ERROR: That's definitely not an option bro. Try that again.")

# The end