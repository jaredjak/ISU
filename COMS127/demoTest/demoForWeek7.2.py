# Jared Krug         # 10/5/2023
# Lists Demo 2/2

# cat1 = "Bonkers"
# cat2 = "Bonkers"
# print(cat1 is cat2) # = True
# print(hex(id(cat1))) # = 0x1e4f2cf8c30
# print(hex(id(cat2))) # = 0x1e4f2cf8c30

# a = ["Orange", "Peach", "Apple"]
# b = ["Orange", "Peach", "Apple"]
# print(a) # = ["Orange", "Peach", "Apple"]
# print(hex(id(a))) # 0x24bef6f8fc0
# print(b) # = ["Orange", "Peach", "Apple"]
# print(hex(id(b))) # 0x24bef8574c0
# print(a is b) # = False
# print(a == b) # = True

# sports = ["Fencing", "Football", "Baseball", "Softball"]
# clone = sports[:]
# print(sports)
# print(clone)
# clone.append("Lacrosse")
# print(sports)
# print(clone)
# print(hex(id(sports)))
# print(hex(id(clone)))

# print(hex(id(sports[0])))
# print(hex(id(clone[0])))

# planets = ["Jupiter", "Earth", "Mars", "Saturn"]
# clone = planets[:]
# print(planets)
# print(clone)
# clone.append("Pluto")

# planets2 = planets
# del planets2[1]
# print(planets)

# print(hex(id(planets)))
# print(hex(id(planets2)))

# animals = ["Cat", "Dog", "Penguin"]
# print(animals)
# newAnimals = [animals] * 2
# print(newAnimals)
# animals[0] = "Aligator"
# print(newAnimals)

# def listStringDoubler(lst):
#     for i in range(0, len(lst)):
#         lst[i] *= 2
# birds = ["Larry", "Ducks", "Goose"]
# print(birds) # ['Larry', 'Ducks', 'Goose']
# listStringDoubler(birds)
# print(birds) # ['LarryLarry', 'DucksDucks', 'GooseGoose']

def listRef(lst):
    returnList = []
    for i in range(0, len(lst)):
        returnList.append(lst[i] * 2)
    return returnList
animals = ["Cat", "Dog", "Penguin"]
print(animals)
print(hex(id(animals)))
animals = listRef(animals)
print(animals)
print(hex(id(animals)))
