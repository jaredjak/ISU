# Jared Krug       9/7/2023          Lab Section:6
# Lab Week 3
# Finding the sphere's volume based on a user input


# Formula: V = 4/3πr^3
# https://www.wikihow.com/Calculate-the-Volume-of-a-Sphere
# Co-Author: Grace Imson
# Created: February 10, 2023
# Accessed: September 7, 2023

import math

print("We're here to find a sphere's volume.")
sv = (float(input("Enter a number here that will represent the radius of a sphere: ")))
print("The sphere's volume is:", 4/3*math.pi*sv**3)

