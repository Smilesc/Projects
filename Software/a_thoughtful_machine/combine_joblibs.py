from sklearn.externals import joblib


with open("joblibs/IMG_list_train_all_hsv.joblib", 'rb') as jl:
    hsv = joblib.load(jl)

    # print("hsv1: ", len(hsv1))
    # print(len(hsv1[0]))

with open("joblibs/IMG_list_train_all_hsv1.joblib", 'rb') as jl:
    hsv1 = joblib.load(jl)

    # print("hsv: ", len(hsv), " hsv1:", len(hsv1))
    hsv.extend(hsv1)

with open("joblibs/IMG_list_train_all_hsv_all.joblib","wb") as p:
    joblib.dump(hsv, p)
