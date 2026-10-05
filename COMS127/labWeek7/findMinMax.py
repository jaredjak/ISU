# Jared Krug     10/4/2023        Lab Section: 6
# Finding the min and max of inputted numbers

print()
print("Finding the min and max")
print()

def numbers():
    numsList = []
    while True:
        nums = input("Please enter as many integers as you want. Enter [*] to continue finding the min and max: ")
        if nums == "*":
            break
        else:
            numsList.append(nums)
    return numsList

def findMin(list):
    min_num = list[0]
    for i in list:
        if i < min_num:
            min_num = i
    return min_num

def findMax(list):
    max_num = list[0]
    for i in list:
        if i > max_num:
            max_num = i
    return max_num

def main():
    numList = numbers()
    # Looked up how to convert strings to integers
    # Citation:
    # Site: geeksforgeeks.org
    # Accessed: 10/4/2023
    numListInt = [eval(i) for i in numList]
    print("The numbers you picked were: ",numListInt)

    min = findMin(numListInt)
    print("The min is: ",min)

    max = findMax(numListInt)
    print("The max is: ",max)


if __name__ == "__main__":
    main()

