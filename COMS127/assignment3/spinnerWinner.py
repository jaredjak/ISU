# Jared Krug       9/27/2023
# Assignment 3
# Creating a game that is essentially a gambling game where you gain or lose points based on spins. 
# Two options: p1 vs p2 or p1 v ai

import random

# NOTE: Your functions should go here!
def printTitleMaterial():
    """ This function prints the 'title material' that prints out when the program starts.
    """
    print("Spinner Winner!")
    print()


    print("By: Jared Krug")
    print("[COM S 127 G]")
    print()

def initialChoice():
    """ This function allows the player to make various choices when starting the game. This is an 
    example of the 'do-while' pattern.

    :return String: The choice the player has made when starting the program to [p]lay the game, view the
    [i]nstructions, or [q]uit the program..
    """
    choice = input("Choice? [p]lay, [i]nstructions, [q]uit: ")
    while choice != "p" and choice != "i" and choice != "q":
        print("ERROR: Please enter 'p', 'i', or 'q'...")
        choice = input("Choice? [p]lay, [i]nstructions, [q]uit: ")
    return choice

def chooseNumPlayers():
    """ This function allows the player to choose whether they will play against another
    human, or play against the computer.

    :return Integer: The number of players in the game.
    """
    numPlayers = 0
    # TODO: Use the 'do-while' pattern to take in input for how many players will play the game
    # NOTE: The only valid inputs should be '1' and '2'
    #       If the user chooses '1,' then they play against the computer
    #       If the user chooses '2,' then they play against another human
    numPlayers = int(input("How many players? [1] or [2]? "))
    while numPlayers != 1 and numPlayers != 2:
            print("ERROR: Can only accept inputs '1' or '2', but entered instead was:", numPlayers)
            numPlayers = int(input("How many players? [1] or [2]? "))
    return numPlayers

def wait():
    """ This function has the computer 'wait' until the [Enter] key is pressed. This allows 
    for better 'readability' in the final output.
    """
    input("Press [Enter] To Continue...")
    print()

def printBanner():
    """ Prints the 'banner' between each round of the game so that the output text does not 
    get too 'messy.'
    """
    print()
    print("#######################################################################")
    print()
    print("~~ Starting New Round ~~")
    print()

def printPoints(playerNum, points):
    """ This function prints the number of points a certain player currently has.

    :param Integer playerNum: The player whose points are being displayed.
    :param Integer points: The number of points to be displayed
    """
    print("* Player {0} Has {1} Points!".format(playerNum, points))
    print()

def wagerPointsHuman(playerNum, points):
    """ This function uses the 'do-while' pattern to take in input for the number of points to be wagered. It checks to
    make sure that the player has entered a valid amount of points. Meaning, the player cannot wager more points than
    they have, nor zero points, nor a negative number of points. The function then returns the wager.

    As there could potentially be two human players, this function requires the playerNum to know which player to include
    in any printouts.

    :param Integer playerNum: The player to include in any printouts. Can be 1 or 2.
    :param Integer points: The number of points the specified player currently has.
    :return Integer: The number of points to be wagered.
    """
    if playerNum == 1:
        print("* Player", playerNum, "has", points,"points!")
        print()
        wager = 0
        while wager == 0:
            wager = int(input("* Player 1 (HUMAN) - How many points to wager?: "))
            if wager <= points and wager > 0:
                return wager
            else:
                print("* Please wager an amount you have/more than zero...")
                wager = 0
        return wager

    if playerNum == 2:
        print("* Player", playerNum, "has", points,"points!")
        print()
        wager = 0
        while wager == 0:
            wager = int(input("* Player 2 (HUMAN) - How many points to wager?: "))
            if wager <= points and wager > 0:
                return wager
            else:
                print("* Please wager an amount you have/more than zero...")
                wager = 0
        return wager

