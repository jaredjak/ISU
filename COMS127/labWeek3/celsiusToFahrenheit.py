# Jared Krug       9/7/2023          Lab Section:6
# Lab Week 3
# Finding the conversion of celsius to fahrenheit via user input

# Formula:  F = (C × 9/5) + 32
# https://www.thoughtco.com/celcius-to-farenheit-formula-609227
# Author:  Anne Marie Helmenstine, Ph.D.
# Created: February 04, 2020
# Accessed: September 7, 2023

print("We're here to find the conversion of celsius to fahrenheit.")
ctf = (float(input("Enter a number here that will represent the celsius: ")))
print("The conversion to fahrenheit is:", (ctf*9/5)+32)