# Jared Krug             9/20/2023         Lab Section: 6
# Lab Week 5
# Calculations for temps inside of functions to be used in calculationTest.py

def cToF(a):
    # Formula:  F = (C × 9/5) + 32
    # https://www.thoughtco.com/celcius-to-farenheit-formula-609227
    # Author:  Anne Marie Helmenstine, Ph.D.
    # Created: February 04, 2020
    # Accessed: September 7, 2023
    
    return(a*9/5)+32

def fToC(a):
    # Formula: 5/9(F-32)
    # https://www.thoughtco.com/fahrenheit-to-celsius-formula-609230
    # Author: Anne Marie Helmenstine, Ph.D.
    # Created: July 18, 2022
    # Accessed: September 7, 2023

    return (a-32)*5/9

def cToK(a):
    # Formula: K = C + 273.15
    # https://www.thoughtco.com/convert-celsius-to-kelvin-609229
    # Author: Anne Marie Helmenstine, Ph.D.
    # Created: December 04, 2019
    # Accessed: September 7, 2023

    return a+273.15

def kToC(a):
    # Formula: C = K - 273.15
    # https://www.thoughtco.com/convert-kelvin-to-celsius-609233
    # Author: Anne Marie Helmenstine, Ph.D.
    # Created: December 02, 2019
    # Accessed: September 7, 2023

    return a-273.15

def fToK(a):
    # Formula: (K) = (F - 32) / 1.8 + 273.15
    # https://www.srhartley.com/fahrenheit-to-kelvin/formula/
    # Author: -
    # Created: -
    # Accessed: September 7, 2023

    return (a-32)/1.8+273.15

def kToF(a):
    # Formula: F = 1.8*(K-273) + 32.
    # https://www.thoughtco.com/convert-kelvin-to-fahrenheit-609234
    # Author: Anne Marie Helmenstine, Ph.D.
    # Created: February 02, 2022
    # Accessed: September 7, 2023

    return 1.8*(a-273)+32