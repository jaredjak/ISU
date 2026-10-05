# Jared Krug            9/5/2023
# Turtle demo


import turtle # allows us to use the turtles library
wb = turtle.Screen() # creates a window
alex = turtle.Turtle() # create a turtle named alex
alex.forward(150) # tell alex to move forward by 150
alex.left(90) # turn by 90 degrees
alex.forward(75) # complete second side

wb.exitonclick() # prevents screen from disappearing

# turtle.done() # prevents screen from disappearing