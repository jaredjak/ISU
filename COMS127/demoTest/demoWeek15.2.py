# Jared Krug         11/30/23
# Demo week 15: lecture 2: Inheritance demo (Class and objects)

class Cat:
    def __init__(self, name, age, color, cost) -> None:
        self.name = name
        self.age = age
        self.color = color
        self.cost = cost

    def __str__(self) -> str:
        return ("Name: " + str(self.name) +
                ", Age: " + str(self.age) +
                ", Color: " + str(self.color) +
                ", Cost: " + str(self.cost))
    
    def speak(self):
        return "Meow" + ("!" * self.age)
    
c1 = Cat("Tom", 17, "Gray", 0.25)
print(c1)
print(c1.speak())
print(c1.speak) #notice the difference between this and the previous line

# ---------------------------------------------------------

class Dragon:
    def __init__(self, name, age, color, cost) -> None:
        self.name = name
        self.age = age
        self.color = color
        self.cost = cost
    
    def __str__(self) -> str:
        return ("Name: " + str(self.name) +
                ", Age: " + str(self.age) +
                ", Color: " + str(self.color) +
                ", Cost: " + str(self.cost))
    
    def speak(self):
        return "Roar" + ("." * self.age)
    
d1 = Dragon("Barney", 50, "Orange", 300000000)
print(d1)
print(d1.speak())
print(d1.speak) #notice the differences between this line and the previous one

# ----------------------------------------------------------

class Werewolf:
    def __init__(self, name, age, color, cost) -> None:
        self.name = name
        self.age = age
        self.color = color
        self.cost = cost
    def __str__(self) -> str:
        return ("Name: " + str(self.name) +
                ", Age: " + str(self.age) +
                ", Color: " + str(self.color) +
                ", Cost: " + str(self.cost))
    def speak(self) -> str:
        return "Howl" + ("?" * self.age)
    
w1 = Werewolf("Ted", 15, "Black", 0)
print(w1)
print(w1.speak())
print(w1.speak) #notice the differences between this line and the previous one

# ___________________________________________________________________________________________________
# This code makes more sense as it avoids the constant repetition of code in the previous examples
print()

class Animal:
    def __init__(self, name, age, color, cost) -> None:
        self.name = name
        self.age = age
        self.color = color
        self.cost = cost
    def __str__(self) -> str:
        return ("Name: " + str(self.name) +
            ", Age: " + str(self.age) +
            ", Color: " + str(self.color) +
            ", Cost: " + str(self.cost))
    def speak(self) -> str:
        return "Hello"
    
class Cat(Animal):
    def speak(self) -> str:
        return "Meow" + ("!" * self.age)
    
class Dragon(Animal):
    def speak(self) -> str:
        return "Roar" + ("." * self.age)
    
class Werewolf(Animal):
    def speak(self) -> str:
        return "Howl" + ("?" * self.age)
    
# new class for Tiger. Shows multiple-inheritance 
class Robot:
    def __init__(self, power) -> None:
        self.power = power
    def beep(self):
        return "Beep " * self.power
    
# Includes a class that calls one that calls the super class
class Tiger(Cat, Robot):
    def __init__(self, name, age, color, cost, strength, power) -> None:
        Cat.__init__(self, name, age, color, cost)
        Robot.__init__(self, power)
        self.strength = strength
    def __str__(self) -> str:
        return super().__str__() + ", Strength" + str(self.strength)
    def yell(self):
        return "A" + ("h" * self.strength)

c1 = Cat("Tom", 17, "Gray", 0.25)
print(c1)
print(c1.speak())
print(c1.speak)

d1 = Dragon("Barney", 50, "Orange", 300000000)
print(d1)
print(d1.speak())
print(d1.speak)

w1 = Werewolf("Ted", 15, "Black", 0)
print(w1)
print(w1.speak())
print(w1.speak)

# Both meows and ahs because it is both a tiger and a cat
t1 = Tiger("Tigger", 5, "Orange", 70, 10, 7)
print(t1)
print(t1.speak())
print(t1.speak)
print(t1.yell())
print(t1.yell)
print(t1.beep())
print(t1.beep)

animals = [c1, d1, w1, t1]
# animals.append(Animal("Genericy", 1, "White", 10))
# animals.append(Cat("Scratchy", 2, "Orange", 10))
# animals.append(Dragon("Scaley", 5, "Green", 10))
# animals.append(Werewolf("Growly", 4, "Green", 10))
# animals.append(Tiger("Stripey", 3, "White", 10, 4))
for animal in animals:
    print(animal.speak())