def wagerPointsAI(playerNum, points):
    """ This function should choose a random number between 1 and the number of points the AI has (inclusive). For example,
    if the computer has 5 points, it can wager either 1, 2, 3, 4, or 5. This function should include a printout similar to 
    what is printed when the human wagers points.

    :param Integer playerNum: The player to include in any printouts. While this will usually be 2, including this value allows 
                              for a future version of the game with 2 computer players.
    :param Integer points: The number of points the specified player currently has.
    :return Integer: The number of points to be wagered.
    """
    playerNum = playerNum + 1
    wager = 0
    print("* Player", playerNum, "has", points,"points!")
    print()
    wager = random.randrange(1, points+1)
    print("* Player 2 (AI) - How many points to wager?:",wager)
    return wager

def generateTargetValue(numSpinners, spinnerLow, spinnerHigh):
    """ This function generates the 'target value' that the players try to match. This number is generated by summing together
    'numSpinners' number of spinners, between 'spinnerLow' and 'spinnerHigh' (inclusive). For example, of there are 3 spinners, 
    and a spinner can have values between 1 - 3, then the target value would be the summation of 3 random values between 1 and 3
    (inclusive).

    :param Integer numSpinners: The number of spinners used in the game.
    :param Integer spinnerLow: The smallest value a spinner can generate.
    :param Integer spinnerHigh: The highest value a spinner can generate.
    :return Integer: The target value generated by summing together 'numSpinners' number of random numbers between 'spinnerLow' and
                     'spinnerHigh' (inclusive).
    """
    target = 0
    numSpinners = random.randrange(1, numSpinners + 1)
    for i in range(numSpinners):
        sum = random.randrange(spinnerLow, spinnerHigh + 1)
        target = target + sum
    return target

def getSpinnerChoiceHuman(playerNum, target, numSpinners, spinnerLow, spinnerHigh):
    """ This function gets the number of spinners that the human wants to spin. It should print out the 'target value' that the player
    is trying to match, as well as the values that a spinner can produce (ex: 1 - 3), and the number of spinners that can be spun. The
    player cannot pick more spinners than are in the game, nor can the pick zero spinners, nor can they pick a negative number of spinners.

    :param Integer playerNum: The player to include in any printouts. Can be 1 or 2.
    :param Integer target: The 'target value' the player is trying to match.
    :param Integer numSpinners: The total number of spinners in the game.
    :param Integer spinnerLow: The smallest value a spinner can generate.
    :param Integer spinnerHigh: The highest value a spinner can generate.
    :return Integer: The number of spinners the player chooses to spin.
    """

    print("* Your target value is: ",target)
    print("* A single spinner can produce values between ",spinnerLow,"-",spinnerHigh)

    if playerNum == 1:
        spinnerChoice = 0
        while spinnerChoice == 0:
            spinnerChoice = int(input("* Player 1 (HUMAN) - How many spinners would you like to spin (1 - 3)?: "))
            if spinnerChoice >= 1 and spinnerChoice <= numSpinners:
                x = spinSpinners(playerNum, spinnerChoice, target, spinnerLow, spinnerHigh)
                return x
            else:
                print("* Please enter a valid option between 1 and",numSpinners)
                spinnerChoice = 0
    
    else:
        spinnerChoice = 0
        while spinnerChoice == 0:
            spinnerChoice = int(input("* Player 2 (HUMAN) - How many spinners would you like to spin (1 - 3)?: "))
            if spinnerChoice >= 1 and spinnerChoice <= numSpinners:
                x = spinSpinners(playerNum, spinnerChoice, target, spinnerLow, spinnerHigh)
                return x
            else:
                print("* Please enter a valid option between 1 and",numSpinners)
                spinnerChoice = 0
    

def getSpinnerChoiceAI(playerNum, target, numSpinners, spinnerLow, spinnerHigh):
    """ This function gets the number of spinners that the computer wants to spin. This number should be a randomly generated value
    between 1 and numSpinners (inclusive). It should print out text similar to what the 'getSpinnerChoiceHuman()' function produces.

    :param Integer playerNum: The player to include in any printouts. While this will usually be 2, including this value allows 
                              for a future version of the game with 2 computer players.
    :param Integer target: The 'target value' the computer is trying to match. The computer does not take this value into account when
                           choosing the number of spinners - it should be used for printouts, however.
    :param Integer numSpinners: The total number of spinners in the game.
    :param Integer spinnerLow: The smallest value a spinner can generate.
    :param Integer spinnerHigh: The highest value a spinner can generate.
    :return Integer: The number of spinners the computer chooses to spin.
    """
    playerNum = playerNum+1

    spinnerChoice = 0
    print("* Your target value is: ",target)
    print("* A single spinner can produce values between ",spinnerLow,"-",spinnerHigh)
    spinnerChoice = random.randrange(1, numSpinners+1)
    print("* Player",playerNum,"(AI) - How many spinners would you like to spin (1 - 3)?: ",spinnerChoice)
    x = spinSpinners(playerNum, spinnerChoice, target, spinnerLow, spinnerHigh)
    return x

