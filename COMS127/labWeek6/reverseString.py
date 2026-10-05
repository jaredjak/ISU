# Jared Krug          9/26/2023          Lab Number: 6
# Creating two functions that will reverse a string. Two versions.


def main():
    str1 = input("Please write a string: ")

    # Version 1
    revStr1 = reverseStringV1(str1)
    print("String:    ", str1)
    print("Version 1: ", revStr1)

    # Version 2
    revStr2 = reverseStringV2(str1)
    print("Version 2: ", revStr2)


# Version 1
def reverseStringV1(str):
    newStr1 = str
    return newStr1[::-1]

# Version 2
def reverseStringV2(str):
    tempStr = ""
    leng = len(str) - 1
    while leng >= 0:
        tempStr = tempStr + str[leng]
        leng = leng - 1
    return tempStr

if __name__ == "__main__":
    main()