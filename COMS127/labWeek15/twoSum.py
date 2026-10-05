# Jared Krug    12/4/2023   Lab Section:6
#Finds the indices of a hardcoded list that add up to a given target value. Two find the first one they find while
# the other two find all possible solutions.


def twoSumLoops(list, tar):
    #taken from two-sum article shown in Lab Week 15 rubric - brute force
    for i in range(len(list)):
        for j in range(i+1, len(list)):
            if list[i] + list[j] == tar:
                return i,j

def twoSumDict(list, tar):
    #taken from two-sum article shown in Lab Week 15 rubric - hash table
    listToIndex = {}
    for i in range(len(list)):
        complement = tar - list[i]

        if complement in listToIndex:
            return listToIndex[complement], i
        listToIndex[list[i]] = i

def twoSumLoopsAll(list, tar):
    returnList = []
    for i in range(len(list)):
        for j in range(i+1, len(list)):
            if list[i] + list[j] == tar:
                list1 = [i, j]
                returnList.append(list1)
    return returnList

def twoSumDictAll(list, tar):
    returnList = []
    #taken from two-sum article shown in Lab Week 15 rubric - hash table
    listToIndex = {}
    for i in range(len(list)):
        complement = tar - list[i]

        if complement in listToIndex:
            list1 = [listToIndex[complement], i]
            returnList.append(list1)
        listToIndex[list[i]] = i
    return returnList

def main():
    listInt = [1,2,3,4,5,6,7,8,9]
    targetVal = 5

    #finds first solution to find target value but inefficiently
    indice1, indice2 = twoSumLoops(listInt, targetVal)
    print(indice1, indice2)

    #finds first solution to find target value but efficiently
    ind3, ind4 = twoSumDict(listInt, targetVal)
    print(ind3, ind4)

    #finds all solutions to find target value but inefficiently
    list1 = twoSumLoopsAll(listInt, targetVal)
    print(list1)

    #finds all solutions to find target value but efficiently
    list2 = twoSumDictAll(listInt, targetVal)
    print(list2)

if __name__ == "__main__":
    main()