def spinSpinners(playerNum, spinnerChoice, target, spinnerLow, spinnerHigh):
    """ This function can be used for either human or computer players, and it calculates the summed values of the number of
    spinner spins. For example, if the player chooses to spin 3 spinners, and these spinners can have values between 1 and 3,
    the player could spin values of 2, 3, and 1 for a total of 6. This is the value the function would return.

    This function should print out the results of each spin as each spin is spun. The function should then print the sum of 
    all the spins and the target value once all the spins are complete.

    Please note - the winner of the round is *not* calculated here - only the spinner totals.

    :param Integer playerNum: The player to include in any printouts. Can be 1 or 2.
    :param Integer spinnerChoice: The number of spinners the player wishes to spin.
    :param Integer target: Use this value in the printout so the user can compare what they spun compared to the target.
    :param Integer spinnerLow: The smallest value a spinner can generate.
    :param Integer spinnerHigh: The highest value a spinner can generate.
    :return Integer: The sum of all the spinner spins.
    """
    spinVal = 0 # Assign the output of a random number between spinnerLow and spinnerHigh (inclusive) to this variable.
    spinTotal = 0 # Sum the spinVal values together with this variable.
    print()

    for i in range(spinnerChoice):
        spinVal = random.randrange(spinnerLow, spinnerHigh+1)
        print("* Player",playerNum,"has spun a",spinVal)
        spinTotal = spinTotal + spinVal
        spinVal = 0
    print("* The total value of the spinner(s) was:",spinTotal,", and the 'target value' was:",target)
    return spinTotal

