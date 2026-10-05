# Jared Krug      9/5/2023
# turtle demo

import turtle


wb = turtle.Screen()
wb.bgcolor("lightGreen")

jared = turtle.Turtle()
jared.color("red")
jared.pensize(50)

jared.forward(250)
jared.left(45)
jared.forward(50)

wb.exitonclick()