def fizzBuzzModulus(inp):
    output = []
    for i in range(1, inp):
        if (i%3 == 0) and (i%5 == 0):
            output.append("FizzBuzz")
        elif i%3 == 0:
            output.append("Fizz")
        elif i%5 == 0:
            output.append("Buzz")
        else:
            output.append(str(i))
    return output

def fizzBuzzDict(inp):
    output = []
    fizz_buzz = {3: "Fizz", 5: "Buzz"}
    for i in range(1, inp):
        s = ""
        for key in fizz_buzz:
            if i%key == 0:
                s=s+fizz_buzz[key]
        if s == "":
            s = str(i)
        output.append(s)
    return output

# -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

def fizzBuzzDict(input):
    output = []
    fizz_buzz = {3: "Fizz", 5: "Buzz"}
    for i in range(1, input):
        s = ""
        for key in fizz_buzz:
            if i%key == 0:
                s = s + fizz_buzz[key]
        if s == "":
            s = str(i)
    return output

def fizzBuzzModulus(input):
    output = []
    for i in range(1, input):
        if (i%3 == 0) and (i%5 == 0):
            output.append("FizzBuzz")
        elif i%3 == 0:
            output.append("Fizz")
        elif i%5 == 0:
            output.append("Buzz")
        else:
            output.append(str(i))
    return output