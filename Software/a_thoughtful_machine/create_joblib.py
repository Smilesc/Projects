import urllib.request
from PIL import Image
from resizeimage import resizeimage
import pickle
from sklearn.externals import joblib
import colorsys

IMG_list=[]
img_num = 1

for _ in range(1232):

    #construct image file name
    img_file = "img" + str(img_num) + ".jpg"

    img = Image.open("images/200x200s/" + img_file)

    #convert image to rgb values and flatten
    img_px = list(img.getdata())
    hsv_img_px = []
    for rgb in img_px:
        hsv_img_px.append(colorsys.rgb_to_hsv(rgb[0], rgb[1], rgb[2]))
    img_px_flat = [round(x, 3) for sets in hsv_img_px for x in sets]

    #add image to list
    IMG_list.append(img_px_flat)

    img_num += 1

with open("joblibs/IMG_list_train_all_hsv.joblib","wb") as p:
    joblib.dump(IMG_list, p)
