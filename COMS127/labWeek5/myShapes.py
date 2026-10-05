# Jared Krug             9/20/2023         Lab Section: 6
# Lab Week 5
# Calculations for shapes inside of functions to be used in calculationTest.py

import math

def cubeSurface(a):
    # Formula: SA = 6 * s^2
    # https://www.wikihow.com/Find-the-Surface-Area-of-a-Cube
    # Author: David Jia
    # Written: October 17, 2022 
    # Accessed: September 7, 2023

    return 6*a**2

def cubeVolume(a):
    # Formula: V = s^3
    # https://tutors.com/lesson/volume-of-a-cube
    # Author: Malcolm McKinsey
    # Written: January 28, 2023
    # Accessed: September 7, 2023

    return a**3

def sphereVolume(a):
    # Formula: V = 4/3πr^3
    # https://www.wikihow.com/Calculate-the-Volume-of-a-Sphere
    # Co-Author: Grace Imson
    # Created: February 10, 2023
    # Accessed: September 7, 2023

    return 4/3*math.pi*a**3

def sphereSurface(a):
    # Formula: 4πr^2
    # https://www.wikihow.com/Find-the-Surface-Area-of-a-Sphere
    # Author: -
    # Created: November 25, 2022
    # Accessed: September 7, 2023

    return 4*math.pi*a**2