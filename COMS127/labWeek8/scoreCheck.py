# Jared Krug     10/11/2023       Lab Section: 6
# Finding a threshold of scores via the studentData.txt file

def threshy(conv,thresh):
    threshG = 0
    threshL = 0
    for i in conv:
        if i >= thresh:
            threshG = threshG + 1
        else:
            threshL = threshL + 1
    return threshG,threshL

def main():
    fileref = open("studentData.txt", "r")

    threshold = int(input("Enter a threshold for the students scores: "))

    for aLine in fileref:
        values = aLine.split()
        convInt = [eval(i) for i in values[1:len(values)]]

        for i in convInt:
            gScores, lScores = threshy(convInt, threshold)

        print(values[0]+":", gScores, "scores >=", threshold,"|", lScores,"scores <", threshold)
    fileref.close()
    
if __name__ == "__main__":
    main()