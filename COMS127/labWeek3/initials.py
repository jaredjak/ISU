# Jared Krug       9/7/2023          Lab Section:6
# Lab Week 3
# Writing own initials with turtle


# Assignment 1
# Practicing printing strings, gathering input, and combining them together
import turtle

wb = turtle.Screen()
wb.bgcolor("blue")

# letter j
j = turtle.Turtle()
j.color("orange")
j.pensize(10)

# bring to starting position
j.speed(10)
j.penup()
j.backward(25)
j.left(90)
j.forward(75)
j.left(180)
j.pendown()

# actual writing for J
j.forward(150)
j.right(90)
j.forward(75)
j.right(90)
j.forward(25)

# Letter k
k = turtle.Turtle()
k.color("black")
k.pensize(10)

# bringing to position
k.speed(10)
k.penup()
k.forward(25)
k.left(90)
k.forward(75)
k.left(180)
k.pendown()

# actually writing k
k.forward(150)
k.right(180)
k.forward(75)
k.right(45)
k.forward(105)
k.backward(105)
k.right(90)
k.forward(105)

wb.exitonclick()