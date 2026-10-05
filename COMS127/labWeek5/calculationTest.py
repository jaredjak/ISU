# Jared Krug             9/20/2023         Lab Section: 6
# Lab Week 5
# Using myShapes.py and myTemps.py to do calculations based on user input through functions

import myShapes
import myTemps

def getInput():
    a = int(input("Enter an integer please: "))
    return a

def main():
    blah = True
    while blah:
        choice = input("Choice?: [cs]cube surface, [cv]cube volume, [sv]sphere volume, [ss]sphere surface,\n [ck]cel to kel, [kc]kel to cel, [fk]fahr to kel, [kf]kel to fahr,\n [cf]cel to fahr, [fc]fahr to cel, [q]quit: ",)
        if choice == "cs":
            a = getInput()
            answer = myShapes.cubeSurface(a)
            print("The answer is: {0}".format(answer))
        elif choice == "cv":
            a = getInput()
            answer = myShapes.cubeVolume(a)
            print("The answer is: {0}".format(answer))
        elif choice == "sv":
            a = getInput()
            answer = myShapes.sphereVolume(a)
            print("The answer is: {0}".format(answer))
        elif choice == "ss":
            a = getInput()
            answer = myShapes.sphereSurface(a)
            print("The answer is: {0}".format(answer))
        elif choice == "ck":
            a = getInput()
            answer = myTemps.cToK(a)
            print("The answer is: {0}".format(answer))
        elif choice == "kc":
            a = getInput()
            answer = myTemps.kToC(a)
            print("The answer is: {0}".format(answer))
        elif choice == "fk":
            a = getInput()
            answer = myTemps.fToK(a)
            print("The answer is: {0}".format(answer))
        elif choice == "kf":
            a = getInput()
            answer = myTemps.kToF(a)
            print("The answer is: {0}".format(answer))
        elif choice == "cf":
            a = getInput()
            answer = myTemps.cToF(a)
            print("The answer is: {0}".format(answer))
        elif choice == "fc":
            a = getInput()
            answer = myTemps.fToC(a)
            print("The answer is: {0}".format(answer))
        elif choice == "q":
            print("Adios amigo")
            blah = False
        else:
            print("ERROR. Please put in valid input...")

if __name__ == "__main__":
    main()