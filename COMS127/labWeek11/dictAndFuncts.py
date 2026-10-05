# Jared Krug    11/2/2023    Lab Section: 6
# Finding the 27 possible combnations between +, -, and * and then using all of them with given integers

def inputValidInteger():
    while True:
        num = input("Enter an integer: ")
        try:
            num = int(num)
        except ValueError:
            print("ERROR, please enter an integer: ")
            continue
        break
    return num

def calcAAA(a,b,c,d): #1 +++
    return a+b+c+d
def calcSSS(a,b,c,d): #2 ---
    return a-b-c-d
def calcMMM(a,b,c,d): #3 ***
    return a*b*c*d
def calcAAS(a,b,c,d): #4 ++-
    return a+b+c-d
def calcASS(a,b,c,d): #5 +--
    return a+b-c-d
def calcSSA(a,b,c,d): #6 --+
    return a-b-c+d
def calcSAA(a,b,c,d): #7 -++
    return a-b+c+d
def calcASA(a,b,c,d): #8 +-+
    return a+b-c+d
def calcSAS(a,b,c,d): #9 -+-
    return a-b+c-d
def calcAAM(a,b,c,d): #10 ++*
    return a+b+c*d
def calcAMM(a,b,c,d): #11 +**
    return a+b*c*d
def calcMMA(a,b,c,d): #12 **+
    return a*b*c+d
def calcMAA(a,b,c,d): #13 *++
    return a*b+c+d
def calcAMA(a,b,c,d): #14 +*+
    return a+b*c+d
def calcMAM(a,b,c,d): #15 *+*
    return a*b+c*d
def calcSSM(a,b,c,d): #16 --*
    return a-b-c*d
def calcSMM(a,b,c,d): #17 -**
    return a-b*c*d
def calcMMS(a,b,c,d): #18 **-
    return a*b*c-d
def calcMSS(a,b,c,d): #19 *--
    return a*b-c-d
def calcSMS(a,b,c,d): #20 -*-
    return a-b*c-d
def calcMSM(a,b,c,d): #21 *-*
    return a*b-c*d
def calcASM(a,b,c,d): #22 +-*
    return a+b-c*d
def calcAMS(a,b,c,d): #23 +*-
    return a+b*c-d
def calcSMA(a,b,c,d): #24 -*+
    return a-b*c+d
def calcMSA(a,b,c,d): #25 *-+
    return a*b-c+d
def calcMAS(a,b,c,d): #26 *+-
    return a*b+c-d
def calcSAM(a,b,c,d): #27 -+*
    return a-b+c*d

def main():
    a = inputValidInteger()
    b = inputValidInteger()
    c = inputValidInteger()
    d = inputValidInteger()

    calculations = {}
    calculations["AAA"] = calcAAA(a,b,c,d)
    calculations["SSS"] = calcSSS(a,b,c,d)
    calculations["MMM"] = calcMMM(a,b,c,d)
    calculations["AAS"] = calcAAS(a,b,c,d)
    calculations["ASS"] = calcASS(a,b,c,d)
    calculations["SSA"] = calcSSA(a,b,c,d)
    calculations["SAA"] = calcSAA(a,b,c,d)
    calculations["ASA"] = calcASA(a,b,c,d)
    calculations["SAS"] = calcSAS(a,b,c,d)
    calculations["AAM"] = calcAAM(a,b,c,d)
    calculations["AMM"] = calcAMM(a,b,c,d)
    calculations["MMA"] = calcMMA(a,b,c,d)
    calculations["MAA"] = calcMAA(a,b,c,d)
    calculations["AMA"] = calcAMA(a,b,c,d)
    calculations["MAM"] = calcMAM(a,b,c,d)
    calculations["SSM"] = calcSSM(a,b,c,d)
    calculations["SMM"] = calcSMM(a,b,c,d)
    calculations["MMS"] = calcMMS(a,b,c,d)
    calculations["MSS"] = calcMSS(a,b,c,d)
    calculations["SMS"] = calcSMS(a,b,c,d)
    calculations["MSM"] = calcMSM(a,b,c,d)
    calculations["ASM"] = calcASM(a,b,c,d)
    calculations["AMS"] = calcAMS(a,b,c,d)
    calculations["SMA"] = calcSMA(a,b,c,d)
    calculations["MSA"] = calcMSA(a,b,c,d)
    calculations["MAS"] = calcMAS(a,b,c,d)
    calculations["SAM"] = calcSAM(a,b,c,d)

    for i in calculations:
        calculations[i] = str(calculations[i])
        print(i+": "+ calculations[i])

if __name__ == "__main__":
    main()