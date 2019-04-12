from PIL import Image
from resizeimage import resizeimage

img_num = 1

for i in range(1538):

    img_file = "img" + str(img_num) + ".jpg"
    
    resized_img = resizeimage.resize_cover((Image.open("images/originals/" + img_file)), [200,200])
    resized_img.save("images/200x200s/" + img_file)

    img_num += 1