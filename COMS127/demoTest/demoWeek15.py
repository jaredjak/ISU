# Jared Krug      11/28/23
# Classes and objects demo

class Point:
    def __init__(self, x, y) -> None:
        self.x = x
        self.y = y

    def getX(self):
        return self.x
    def getY(self):
        return self.y
    
    def setX(self):
        self.x = x
    def setY(self):
        self.y = y

p1 = Point(5, 3)
p2 = Point(0, -7)

print(p1)
print(p1.getX(), p1.getY())
print(p2)
print(p2.getX(), p2.getY())

class MyMath:
    @staticmethod
    def multiply(x, y):
        return x*y
    
print(MyMath.multiply(2, 9))

class Cat:
    kind = "feline"
    def __init__(self, name) -> None:
        self.name = name
    def getName(self):
        return self.name
    def setName(self):
        self.name = name

c1 = Cat("Garfield")
c2 = Cat("Felix")
print(c1.kind, c1.getName())
print(c2.kind, c2.getName())
Cat.kind = "CAAAT!!!"
print(c1.kind, c1.getName())
print(c2.kind, c2.getName())