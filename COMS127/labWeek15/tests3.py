def isPalindromeIter(str):
    for i in range(0, int(len(str)/2)):
        if str[i] != str[len(str) - i - 1]:
            return False
    return True

def isPalindromeRecursive(str):
    # Base Case
    if len(str) <= 1:
        return True
    # Recursion
    return str[0] == str[len(str)-1] and isPalindromeRecursive(str[1:len(str)-1])

def reverseStringIter(str):
    tempStr = ""
    length = len(str) - 1
    while length >= 0:
        tempStr = tempStr + str[length]
        length = length - 1
    return tempStr

def reverseStringRecursive(str):
    # Base Case
    if len(str) == 0:
        return str
    #Recursion
    else:
        return reverseStringRecursive(str[1:]+str[0])

# -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

