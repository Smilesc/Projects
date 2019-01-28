import numpy as np
from collections import deque

f = open("ID_only_ag3.csv")
o = open("data_dict_ag3.txt", "a")

data_dict = {}
for line in f:
    line = line[:-1]
    split_line = line.split(',')
    data_dict[split_line[0]] = split_line[1:]
    o.write("{}: {}\n".format(split_line[0],split_line[1:]))


#print(data_dict)