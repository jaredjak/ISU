def TwoSumLoop(list,target):
    for i in range(len(list)):
        for j in range(i+1, len(list)):
            if list[i] + list[j] == target:
                return i,j
            
def TwoSumLoopAll(list, target):
    returnList = []

    for i in range(len(list)):
        for j in range(i+1, len(list)):
            if list[i] + list[j] == target:
                tempList = [i,j]
                returnList.append(tempList)
    return returnList

def TwoSumDict(list, target):
    listToIndex = {}
    for i in range(len(list)):
        complement = target - list[i]

        if complement in listToIndex:
            return listToIndex[complement], i
        
        listToIndex[list[i]] = i

def TwoSumDictAll(list, target):
    returnList = []
    listToIndex = {}

    for i in range(len(list)):
        complement = target - list[i]

        if complement in listToIndex:
            tempList = [listToIndex[complement], i]
            returnList.append(tempList)
        
        listToIndex[list[i]] = i
    return returnList

#-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

def twoSumLoop(list, target):
    for i in range(len(list)):
        for j in range(i+1, len(list)):
            if list[i] + list[j] == target:
                return i,j
    
def twoSumDict(list, target):
    listIndex = {}

    for i in range(len(list)):
        complement = target - list[i]
        if complement in listIndex:
            return listIndex[complement], i
        listIndex[list[i]] = i