# Jared Krug    12/4/2023   Lab Section:6
# checks for palindromes iteratively and recursively

def isPalindromeIterative(str):
    for i in range(0, int(len(str)/2)):
        if str[i] != str[len(str)-i-1]:
            return False
    return True

def isPalindromeRecursive(str):
    #Base case
    if len(str) <= 1:
        return True
    #Recursion
    return str[0] == str[len(str) - 1] and isPalindromeRecursive(str[1:len(str) - 1])

def main():
    str1 = input("Please enter anything to be checked for palindromity: ")
    check1 = isPalindromeIterative(str1)
    print(check1)

    check2 = isPalindromeRecursive(str1)
    print(check2)


if __name__ == "__main__":
    main()