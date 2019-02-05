import urllib.request
from PIL import Image
from resizeimage import resizeimage


url_file = open("Image_URLS.csv")
o = open("HTTP_Image_URLS.txt", "a")

URLS = []
img_num = 0
for url in url_file:
    new_url = url.replace("https", "http")
    URLS.append(new_url)
    o.write(new_url)

    # img_file = "img" + str(img_num) + ".jpg"
    # urllib.request.urlretrieve(new_url, img_file)
    
    # img = open(img_file)
    # resizeimage.resize_height(img, 512)

    img_num += 1