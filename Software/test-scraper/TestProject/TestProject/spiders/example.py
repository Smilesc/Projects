# -*- coding: utf-8 -*-
import scrapy

class ExampleSpider(scrapy.Spider):
    name = 'wikipedia'
    allowed_domains = ['wikipedia.org']
    start_urls = ['https://en.wikipedia.org/wiki/Support']

    def parse(self, response):
        text_response = response.css("div.mw-parser-output").extract()[0]
        line2 = ""

        afile = open("test6.txt", "w")
        afile.write(text_response)
        afile.close()

        afile2 = open("test6.txt", "r")

        astring = ""  
        lines = afile2.readlines()

        # Look for first p tag without class
        for index, item in enumerate(lines):
            if "<p>" in item:
                line2 = lines[index]
                break

        # line = afile2.readline()
        
        line_array = list(line2)

        index = 0
        while line_array[index] != "." and line_array[index] != ":":

            if line_array[index] == "<":
                while line_array[index] != ">":
                    index += 1
                index += 1
            elif line_array[index] == "[":
                while line_array[index] != "]":
                    index += 1
                index += 1
            elif line_array[index] == "(":
                while line_array[index] != ")":
                    index += 1
                index += 2
            else:
                astring += line_array[index]
                index += 1

        if "may refer to" in astring:
            print("Term too ambigous, please try again")
        else:
            print(astring)

