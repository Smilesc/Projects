import urllib.request
url_file = open("URLs/Image_URLS_all.txt")

IMG_list=[]
img_num = 1

for url in url_file:
    # #reformat protocol for opening (security cert is expired)
    new_url = url.replace("https", "http")

    #construct image file name
    img_file = "img" + str(img_num) + ".jpg"

    #retrieve image
    urllib.request.urlretrieve(new_url, "images/originals/" + img_file)
    
    img_num += 1
    
url_file.close()
