import urllib.request
from PIL import Image
from resizeimage import resizeimage
import pickle
import joblib

url_file = open("Image_URLS_test.txt")

IMG_list=[]
img_num = 1232

for url in url_file:
    #reformat protocol for opening (security cert is expired)
    new_url = url.replace("https", "http")

    #construct image file name
    img_file = "img" + str(img_num) + ".jpg"

    #retrieve image
    urllib.request.urlretrieve(new_url, "images/test/" + img_file)
    
    #resize image to 400x400 box w/ grey bg
    resized_img = resizeimage.resize_contain(Image.open("images/test/" + img_file), [400, 400], bg_color=(132, 132, 132, 0))
    resized_img.save("images/test/" + img_file)

    #convert image to rgb values and flatten
    img_px = list(resized_img.getdata())
    img_px_flat = [x for sets in img_px for x in sets]

    #add image to list
    IMG_list.append(img_px_flat)

    img_num += 1

with open("IMG_list_test.joblib","wb") as p:
    joblib.dump(IMG_list, p)

url_file.close()

