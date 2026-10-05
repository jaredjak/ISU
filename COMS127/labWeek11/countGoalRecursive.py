# Jared Krug    11/2/2023    Lab Section: 6
# Use recursion to countdown to the goal. test print() function befire and after recursion

def countDownGoalRecursive(n, goal):
    #Base case
    if n<goal:
        return
    #Recursion
    else:
        print(n)
        countDownGoalRecursive(n-1,goal)

def countUpGoalRecursive(n, goal):
    #Base case
    if n<goal:
        return
    #Recursion
    else:
        countUpGoalRecursive(n-1, goal)
        print(n)

def main():
    countDownGoalRecursive(3, 1)
    print()
    countUpGoalRecursive(3, 1)

if __name__ == "__main__":
    main()