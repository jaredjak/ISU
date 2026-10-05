# Jared Krug            9/7/2023
# Testing the random module


import random as rand

print(rand.random())

val = rand.randrange(0,100,10)
print(val)


# create a formula that generates an integer between:
# [lower_bound, upper_bound]
lower_bound = 1
upper_bound = 5
answer = lower_bound + int(rand.random() * (upper_bound - lower_bound + 1))
print(answer)