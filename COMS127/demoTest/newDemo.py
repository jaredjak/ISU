# Jared Krug         9/14/2023
# Functions Demo - fruitful function

def cubeVal(x):
    return x*x*x

def printNameNumberOfTimes(name, number):
    for i in range(1, number+1):
        print("Your name is {0}. Printed {1} / {2}".format(name, i, number))

def subtract(a, b):
    return a - b

x=3
def foo():
    global x
    x += 1

def foo2():
    x = 0
    x += 1


value = int(input("Enter an integer: "))
returnValue = cubeVal(value)
print(" The cubed value of {0} is {1}. The original value was {0}".format(value,returnValue))

print()

myName = input("What is your name?: ")
myTimes = int(input("How many times to print? "))
printNameNumberOfTimes(myName, myTimes)

print()

subVal = subtract(subtract(3, 2), subtract(2, 3)) - subtract(10,4)
print(subVal)

print()

# foo()
# print(x)

print()

print(x)
foo2()
print(x)