# Jared Krug         # 10/3/23
# lists

# emptyList = []
# print(emptyList)
# print(len(emptyList))

# colorList = ["indigo","blue","magenta","red","lavendar"]
# print(colorList)
# print(len(colorList))

# multiTypeList = [17, 3.14, "string", False, None]
# print(multiTypeList)
# print(len(multiTypeList))

# listOfLists = [emptyList, colorList, multiTypeList]
# print(listOfLists)
# print(len(listOfLists))

# print(listOfLists[1][1]) # = blue
# print(listOfLists[1][1][1]) # = l
# print(listOfLists[1][1][100]) # = error

# letterList = ["W","Q","P",["O","o"],"U"]
# truthVal = "Q" in letterList
# print(truthVal) # = True

# truthVal2 = "O" in letterList
# print(truthVal2) # = False

# truthVal3 = "o" in letterList[3]
# print(truthVal3) # = True

# countries = ["Israel", "Uzbekistan", "Mexico", "Indonesia"]
# print(countries)

# countries += ["America", "Chad", "Australia"]
# print(countries)

# countries *= 2
# print(countries)

# numbers = [1, 2, 3, 4, 5, 6, 7]
# print(numbers[2:5]) # = [3,4,5]
# print(numbers[2:5:2]) # = [3,5]
# print(numbers[5:2:-1]) # = [6,5,4]

# numbers = [1,2,3,4,5,6,7]
# print(numbers) # = [1, 2, 3, 4, 5, 6, 7]

# numbers[2] = "q"
# print(numbers) # = [1, 2, 'q', 4, 5, 6, 7]

# numbers[2:6] = ["s", "t"]
# print(numbers) # = [1, 2, 's', 't', 7]

# numbers[1:4] = []
# print(numbers) # = [1, 7]

# numbers[1:1] = ["A", "B", "C", "D"]
# print(numbers) # = [1, 'A', 'B', 'C', 'D', 7]

# animals = ["Cat", "Dog", "Rabbit"]
# print(hex(id(animals)))
# animals.append("Vulture")
# print(hex(id(animals)))

# animals = ["Cat", "Dog", "Rabbit"]
# print(hex(id(animals)))

# animals = ["Cat", "Dog", "Rabbit"] + ["Vulture"]
# print(hex(id(animals)))

def countList(count):
    lst = []
    for i in range(0, count):
        lst.append(i)
    return lst

numbers = countList(10)
print(numbers)