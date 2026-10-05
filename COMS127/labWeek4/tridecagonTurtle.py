# Jared Krug        9/14/2023         Lab Number:6
# Creating a tridecagon with the sides and initial location based on user input

# Citation
# https://handwiki.org/wiki/Tridecagon#Regular_tridecagon
# Author: HandWiki
# Created: June 15, 2021
# Accessed: September 14, 2023

import turtle
wb = turtle.Screen()
wb.bgcolor("lightBlue")
jared = turtle.Turtle()
jared.pencolor("red")

def tridecagonTurtle(s,x,y):
    jared.penup()
    jared.goto(x,y)
    jared.pendown()
    for i in range(13):
        jared.forward(s)
        jared.right(360/13)

s = int(input("Please enter the first integer that will be a side length: "))
x = int(input("Please enter the second integer that will represent where the shape starts: "))
y = int(input("Please enter the third integer that will also represent where the shape starts: "))
tridecagonTurtle(s, x, y)

wb.exitonclick()