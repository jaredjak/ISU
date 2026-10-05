# Jared Krug             8/31/2023
# Assignment 1
# Practicing printing strings, gathering input, and combining them together

print("Zany Text!")
print()

print("By: Jared Krug")
print("[COM S 127 G]")
print()

# 'Zany Text' #1
print("Zany Text #1")
print()

# Gathering input
noun1 = input("noun: ")
adjective1 = input("adjective: ")
adjective2 = input("adjective: ")
adjective3 = input("adjective: ")
verb1 = input("past tense verb: ")
noun2 = input("noun: ")
print() # Print a blank line

# Printing the final string
print("Once upon a time, there was a " + 
        noun1 + 
        ". It was a " +
        adjective1 + ", " + 
        adjective2 + ", " + 
        adjective3 + " " +
        noun1 + 
        ". And, one day it " + 
        verb1 + " " +
        "all over the " + 
        noun2 + "!")

# Print a blank line between Zany Texts
print()

# 'Zany Text' #2 (2 pts.)
print("Zany Text #2")
print()

# Gathering input
hi1 = input("Hello! (say it back right now): ")
print(type(hi1))
# Printing the final string
print(hi1)
# Print a blank line between Zany Texts
print()

# 'Zany Text' #3 (2 pts.)
print("Zany Text #3")
print()

print("Time to do some math :(")
print()
# Gathering input
num1 = int(input("Enter a number here: "))
num2 = int(input("Enter another number here: "))
# Printing the final string
print("The numbers added:", num1+num2,
      " | ","subtracted:", num1-num2,
      " | ","multiplied:",num1*num2,
      " | ","divided:",num1/num2)
# Print a blank line between Zany Texts
print()

# 'Zany Text' #4 (2 pts.)
print("Zany Text #4")
print()

print("(insert lengthy 5,000 word user agreement made by your favorite app to steal your information)")
# Gathering input
info1 = input("Do you agree? (yes or yes): ")
# Printing the final string
#else ifs would go crazy here
print(info1 + "?! Thank you for giving us your information :)")
# Print a blank line between Zany Texts
print()