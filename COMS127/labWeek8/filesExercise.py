# Jared Krug     10/11/2023       Lab Section: 6
# Finding the average scores of students given two files and throwing it in another file!

def avg(sum, total):
    convInt = [eval(i) for i in sum[0:len(sum)]]

    newSum = 0
    for i in convInt:
        newSum = newSum + i
    average = newSum/total
    return average, newSum

def main():
    with open("students.txt", "r") as stu_file:
        top_line = stu_file.readline()
        students = []
        for line in stu_file:
            line = line.split(",")
            for i in range(0,len(line)):
                line[i] = line[i].strip()
            students.append(line)

    with open("scores.txt", "r") as stu_score:
        top_line = stu_score.readline()
        scores = []
        for line in stu_score:
            line = line.split(",")
            for i in range(0,len(line)):
                line[i] = line[i].strip()
            scores.append(line)
    # print(scores)

    # print("Student ID,Name,Total Scores,Sum of All Scores,Score Average")

    sum = []
    total = 0
    newGrades = []
    grades = []

    for id in students:

        for scoreid in scores:
            if id[0] == scoreid[0]:
                sum.append(scoreid[2])
                total = total + 1
                averages, newSum = avg(sum, total)
        grades = [id[0], id[1], total, newSum, averages]
        newGrades.append(grades)
        # print("{0},{1},{2},{3},{4}".format(id[0],id[1],total,newSum,averages))
    # print(newGrades)
  
    with open("grades.txt", "w") as finalGrades:
        finalGrades.write("Student ID,Name,Total Scores,Sum of All Scores,Score Average\n")

        for i in newGrades:
            finalGrades.write(str(i)+"\n")

if __name__ == "__main__":
    main()