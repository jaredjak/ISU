# Jared Krug       9/7/2023          Lab Section:6
# Lab Week 3
# Finding the conversion of kelvin to fahrenheit via user input

# Formula: F = 1.8*(K-273) + 32.
# https://www.thoughtco.com/convert-kelvin-to-fahrenheit-609234
# Author: Anne Marie Helmenstine, Ph.D.
# Created: February 02, 2022
# Accessed: September 7, 2023

print("We're here to find the conversion of kelvin to fahrenheit.")
ktf = (float(input("Enter a number here that will represent the kelvin: ")))
print("The conversion to fahrenheit is:", 1.8*(ktf-273)+32)