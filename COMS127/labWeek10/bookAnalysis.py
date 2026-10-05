# Jared Krug         10/26/2023          Lab Section: 6
# Counting the words in a .txt file of your choice (as long as it is on the device)

def analyzeBook(title):
    with open('{}.txt'.format(title), 'r') as f:

        count = {}
        for line in f:
            for word in line.split():

                # remove punctuation
                word = word.replace('_', '').replace('"', '').replace(',', '').replace('.', '')
                word = word.replace('-', '').replace('?', '').replace('!', '').replace("'", "")
                word = word.replace('(', '').replace(')', '').replace(':', '').replace('[', '')
                word = word.replace(']', '').replace(';', '')

                # ignore case
                word = word.lower()

                # ignore numbers
                if word.isalpha():
                    if word in count:
                        count[word] = count[word] + 1
                    else:
                        count[word] = 1
    return count

def outputAnalysis(count, title):
    keys = list(count.keys())
    keys.sort()

    # save the word count analysis to a file
    try:
        with open('{}_analysis.txt'.format(title), 'w') as out:
            for word in keys:
                out.write(word + " " + str(count[word]))
                out.write('\n')
        return True
    except Exception as e:
        print("ERROR:", e)
        return False

def main():
    strInput = input("What file would you like to pick: ")
    count = analyzeBook(strInput)
    succeed = outputAnalysis(count, strInput)
    print("Did the output succeed?:", succeed)


if __name__ == "__main__":
    main()