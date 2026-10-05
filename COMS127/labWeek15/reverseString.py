# Jared Krug    12/4/2023   Lab Section: 6
# reversing a string both iteratively and recursively

def reverseIterative(str):
    tempStr = ""
    length = len(str) - 1
    while length >= 0:
        tempStr = tempStr + str[length]
        length = length - 1
    return tempStr

def reverseRecursive(str):
    #Base Case
    if len(str) == 0:
        return str
    #Recursion
    else:
        return reverseRecursive(str[1:]) + str[0]

def main():
    str1 = input("Please enter anything to reverse it: ")
    rev1 = reverseIterative(str1)
    print(rev1)

    rev2 = reverseRecursive(str1)
    print(rev2)

if __name__ == "__main__":
    main()