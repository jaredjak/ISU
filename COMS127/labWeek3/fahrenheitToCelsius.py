# Jared Krug       9/7/2023          Lab Section:6
# Lab Week 3
# Finding the conversion of fahrenheit to celsius via user input

# Formula: 5/9(F-32)
# https://www.thoughtco.com/fahrenheit-to-celsius-formula-609230
# Author: Anne Marie Helmenstine, Ph.D.
# Created: July 18, 2022
# Accessed: September 7, 2023

print("We're here to find the conversion of fahrenheit to celsius.")
ftc = (float(input("Enter a number here that will represent the fahrenheit: ")))
print("The conversion to celsius is:", (ftc-32)*5/9)