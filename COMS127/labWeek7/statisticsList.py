# Jared Krug     10/4/2023        Lab Section: 6
# Finding the mean and median of randomly generated lists

import random

print()
print("Mean and Median")
print()

def generateInput():
    aList = []
    for i in range(random.randint(200, 500+1)):
        bList = random.randint(1, 2000+1)
        aList.append(bList)
    return aList

def findMean(lst):

    # Citation: Arithmetic Mean = (sum/number of observations)
    # Source: https://en.wikipedia.org/wiki/Arithmetic_mean
    # Author: none
    # Date Accessed: 10/5/2023

    i = 0
    num = 0
    while i < len(lst):
        num = num + lst[i]
        i = i + 1
    num = num / len(lst)
    return num

def findMedian(lst):

    # Citation: 
    # Source: https://www.mathsisfun.com/median.html
    # Author: None
    # Date Accessed: 10/5/2023

    lst.sort()

    if len(lst)%2 == 0:
        firstMid = len(lst)//2 - 1
        secondMid = len(lst)//2
        
        avg = (lst[firstMid] + lst[secondMid])/2

        return avg
    
    else:
        mid = len(lst)//2
        return lst[mid]

def main():
    geninp = generateInput()
    mean = findMean(geninp)
    median = findMedian(geninp)

    print("The mean of the random list is:", "{:.2f}".format(mean))
    print("The median of the random list is:", median)

if __name__ == "__main__":
    main()