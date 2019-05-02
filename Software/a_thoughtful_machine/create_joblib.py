from PIL import Image
from sklearn.externals import joblib
import colorsys

# IMG_list=[0] * 1232
# IMG_list_inner = [0] * 120000
# IMG_list = [IMG_list_inner] * 1232
# for i in IMG_list:
#     IMG_list[i] = IMG_list_inner

IMG_list = []
img_num = 1

# IMG_list_index = 0
# IMG_list_inner_index = 0

for m in range(615):
    if m != 24:

        #construct image file name
        img_file = "img" + str(img_num) + ".jpg"

        img = Image.open("images/200x200s/" + img_file)

        #convert image to rgb values and flatten
        img_px = list(img.getdata())
        img.close()

        #add image to list
        IMG_list.append([x for sets in img_px for x in sets])

    img_num += 1

for j, image in enumerate(IMG_list):
    for i in range(0, len(image), 3):
        h, s, v = colorsys.rgb_to_hsv((image[i]/255), (image[i + 1]/255), (image[i + 2]/255))
        IMG_list[j][i], IMG_list[j][i + 1], IMG_list[j][i + 2] = round(h, 3), round(s, 3), round(v, 3)

#print(IMG_list)
with open("joblibs/IMG_list_train_all_hsv_to_614.joblib","wb") as p:
    joblib.dump(IMG_list, p)
