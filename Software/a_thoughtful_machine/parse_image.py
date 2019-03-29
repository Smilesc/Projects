import urllib.request
from PIL import Image
from resizeimage import resizeimage
import pickle
from sklearn.externals import joblib
import json

img_file = "img1399.jpg"
outfile = open('image_test4.json', 'w')

resized_img = resizeimage.resize_contain(Image.open("images/test_200/" + img_file), [200, 200], bg_color=(132, 132, 132, 0))
resized_img.save("images/test_200/" + img_file)

#convert image to rgb values and flatten
img_px = list(resized_img.getdata())
img_px_flat = [x for sets in img_px for x in sets]


outfile.write("{\"instances\":[")
json.dump(img_px_flat, outfile)
outfile.write("]}")