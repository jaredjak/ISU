# Jared Krug    9/5/2023
# Turtle demo

import turtle

wb = turtle.Screen()
wb.bgcolor("lightGreen")

jared = turtle.Turtle()
jared.color("red")
jared.pensize(50)

jared.forward(250)
jared.left(45)
jared.forward(50)


bob = turtle.Turtle()
bob.color("gray")
bob.pensize(5)

bob.backward(200)
bob.right(90)
bob.forward(100)

wb.exitonclick()