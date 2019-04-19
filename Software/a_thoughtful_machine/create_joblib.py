from PIL import Image
from sklearn.externals import joblib
import colorsys
import numpy as np

IMG_list=[]
img_num = 616

for m in range(615):
    if m != 24:
        print(m)
        #construct image file name
        img_file = "img" + str(img_num) + ".jpg"

        img = Image.open("images/200x200s/" + img_file)

        #convert image to rgb values and flatten
        img_px = list(img.getdata())

        if len(img_px[0]) == 4:
            print("AAAAAAAAAAAAAAAAAAAAAAAAAAAA", img_num, img_px[0])


        #add image to list
        IMG_list.append([x for sets in img_px for x in sets])

    img_num += 1

for j, image in enumerate(IMG_list):
    for i in range(0, len(image), 3):
        h, s, v = colorsys.rgb_to_hsv(image[i], image[i + 1], image[i + 2])
        IMG_list[j][i], IMG_list[j][i + 1], IMG_list[j][i + 2] = round(h, 3), round(s, 3), round(v, 3)

with open("joblibs/IMG_list_train_all_hsv1.joblib","wb") as p:
    joblib.dump(IMG_list, p)
