# Jared Krug    # 10/15/2023
# Testing code I wrote for pratice exam

# def getIntegerList():
#     returnList = []
#     while True:
#         rList = (input("Enter integers. * to quit: "))
#         if rList == "*":
#             break
#         else:
#             returnList.append(rList)
#     return returnList

# def sumOfOdds(list):
#     sum = 0
#     for i in list:
#         if i % 2 != 0:
#             sum += i
#     return sum

# def main():
#     inputList = getIntegerList()
#     inputInt = [eval(i) for i in inputList]

#     answer = sumOfOdds(inputInt)

#     print("Numbers picked: ",inputInt, "| Sum of odds: ",answer)


# if __name__ == "__main__":
#     main()
#     print("Goodbye!")

# -----------------------------------------------------------

def calculateFinalPrice(price, number, tax):
    grossPrice = price * number

    if number>5 and number<=10:
        discountPrice = grossPrice - (grossPrice*.05)
    elif number>10 and number<=15:
        discountPrice = grossPrice - (grossPrice*.1)
    elif number>15 and number<=20:
        discountPrice = grossPrice - (grossPrice*.15)
    elif number>20:
        discountPrice = grossPrice - (grossPrice*.2)
    else:
        discountPrice = 0

    finalPrice = discountPrice + (discountPrice*tax)
    return finalPrice

def main():
    pr = float(input("Enter Toy Price: "))
    num = int(input("Enter Number of Toys: "))
    tax = float(input("Enter Tax: "))

    finalPrice = calculateFinalPrice(pr, num, tax)
    print("The final price is: ${0}".format(finalPrice))

if __name__ == "__main__":
    main()