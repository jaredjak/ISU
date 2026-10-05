# Jared Krug       9/21/23
# Week 5 Day 2 Work

# for name in ["Jared", "Jeff", "Geoff", "Todd"]:
#     print("The value in 'name' is: {0}".format(name))

# maxNum = 5
# for i in range(0, maxNum+1):
#     print("{0} / {1}".format(i, maxNum))

# a = 10
# for i in range(1, a+1):
#     print("{0} / {1}".format(i, a))

# i = 0
# maxNum2 = 5
# while i < maxNum2 + 1:
#     print("{0} / {1}".format(i, maxNum2))
#     i += 1

#infinite
# running = True
# while running:
#     print("I haven't stopped!")

# do while
# choice = input("Enter 'orange' or 'yellow' or'green': ")
# while choice != "orange" and choice != "yellow" and choice != "green":
#     print("Error, not valid")
#     choice = input("Enter 'orange' or 'yellow' or'green': ")
# print("You chose {0}".format(choice))

# break
# count = 4
# for i in range(1, count + 1):
#     for j in range(1, count + 1):
#         print("i: {0}, j: {1}".format(i, j))

# count = 4
# for i in range(1, count + 1):
#     for j in range(1, count + 1):
#         print("V2 i: {0}, j: {1}".format(i, j))
#         if j >= count // 2:
#             break

# break the whole loop (inside and outside)
# count = 4
# for i in range(1, count + 1):
#     for j in range(1, count + 1):
#         print("V2 i: {0}, j: {1}".format(i, j))
#         if j >= count // 2:
#             breakJ = True
#             break
#     if breakJ:
#         break

count = 4
for i in range(1, count + 1):
    
    if i % 2 == 0:
        continue
    print(" Hello {0}".format(i))