def main():
    """ This is the main function that executes when the game is started from the terminal. It contains all of the logic/ states
    necessary to play the game.
    """
    # main script running control variable
    running = True
    
    # gameplay variables
    SPINNER_LOW = 1
    SPINNER_HIGH = 3
    NUM_SPINNERS = 3
    INITIAL_POINTS = 10
    player1Points = INITIAL_POINTS
    player2Points = INITIAL_POINTS

    # print the title/ author information
    printTitleMaterial()

    # play the game
    while running:
        choice = initialChoice()
        if choice == "p":

            numPlayers = chooseNumPlayers()

            # main game loop
            while True:
                # round setup
                printBanner()

                # TODO: Complete the logic of the game (4 pts.)

                # Find a target value.
                tar = generateTargetValue(NUM_SPINNERS, SPINNER_LOW, SPINNER_HIGH)
                print("** Your target value is: ",tar)
                print()
                wait()

                # Player 1 wager. (Player 1 will always be human.)
                if numPlayers == 1:
                    wager1 = wagerPointsHuman(numPlayers, player1Points)
                else:
                    numPlayers = numPlayers-1
                    wager1 = wagerPointsHuman(numPlayers, player1Points)
                    numPlayers = numPlayers+1
                print()
                wait()

                # Player 2 wager. (Player 2 can be either human or AI - you must account for both.)
                if numPlayers == 1:
                    wager2 = wagerPointsAI(numPlayers, player2Points)
                else:
                    wager3 = wagerPointsHuman(numPlayers, player2Points)
                print()
                wait()

                # Player 1 spin - get the total of all the spinners. (Player 1 will always be human.)
                if numPlayers == 1:
                    gsch = getSpinnerChoiceHuman(numPlayers, tar, NUM_SPINNERS, SPINNER_LOW, SPINNER_HIGH)
                else:
                    numPlayers = numPlayers-1
                    gsch = getSpinnerChoiceHuman(numPlayers, tar, NUM_SPINNERS, SPINNER_LOW, SPINNER_HIGH)
                    numPlayers = numPlayers+1
                print()
                wait()
                
                # Player 2 spin - get the total of all the spinners. (Player 2 can be either human or AI - you must account for both.)
                if numPlayers == 1:
                    gscai = getSpinnerChoiceAI(numPlayers, tar, NUM_SPINNERS, SPINNER_LOW, SPINNER_HIGH)
                else:
                    gsch2 = getSpinnerChoiceHuman(numPlayers, tar, NUM_SPINNERS, SPINNER_LOW, SPINNER_HIGH)
                print()
                wait()
                # Calculate Winner of the round. (If Player1 is closer, Player 1 wins. Else if Player 2 is closer Player 2 wins. If they are equal it is a draw.)
                if numPlayers == 1:
                    # Checked if absolute value was actually a function in python. abs()
                    if abs(gsch - tar) < abs(gscai - tar):
                        player1Points = player1Points+wager1
                        player2Points = player2Points-wager2
                        print("** Player 1 Won That Round! **")
                    elif abs(gsch - tar) > abs(gscai - tar):
                        print("** Player 2 Won That Round! **")
                        player2Points = player2Points+wager2
                        player1Points = player1Points-wager1
                    else:
                        print("** It was a draw! **")
                else:
                    if abs(gsch - tar) < abs(gsch2 - tar):
                        print("** Player 1 Won That Round! **")
                        player1Points = player1Points+wager1
                        player2Points = player2Points-wager3
                    elif abs(gsch - tar) > abs(gsch2 - tar):
                        print("** Player 2 Won That Round! **")
                        player1Points = player1Points-wager1
                        player2Points = player2Points+wager3
                    else:
                        print("** It was a draw! **")
                print()
                wait()
                # Print the points for both players.
                print("* Player 1 has",player1Points,"points!")
                print()
                print("* Player 2 has",player2Points,"points!")
                print()
                wait()
                # Check of the game is over - if it is, print a 'game over' message, reset the points to default values, 
                # and break out of the gameplay loop. Otherwise, print that it is the end of the round.
                if player1Points == 0:
                    print("Player 1 lost. That's the game!")
                    print()
                    player1Points = INITIAL_POINTS
                    player2Points = INITIAL_POINTS
                    break
                elif player2Points == 0:
                    print("Player 2 lost. That's the game!")
                    print()
                    player1Points = INITIAL_POINTS
                    player2Points = INITIAL_POINTS
                    break
                else:
                    print("~~ End Of Round ~~")

        elif choice == "i":
            print('''
    The 'algorithm' of the game is thus:
                  
    The user selects a game between one or two players.
        A one player game has the user play against the computer.
        A two player game has two users play against one another.
                  
    The players start the game with a certain number of 'points.'
                  
    The game is divided into 'rounds.'
        At the start of the 'round,' the computer generates a 'target value.'
            This is the value that the players try to match.
        At the start of the 'round,' each player 'wagers' a certain number of 'points.'
            Players cannot 'wager' more 'points' than they have. Nor can they 'wager' zero (0)
            'points.' Nor can they 'wager' a negative number of 'points.'
        Then each player decides how many 'spinners' to spin.
            Each spin adds its value to a final 'spin value' for the round.
            Players can not spin more spinners than are available to be spun. Nor can the spin
            zero (0) spinners. Nor can they spin a negative number of spinners.
        After each player spins their 'spinners,' their 'spin value' is compared against the 'target
        value.'
        The player who gets their 'spin value' closest to the 'target value' is the winner of the round.
            If both players 'spin value' are equally distant from the 'target value' the round is a
            'draw.'
        Once the winner is decided, 'points' are added and subtracted from each player's score.
            The winning player gets their wager amount added to their score.
            The losing player gets their wager amount subtracted from their score.
        The game continues until one player is completely out of 'points.'
                  ''')
        elif choice == "q":
            running = False
            print()
            print("Bye Bye! Hope you had a good time!")
            print()
        else:
            print("ERROR: Variable 'choice' should have been 'p', 'i', or 'q', but instead was:", choice)
            quit()

if __name__ == "__main__":
    main()
