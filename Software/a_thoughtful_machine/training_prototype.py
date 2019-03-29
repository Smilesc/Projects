# import datetime
# import os
# import subprocess
# import sys
from sklearn import svm
import numpy as np
import pandas as pd
from sklearn import preprocessing
import pickle
from PIL import Image
import joblib

# Fill in your Cloud Storage bucket name
BUCKET_NAME = 'wikiart_dataset'
# [END setup]

data_train = pd.read_csv('train_data.csv')
data_test = pd.read_csv('test_data.csv')

# #print(data_train.sample(5))
# #print(data_train.columns)

def encode_features(df_train, df_test):
    features = ['anger', 'anticipation', 'disagreeableness', 'disgust', 'fear',
       'gratitude', 'happiness', 'humility', 'love', 'optimism', 'pessimism',
       'regret', 'sadness', 'shyness', 'surprise', 'trust', 'neutral']
    df_combined = pd.concat([df_train[features], df_test[features]])
    
    for feature in features:
        le = preprocessing.LabelEncoder()
        le = le.fit(df_combined[feature])
        df_train[feature] = le.transform(df_train[feature])
        df_test[feature] = le.transform(df_test[feature])
    return df_train, df_test
    
data_train, data_test = encode_features(data_train, data_test)
#print(data_train.head())

with open('IMG_list.joblib', 'rb') as jl:
    IMG_list = joblib.load(jl)

img_train = IMG_list[:1231]
img_test = IMG_list[1232:]
# print(img_test)

# Train the model
classifier = svm.SVC(gamma='auto', verbose=True)
classifier.fit(data_train, img_train)

# Export the classifier to a file
model_filename = 'model.joblib'
joblib.dump(classifier, model_filename)

# # [START upload-model]
# # Upload the saved model file to Cloud Storage
# gcs_model_path = os.path.join('gs://', BUCKET_NAME,
#     datetime.datetime.now().strftime('atm_%Y%m%d_%H%M%S'), model_filename)
# subprocess.check_call(['gsutil', 'cp', model_filename, gcs_model_path],
#     stderr=sys.stdout)
# # [END upload-model]