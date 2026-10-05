# Jared Krug     10/25/2023       Lab Section: 6
# Swapping the words in a dictionary to random values from the same dictionary. Given user input

import random

def main():
    sent = {}
    sentInput = input("Enter a sentence: ")
    sentList = sentInput.split(" ")
    for word in sentList:
        if (word not in sent.keys()):
            sent[word] = random.choice(list(sentList))
    print(sent)
    valuesList = list(sent.values())
    print(*valuesList)

if __name__ == "__main__":
    main()


# Running: python .\wordSwap.py
# Enter a Sentence: The cute red cat jumped over the fence!
# {'The': 'cute', 'cute': 'jumped', 'red': 'cat', 'cat': 'over', 'jumped': 'the', 'over': 'red', 'the': 'T
# cute jumped cat over the red The fence!
# Enter a Sentence: asdf qwer asdf zxcv asdf zxcv zxcv
# {'asdf': 'zxcv', 'qwer': 'asdf', 'zxcv': 'qwer'}
# zxcv asdf zxcv qwer zxcv qwer qwer
# Enter a Sentence: 1 2 3 1 1 1 2 3 2 3 4
# {'1': '3', '2': '2', '3': '4', '4': '1'}
# 3 2 4 3 3 3 2 4 2 4 1