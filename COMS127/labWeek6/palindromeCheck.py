# Jared Krug      9/26/2023      Lab Number: 6
# checking for palindromes. 2 versions

import reverseString

# Version 1
def main():
    str1 = input("Please write a string to check if it is a palindrome: ")
    print("String: ", str1)
    str2 = palindromeCheckV1(str1)
    str3 = palindromeCheckV2(str1)

    # Version 1
    if str2 == True:
        print("V1: That's a palindrome right there.")
    else:
        print("V1: That is definitely not a palindrome.")

    # Version 2
    if str3 == True:
        print("V2: That's a palindrome right there.")
    else:
        print("V2: That is definitely not a palindrome.")

# Version 1
def palindromeCheckV1(str):
    pal = reverseString.reverseStringV1(str)
    return pal == pal[::-1]
    
# version 2
def palindromeCheckV2(str):
    for i in range(0, int(len(str)/2)):
        if str[i] != str[len(str)-i-1]:
            return False
    return True

if __name__ == "__main__":
    main()