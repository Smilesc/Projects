import urllib

url_file = open("Image_URLS.csv")
o = open("HTTP_Image_URLS.txt", "a")

URLS = []
for url in url_file:
    new_url = url.replace("https", "http")
    URLS.append(new_url)

    # o.write(new_url)

urllib.request.urlretrieve