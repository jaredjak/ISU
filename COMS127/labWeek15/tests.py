def insertionSort(A):
    for i in range(1, len(A)):
        j = i

        while j > 0 and A[j-1] > A[j]:
            A[j-1], A[j] = A[j], A[j-1]
            j -= 1
    return A

def binarySearchLeftmost(A, target):
    retVal = None

    left = 0
    right = len(A) - 1

    while left < right:
        middle = (left+right) // 2
        if A[middle] < target:
            left = middle + 1
        else:
            right = middle
    
    if A[left] == target:
        retVal = left

    return retVal

# -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

def insertionSort(A):
    for i in range(1, len(A)):
        j = i

        while j > 0 and A[j-1] > A[j]:
            A[j-1], A[j] = A[j], A[j-1]
            j-=1
    return A

def binarySearchLeftmost(A, target):
    returnVal = None
    left = 0
    right = len(A) - 1

    while left < right:
        middle = (left+right) // 2
        if A[middle] < target:
            left = middle + 1
        else:
            right = middle

    if A[left] == target:
        returnVal = left
    return returnVal