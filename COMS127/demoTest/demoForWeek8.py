# Jared Krug      10/10/2023
# Tuple/Files Demo

# pets = ["cat", "dog", "bird", "giraffe"]
# print(pets[3:10]) # = ['giraffe']

# -----------------------------

# fileref = open("ccdata.txt", "r")

# for line in fileref:
#     # print(line) # = doubles the spaces
#     # print(line, end="--JK\n") # = prints "--JK" at beginning of line then the \n creates a new line
#     print(line.split())

# fileref.close()

# -----------------------------

fileref = open("example.txt", "w")

for i in range(0,9):
    fileref.write(str(i)+"\n")
fileref.write("9")

fileref.close()