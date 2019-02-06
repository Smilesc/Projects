import urllib.request
from PIL import Image
from resizeimage import resizeimage
import pickle

url_file = open("Image_URLS.txt")

with open("No_Face_Body_Realism.csv") as csv:
    csv_lines = csv.readlines()

IMG_dict = {}
img_num = 0


for url in url_file:
    #reformat protocol for opening (security cert is expired)
    new_url = url.replace("https", "http")

    #construct image file name
    img_file = "img" + str(img_num) + ".jpg"

    #retrieve image
    urllib.request.urlretrieve(new_url, "images/" + img_file)
    
    #resize image to 400x400 box w/ black bg
    resized_img = resizeimage.resize_contain(Image.open("images/" + img_file), [400, 400], bg_color=(0, 0, 0, 0))
    resized_img.save("images/" + img_file)

    #add image and corresponding data to dict
    IMG_dict[img_file] = csv_lines[img_num].split(',')[:-1]

    img_num += 1

with open("IMG_dict.pickle","wb") as p:
    pickle.dump(IMG_dict, p)

url_file.close()

