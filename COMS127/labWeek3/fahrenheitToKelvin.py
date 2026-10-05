# Jared Krug       9/7/2023          Lab Section:6
# Lab Week 3
# Finds the conversion of fahrenheit to kelvin via user input

# Formula: (K) = (F - 32) / 1.8 + 273.15
# https://www.srhartley.com/fahrenheit-to-kelvin/formula/
# Author: -
# Created: -
# Accessed: September 7, 2023

print("We're here to find the conversion of fahrenheit to kelvin.")
ftk = (float(input("Enter a number here that will represent the fahrenheit: ")))
print("The conversion to kelvin is:", (ftk-32)/1.8+273.15)