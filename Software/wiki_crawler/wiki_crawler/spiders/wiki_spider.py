# -*- coding: utf-8 -*-
import scrapy

class WikiSpider(scrapy.Spider):
    name = 'wikipedia'
    allowed_domains = ['wikipedia.org']
    handle_httpstatus_list = [404]

    def __init__(self, topic=""):
        self.topic = topic
        self.start_urls = ['https://en.wikipedia.org/wiki/%s' % topic]


    def parse(self, response):
        try:
            text_response = response.css("div.mw-parser-output").extract()[0]
        except IndexError: 
            print("Sorry, this article does not exist") 
            exit(1)

        afile = open("test6.txt", "w")
        afile.write(text_response)
        afile.close()

        afile2 = open("test6.txt", "r")
         
        lines = afile2.readlines()
        line = ""

        found = False
        for index, item in enumerate(lines):
            item_lower = item.lower()
            # find ambigous cases
            if "refer to" or "refers to" in item:
                print("Term too ambigous, please try again")
                print(index)
                exit(0)
            # find first <p> tag containing search term
            if item[0] == "<" and item[1] == "p" and item[2] == ">" and self.topic.lower() in item_lower:
                found = True
                line = lines[index]
                break

        if found == False:
            print("Term too ambigous, please try again")
            exit(2)

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
        afile2.close()

        print(astring)

