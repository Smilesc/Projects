import colorsys

h, s, v = colorsys.rgb_to_hsv((233/255), (211/255), (78/255))

print(h*255, s*255, v*255)
