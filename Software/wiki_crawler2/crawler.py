import requests
import sys

def get_request(topic):
    URL = "https://en.wikipedia.org/wiki/" + topic
    response = requests.get(url = URL)

    response2 = list(response.iter_lines())
    parse(response2, topic)

def parse(text_response, topic):

    lines = []
    for item in text_response:
        lines.append(item.decode('utf-8'))

    line = ""

    found = False
    for index, item in enumerate(lines):
        item_lower = item.lower()
        # find ambigous cases
        if "refer to" in item or "refers to" in item:
            print("Term too ambigous, please try again")
            print(index)
            exit(0)

        # find first <p> tag containing search term
        if "<p>" in item_lower[0:5] and topic in item_lower:
            found = True
            line = lines[index]
            break

    if found == False:
        print("Term too ambigous, please try again")
        exit(0)

    # make line into iterable list
    line_array = list(line)

    astring = "" 
    index = 0
    
    while line_array[index] != ".":
        # skip all text between angle brackets
        if line_array[index] == "<":
            while line_array[index] != ">":
                index += 1
            index += 1
        # skip all text between brackets
        elif line_array[index] == "[":
            while line_array[index] != "]":
                index += 1
            index += 1
        # skip all text between parentheses
        elif line_array[index] == "(":
            astring = astring[:-1]
            paren = 1 # start keeping track of paren pairs
            index += 1 # move past current paren
            while paren != 0:
                if line_array[index] == "(":
                    paren += 1
                elif line_array[index] == ")":
                    paren -= 1
                index += 1
        # add text to string
        else:
            astring += line_array[index]
            index += 1

    astring += "."

    print(astring)


def main():
    # topic = input("Enter a topic: ")
    get_request(sys.argv[1])

if __name__ == main():
    pass
