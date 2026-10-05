

def calculateFood(numTots, numShrimp, numQueso):
    amt = 0
    while numTots >= 1 and numShrimp >= 2 and numQueso >= 5:
        amt += 1
        numTots -= 1
        numShrimp -= 2
        numQueso -= 5
    return amt

def main():
    tots = int(input("Enter tortilla amount: "))
    shrimp = int(input("Enter shrimp count: "))
    queso = int(input("Enter queso count: "))
    foodAmt = calculateFood(tots, shrimp, queso)
    print("Amount of tacos: {0}".format(foodAmt))

if __name__ == "__main__":
    main()