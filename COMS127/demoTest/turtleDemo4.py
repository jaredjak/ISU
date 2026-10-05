import turtle

wb = turtle.Screen()
alex = turtle.Turtle()

for aColor in ["red","green","purple","blue"]:
    alex.color(aColor)
    alex.forward(150)
    alex.right(90)

wb.exitonclick()