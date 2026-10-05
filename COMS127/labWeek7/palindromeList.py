# Jared Krug     10/4/2023        Lab Section: 6
# Checking if the items in the list are palindromes
print()
print("Finding palindromes")
print()

def aList():
    bList = []
    while True:
        pali = input("Please enter as many strings as you want. Enter [*] to continue: ")
        if pali == "*":
            break
        else:
            bList.append(pali)
    return bList

def palindromeList(list):
    for i in range(0, int(len(list)/2)):
        if list[i] != list[len(list)-i-1]:
            return False
    return True

def main():
    pali = aList()
    print("The strings you chose are: ",pali)

    pali2 = palindromeList(pali)
    if pali2 == True:
        print("That is a palindrome!")
    else:
        print("That is not a palindrome.")

if __name__ == "__main__":
    main()