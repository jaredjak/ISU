# Jared Krug    11/2/2023    Lab Section: 6
# Draw the 'call stack' of this code into enginerring notebook

def catStrike(x):
    print("Meow", x, "!")

def catAttack(cat):
    for i in range(0, len(cat)):
        catStrike(cat[i])

def main():
    catAttack("TOM")

if __name__ == "__main__":
    main()
    print()