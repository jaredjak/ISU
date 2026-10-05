# Jared Krug             11/05/2023
# Assignment 5 - create a to do list for yourself, saves after code ends, can return later to edit or make new lists

import sys
import pickle

def printTitleMaterial():
    """Prints the title material for the game, including the student's name, class, and section number.
    """
    print("The Ultimate TODO List!")
    print()

    print("By: Jared Krug")
    print("[COM S 127 Section:6]")
    print()

def initList():
    """Create a Dictionary of Lists - this is the primary data structure for the script.

    :return Dictionary of Lists: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    """
    todoList = {}
    todoList["backlog"] = []
    todoList["todo"] = []
    todoList["in_progress"] = []
    todoList["in_review"] = []
    todoList["done"] = []

    return todoList

def checkIfListEmpty(todoList):
    """This function checks if there are any entries in the Dictionary of Lists data structure.

    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    :return Boolean: If there is at least one item in the data structure, return False - it is not empty. Otherwise return True.
    """
    if (len(todoList["backlog"]) > 0 or 
        len(todoList["todo"]) > 0 or
        len(todoList["in_progress"]) > 0 or
        len(todoList["in_review"]) > 0 or
        len(todoList["done"]) > 0):
        return False
    return True

def saveList(todoList):
    """Allows the user to save their data to a binary file.

    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    """
    try:
        listName = input("Enter List Name (Exclude .lst Extension): ")
        with open("./" + listName + ".lst", "wb") as pickle_file:
            pickle.dump(todoList, pickle_file)
    except:
        print("ERROR (saveList): ./{0}.lst is not a valid file name!".format(listName))
        sys.exit()

def loadList():
    """Allows the user to load their data from a binary file.

    :return Dictionary of Lists: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    """
    try:
        listName = input("Enter List Name (Exclude .lst Extension): ")
        with open("./" + listName + ".lst", "rb") as pickle_file:
            todoList = pickle.load(pickle_file)
    except:
        print("ERROR (loadList): ./{0}.lst was not found!".format(listName))
        sys.exit()
    
    return todoList

def checkItem(item, todoList):
    """This function iterates through all the keys in the dictionary, and checks each list to see if a key is present.

    :param String item: The String to search for in each list.
    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    :return Boolean, String, Integer: This function returns True/ False depending on whether the item was found, the String of the keyName, and the index in the list where the item was found.
    """
    itemFound = False
    keyName = ""
    index = -1
    # TODO: Iterate through all the keys in the dictionary and check each list of each key to  
    # see if an item is present. If it is, set itemFound to be 'True.' Then, set the keyName variable  
    # to the key where the item was found, and the index in the list where the item was found. 
    # If the item is not found in any of the lists in the dictionary, keep the default values above. 
    # Return the itemFound boolean, the keyName string, and the index integer (1 pt.)
    for key in todoList.keys():
        # index = 0 # Silly error causing index
        for values in todoList[key]:
            index = index + 1
            if values == item:
                itemFound = True
                keyName = key
                return itemFound, keyName, index

    return itemFound, keyName, index

def addItem(item, toList, todoList):
    """This function allows the user to add an item to a specific list in the todoList data structure.

    :param String item: The String to search for in each list.
    :param String toList: The key in the dictionary whose list to add the item to.
    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    :return Dictionary of Lists: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    """
    # TODO: Use the checkItem function to see if the specified 'item' already exists in the dictionary
    # of lists. If the item is not in the Dictionary of Lists, add it to the list specified by the 'toList'
    # variable. If the item is already in the Dictionary of Lists, print an error message specifying:
    # - the name of the item
    # - the keyName of the list where the item is found
    # - the index of the item in the list
    # Return the todoList data structure after it has been modified (or not) (1 pt.)
    itemFound, keyName, index = checkItem(item, todoList)
    if itemFound == False:
        toList.append(item)
    else:
        print("Error, item: {0}, key name: {1}, index: {2}.".format(item, keyName, index))
        print()

    return todoList

def deleteItem(item, todoList):
    """This function allows the user to delete an item in the todoList data structure.

    :param String item: The String to search for in each list.
    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    :return Boolean, Dictionary of Lists: This function returns True/ False depending on whether the item was found, and the modified Dictionary of Lists data structure.
    """
    # TODO: Use the checkItem function to see if the specified 'item' already exists in the dictionary
    # of lists. If the item is already in the Dictionary of Lists, delete that item. If the item is not
    # in the Dictionary of Lists, print an error message specifying:
    # - the name of the item
    # Return the itemFound boolean and the todoList data structure after it has been modified (or not) (1 pt.)
    itemFound, keyName, index = checkItem(item, todoList)
    if itemFound == True:
        # todoList[keyName].pop(index) # Doesn't work. sad
        del todoList[keyName][index]
    else:
        print("Error, {0} not found".format(item))

    return itemFound, todoList

def moveItem(item, toList, todoList):
    """This function allows the user to move an item from one List in the Dictionary of Lists to another.

    :param String item: The String to search for in each list.
    :param String toList: The key in the dictionary whose list to add the item to.
    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    :return Dictionary of Lists: This function returns the modified Dictionary of Lists data structure.
    """
    # TODO: Use the deleteItem function to search for/ delete the specified item. Use the itemFound boolean
    # it returns to determine if the item was found. If it was, use the addItem function to add the item to  
    # the specified toList key.
    # Return the todoList data structure after it has been modified (or not) (1 pt.)
    itemFound, todoList = deleteItem(item, todoList)
    if itemFound == True:
        addItem(item, toList, todoList)

    return todoList

