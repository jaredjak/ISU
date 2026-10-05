# Jared Krug    12/4/2023   Lab Section:6
# Making the fizzbuzz modulus game in python.

def fizzBuzzModulus(input1):
    #Most of the following code is take from fizz buzz article given in lab week 15
    output = []
    for i in range(1, input1+1):
        if (i % 3 == 0) and (i % 5 == 0):
            output.append("FizzBuzz")
        elif (i % 3) == 0:
            output.append("Fizz")
        elif (i % 5) == 0:
            output.append("Buzz")
        elif (i % 7) == 0:
            output.append("Bazz")
        else:
            output.append(str(i))
    return output

def fizzBuzzDict(input1):
    output = []
    fizz_buzz = {3: "Fizz", 5: "Buzz"}
    for i in range(1, input1+1):
        s = ""
        for key in fizz_buzz:
            if i % key == 0:
                s = s + fizz_buzz[key]
        if s == "":
            s = str(i)
        output.append(s)
    return output

def main():
    inp = int(input("Please enter an integer: "))
    output = fizzBuzzModulus(inp)
    print(output)

    output2 = fizzBuzzDict(inp)
    print(output2)

if __name__ == "__main__":
    main()