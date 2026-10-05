# Jared Krug       9/7/2023          Lab Section:6
# Lab Week 3
# Finding the sphere's surface area based on a user input

# Formula: 4πr^2
# https://www.wikihow.com/Find-the-Surface-Area-of-a-Sphere
# Author: -
# Created: November 25, 2022
# Accessed: September 7, 2023

import math

print("We're here to find a sphere's surface area.")
ssa = (float(input("Enter a number here that will represent the radius of a sphere: ")))
print("The sphere's surface area is:", 4*math.pi*ssa**2)