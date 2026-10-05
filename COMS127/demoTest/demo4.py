# Jared Krug     9/12/203


def printName(name, number_of_times):
    for i in range(0, number_of_times):
        print(name)

name = input("What is your name?: ")
number = int(input("How many times?: "))
printName(name, number)
print("Done!")