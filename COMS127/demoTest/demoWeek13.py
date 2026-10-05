# Jared Krug       11/14/2023
# The big O

def binarySearchLeftMost(A, T):
    retVal = None
    right = len(A) - 1

    while left < right:
        middle = (left + right) // 2
        if A[middle] < T:
            left = middle + 1
        else:
            right = middle
    
    if A[left] == T:
        retVal = left

    return retVal

# ** Visual of what happens with the given code **

# A = [17,22,23,27,32,42]
# T = 42
# L = 0
# R = 5
# M = 2

#      L     M        R
#      0  1  2  3  4  5
# A = 17 22 23 27 32 42

# ----------------------

# T = 42
# L = 3
# R = 5
# M = 2

#            M  L     R
#      0  1  2  3  4  5
# A = 17 22 23 27 32 42

# ----------------------

# T = 42
# L = 3
# R = 5
# M = 4

#               L  M  R
#      0  1  2  3  4  5
# A = 17 22 23 27 32 42

# ----------------------

# T = 42
# L = 5
# R = 5
# M = 4
#                     L
#                  M  R
#      0  1  2  3  4  5
# A = 17 22 23 27 32 42

# return 5