def printTODOList(todoList):
    """This function prints out the contents of the Dictionary of Lists data structure.

    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    :return None: After printing out the contents of the Dictionary of Lists data structure, we are explicitly returning 'None.'
    """
    # TODO: Iterate through all the keys in the dictionary and print both the key and the list the dictionary
    # holds for that key on a single line on the screen.
    # ex: todo: ['laundry', 'dishes']
    # Return None (1 pt.)
    for key, value in todoList.items():
        print("{0}: {1}".format(key, value))

    return None

def runApplication(todoList):
    """This function provides the primary funcionality for the application. It allows the user to add items to the data structure, move items,
    delete items, save the data structure to a binary file, and return to the main menu.

    :param Dictionary of Lists todoList: A dictionary whose keys contain the various categories the user can access. The values are lists the user can modify.
    :return Dictionary of Lists: This function returns the modified Dictionary of Lists data structure.
    """
    while True:
        print("-----------------------------------------------------------------")
        choice = input("APPLICATION MENU: [a]dd to backlog, [m]ove item, [d]elete item, [s]ave list, or [q]uit to main menu?: ")
        print()

        if choice == "a":
            # TODO: Prompt the user to enter an item, and take in that input as a string. Call the addItem function
            # to add the item to the 'backlog' key's corresponding list. Finally, use the printTODOList function
            # to print the todoList data structure. (1 pt.)
            item1 = input("Enter an item: ")
            addItem(item1, todoList["backlog"], todoList)
            print()
            printTODOList(todoList)
            
        elif choice == "m":
            # TODO: Use the provided checkIfListEmpty() function to check to see if any of the lists in the data 
            # structure have items in them. 
            tORf = checkIfListEmpty(todoList)
            # If checkIfListEmpty() returns False, meaning that at least one of the lists in the dictionary has 
            # an item in it, do the following:
            # - Prompt the user to enter an item, and take in that input as a string.
            # - Use the checkItem function to confirm if that item is in the data structure.
            # - While the item is not in the data structure, print an error message stating the item does not exist
            #   and then prompt the user to enter a different item. Use the checkItem function again to confirm if 
            #   the new item is inside the data structure.
            # - Prompt the user to enter a dictionary key for the list to move the item to, and take in that input 
            #   as a string.
            # - While the dictionary key the user specifies is not a key that is in the dictionary, print an error
            #   message stating that the key does not exist. Then, prompt the user to enter a new dictionary key.
            # - Call the moveItem function, passing the item, dictionary key, and todoList as arguments.
            if tORf == False:
                item2 = input("Enter An Item To Move: ")
                itemFound, keyName, index = checkItem(item2, todoList)
                while itemFound == False:
                    print("Error, item does not exist")
                    item2 = input("Enter An Item To Move: ")
                    itemFound, keyName, index = checkItem(item2, todoList)
                
                moveto = input("Enter The List To Move {0} To: ".format(item2))
                while moveto not in todoList.keys():
                    print("Error, {0} is not an existing key".format(moveto))
                    moveto = input("Enter The List To Move {0} To: ".format(item2))
                print()
                moveItem(item2, todoList[moveto], todoList)
            # If all of the lists in the dictionary are empty, print an error message that states 'No items to move!' 
            # or something similar.
            else:
                print("There aren't any items to move!")
                print()
            # Finally, whether or not the data structure is totally empty or not, call the printTODOList function
            # and print out the data structure. (1 pt.)
            printTODOList(todoList)
        elif choice == "d":
            # TODO: Use the provided checkIfListEmpty() function to check to see if any of the lists in the data 
            # structure have items in them. 
            tORf = checkIfListEmpty(todoList)
            # If checkIfListEmpty() returns False, meaning that at least one of the lists in the dictionary has 
            # an item in it, do the following:
            # - Prompt the user to enter an item, and take in that input as a string. 
            # - Call the deleteItem function to remove the item from the data structure if it is present. 
            if tORf == False:
                item3 = input("Enter An Item To Delete: ")
                deleteItem(item3, todoList)
            # If all of the lists in the dictionary are empty, print an error message that states 'No items to move!' 
            # or something similar.
            else:
                print("There aren't any items to delete!")
                print()
            # Finally, whether or not the data structure is totally empty or not, call the printTODOList function
            # and print out the data structure. (1 pt.)
            printTODOList(todoList)
            # HINT: the deleteItem function returns two (2) values - do you actually need to do things with both of them?

        elif choice == "s":
            saveList(todoList)
            print("Saving List...")
            print()
            printTODOList(todoList)
        elif choice == "q":
            print("Returning to MAIN MENU...")
            print()
            break
        else:
            print("ERROR: Please enter [a], [m], [d], [s], or [q].")
            print()

    return todoList

def main():
    """This is the main() function for the program. It contains all of the initial choices the user can make. These choices include:
    - Starting a new Dictionary of Lists.
    - Loading a previously saved Dictionary of Lists.
    - Quitting the script altogether.
    """
    taskOver = False

    printTitleMaterial()

    while taskOver == False:
        print("-----------------------------------------------------------------")
        choice = input("MAIN MENU: [n]ew list, [l]oad list, or [q]uit?: ")
        print()
        if choice == "n": 
            todoList = initList()

            printTODOList(todoList)
            
            runApplication(todoList)
        elif choice == "l":
            todoList = loadList()

            printTODOList(todoList)
            
            runApplication(todoList)
        elif choice == "q":
            taskOver = True
            print("Goodbye!")
            print()
        else:
            print("Please enter [n], [l], or [q]...")
            print()

if __name__ == "__main__":
    main()