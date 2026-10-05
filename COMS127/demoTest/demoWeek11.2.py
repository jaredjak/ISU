# Jared Krug     11/2/2023
# Recursion Demo #2

def factorial(n):
    #Base case
    if n<= 1:
        return 1
    #recursion
    return n * factorial(n - 1)

def fibonacci(n):
    #Fibonacci math: 0, 1, 1, 2, 3, 5, 8, 13, 21 ...
    # Add the previous two numbers togehter
    #Base Case
    if n==0 or n==1:
        return n
    #Recursion
    return fibonacci(n - 1) + fibonacci(n - 2)

#STUDY THIS FUNCTION BECAUSE IT IS AP POTENTIA FINAL QUESTION
def isPalindrome(n):
    #Base Case
    if len(n) <= 1:
        return True
    #Recursion
    return n[0] == n[len(n) - 1] and isPalindrome(n[1:len(n) - 1])
    
def stairs(n):
    #Base Vase
    if n ==1 or n<= -1:
        return 0
    
    if n==0:
        return 1
    
    #Recursion
    stairs2 = stairs(n-2)
    stairs3 = stairs(n-3)
    return stairs2 + stairs3

def main():
    val1 = factorial(5)
    print(val1)

    val2 = fibonacci(10) #starts breaking after 40? (maybe)
    print(val2)

    val3 = isPalindrome("tacocat")
    val4 = isPalindrome("cat")
    print(val3)
    print(val4)

    val5 = stairs(9)
    print(val5)

if __name__ == "__main__":
    main()