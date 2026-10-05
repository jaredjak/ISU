# Jared Krug      10/26/2023
# Exceptions (Try/ except demo)

# pet = "Cat"

# try:
#     print(pet[12])
#     print("After exception") #Doesn't show up because skipped
# except Exception as e:
#     print("ERROR:", e) # ERROR: string index out of range

# try:
#     x = 12/0
# except Exception as e:
#     print("ERROR:", e) # ERROR: division by zero
# print("After Error...") # After Error...

# pets = {"Scratch": "Cat", "Fido": "Dog"}

# try:
#     print(pets["Dinosaur"])
# except Exception as e:
#     print("ERROR:", e) # ERROR: 'Dinosaur'

# def foo():
#     print("Hello from foo()")
#     try:
#         foo2()
#     except Exception:
#         print("foo2() didn't work right!")
# def foo2():
#     if True:
#         raise Exception
#     print("Hello from foo2()")
# def foo3():
#     print("Hello from foo3()")
# def main():
#     foo()
#     foo3()
# if __name__ == "__main__":
#     main()
# =-------------------------------------
# while True:
# Assign Input to a Variable as a String
# try:
# Attempt Type Conversion
# except:
# Print Error Message
# continue
# Test range of input (optional)
# Print Error Message
# continue
# break (Ends the Loop)

while True:
    testVariable = input("Please Enter an Integer Between 0-9: ")
    try:
        testVariable = int(testVariable)
    except:
        print("ERROR: Please Enter an Integer Between 0-9: ")
        continue
    if testVariable < 0 or testVariable > 9:
        print("ERROR: Integer Must Be > 0 and <= 9")
        continue
    break
print("You entered: {0}".format(testVariable))