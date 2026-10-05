# Jared Krug        9/26/2023
# Creating a turtle to draw an L System

# Citation
# “How to Think Like a Computer Scientist: Interactive Edition”
# Activity: 9.15.2 ActiveCode
# Author(s): Jeffrey Elkner, Peter Wentworth, Allen B. Downey, Chris Meyers, and Dario Mitchell
# Accessed on: 9/26/2023
import turtle
import random

def createLSystem(numIters,axiom):
    startString = axiom
    endString = ""
    for i in range(numIters):
        endString = processString(startString)
        startString = endString

    return endString

def processString(oldStr):
    newstr = ""
    for ch in oldStr:
        newstr = newstr + applyRules(ch)

    return newstr

def applyRules(ch):
    newstr = ""
    if ch == 'F':
        newstr = 'F-F++F-F'   # Rule 1
    elif ch == 'T':
        newstr = 'TPTP'   # Rule 2
    elif ch == 'P':
        newstr == 'T+P'   # Rule 3
    else:
        newstr = ch    # no rules apply so keep the character

    return newstr

def drawLsystem(aTurtle, instructions, angle, distance):
    for cmd in instructions:
        if cmd == 'F':
            aTurtle.forward(distance)
        elif cmd == 'B':
            aTurtle.backward(distance)
        elif cmd == 'T':
            tridecagonTurtle(25, aTurtle) 
        elif cmd == 'P':
            randomLocation(aTurtle)
        elif cmd == '+':
            aTurtle.right(angle)
        elif cmd == '-':
            aTurtle.left(angle)

def tridecagonTurtle(side,turtle):
    for i in range(13):
        turtle.forward(side)
        turtle.right(360/13)

def randomLocation(turtle):
    x = random.randint(-200,200)
    y = random.randint(-200,200)
    turtle.penup()
    turtle.goto(x,y)
    turtle.pendown()


def main():
    inst = createLSystem(4, "T")   # create the string
    print(inst)
    t = turtle.Turtle()            # create the turtle
    wn = turtle.Screen()

    t.up()
    t.back(200)
    t.down()
    t.speed(9)
    drawLsystem(t, inst, 60, 5)   # draw the picture
                                  # angle 60, segment length 5
    wn.exitonclick()

main()


