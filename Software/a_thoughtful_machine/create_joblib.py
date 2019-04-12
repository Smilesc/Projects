import urllib.request
from PIL import Image
from resizeimage import resizeimage
import pickle
from sklearn.externals import joblib

url_file = open("URLs/Image_URLS_test.txt")

IMG_list=[]
img_num = 1232

for url in url_file:

    #construct image file name
    img_file = "img" + str(img_num) + ".jpg"

    img = Image.open("images/originals/" + img_file)

    #convert image to rgb values and flatten
    img_px = list(img.getdata())
    img_px_flat = [x for sets in img_px for x in sets]

    #add image to list
    IMG_list.append(img_px_flat)

    img_num += 1

with open("joblibs/IMG_list_test.joblib","wb") as p:
    joblib.dump(IMG_list, p)

url_file.close()
