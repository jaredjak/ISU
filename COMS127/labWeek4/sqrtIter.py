# Jared Krug        9/14/2023         Lab Number:6
# Square root iteration via user input. User decides x and number of iterations

def sqrtIter(x, iterations):
    y = (x+1)/2
    for i in range(0, iterations):
        y = ((x/y)+y)/2
    return y
        
x = int(input("Integer that we will be square rooting: "))
iterations = int(input("Integer for the iterations: "))
returnSqrt = sqrtIter(x, iterations)
print(returnSqrt)