# import datetime
# import os
# import subprocess
# import sys
from sklearn import svm
import numpy as np
import pandas as pd
from sklearn import preprocessing
import pickle
import cv2
from sklearn.model_selection import train_test_split

data_train = pd.read_csv('train_data.csv')
data_test = pd.read_csv('test_data.csv')
data_train = data_train.rename(columns={' anger': 'anger'})
data_test = data_test.rename(columns={' anger': 'anger'})

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

with open('IMG_dict.pickle', 'rb') as pickle_file:
    img_dict = pickle.load(pickle_file)

img_train = list(img_dict.keys())[:1231]
img_test = list(img_dict.keys())[1232:]
# print(img_test)


classifier = svm.SVC(gamma='auto', verbose=True)
classifier.fit(data_train, img_train)

# from sklearn.ensemble import RandomForestClassifier
# from sklearn.metrics import make_scorer, accuracy_score
# from sklearn.model_selection import GridSearchCV

# # Choose the type of classifier. 
# clf = RandomForestClassifier()

# # Choose some parameter combinations to try
# parameters = {'n_estimators': [4, 6, 9], 
#               'max_features': ['log2', 'sqrt','auto'], 
#               'criterion': ['entropy', 'gini'],
#               'max_depth': [2, 3, 5, 10], 
#               'min_samples_split': [2, 3, 5],
#               'min_samples_leaf': [1,5,8]
#              }

# # Type of scoring used to compare parameter combinations
# acc_scorer = make_scorer(accuracy_score)

# # Run the grid search
# grid_obj = GridSearchCV(clf, parameters, scoring=acc_scorer)
# grid_obj = grid_obj.fit(data_train, img_train, cv=2)

# # Set the clf to the best combination of parameters
# clf = grid_obj.best_estimator_

# # Fit the best algorithm to the data. 
# clf.fit(data_train, img_train)
# y_train,y_test= train_test_split(list(img_dict.keys()), test_size=0.2)
# print(y_train)
# # Fill in your Cloud Storage bucket name
# BUCKET_NAME = '<YOUR_BUCKET_NAME>'
# # [END setup]


# # [START download-data]
# iris_data_filename = 'iris_data.csv'
# iris_target_filename = 'iris_target.csv'
# data_dir = 'gs://cloud-samples-data/ml-engine/iris'

# # gsutil outputs everything to stderr so we need to divert it to stdout.
# subprocess.check_call(['gsutil', 'cp', os.path.join(data_dir,
#                                                     iris_data_filename),
#                        iris_data_filename], stderr=sys.stdout)
# subprocess.check_call(['gsutil', 'cp', os.path.join(data_dir,
#                                                     iris_target_filename),
#                        iris_target_filename], stderr=sys.stdout)
# # [END download-data]


# # [START load-into-pandas]
# # Load data into pandas, then use `.values` to get NumPy arrays
# iris_data = pd.read_csv(iris_data_filename).values
# iris_target = pd.read_csv(iris_target_filename).values

# # Convert one-column 2D array into 1D array for use with scikit-learn
# iris_target = iris_target.reshape((iris_target.size,))
# # [END load-into-pandas]


# # [START train-and-save-model]
# # Train the model
# classifier = svm.SVC(gamma='auto', verbose=True)
# classifier.fit(iris_data, iris_target)

# # Export the classifier to a file
# model_filename = 'model.joblib'
# joblib.dump(classifier, model_filename)
# # [END train-and-save-model]


# # [START upload-model]
# # Upload the saved model file to Cloud Storage
# gcs_model_path = os.path.join('gs://', BUCKET_NAME,
#     datetime.datetime.now().strftime('iris_%Y%m%d_%H%M%S'), model_filename)
# subprocess.check_call(['gsutil', 'cp', model_filename, gcs_model_path],
#     stderr=sys.stdout)
# # [END upload-model]