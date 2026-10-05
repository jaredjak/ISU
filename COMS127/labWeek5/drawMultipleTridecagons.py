# Jared Krug        9/20/2023         Lab Number:6
# Creating multiple tridecagons with the sides and initial location based on user input

# Citation
# https://handwiki.org/wiki/Tridecagon#Regular_tridecagon
# Author: HandWiki
# Created: June 15, 2021
# Accessed: September 14, 2023

import turtle
jared = turtle.Turtle()

def tridecagonTurtle(s,x,y):
    jared.speed(9)
    jared.penup()
    jared.goto(x,y)
    jared.pendown()
    for i in range(13):
        jared.forward(s)
        jared.right(360/13)

def drawMultipleTridecagons(s,x,y,nr,sr):
    # drawing the original tridecagon
    tridecagonTurtle(s,x,y)

    # drawing the new tridecagons
    for i in range(0, nr):
        x = x + sr
        tridecagonTurtle(s,x,y)
    
def main():
    # turtle
    wb = turtle.Screen()
    wb.bgcolor("lightBlue")
    jared.pencolor("darkOrange")

    # Gathering the values for the Tridecagon
    s = int(input("Please enter the first integer that will be a side length: "))
    x = int(input("Please enter the second integer that will represent the x-axis: "))
    y = int(input("Please enter the third integer that will also represent the y-axis: "))
    nr = int(input("Number of tridecagon repetitions:"))
    sr = int(input("Amount of space between each tridecagon repetitions: "))
    drawMultipleTridecagons(s,x,y,nr,sr)

    wb.exitonclick()

if __name__ == "__main__":
    main()
