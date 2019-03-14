import datetime
import os
import subprocess
import sys
import pandas as pd
from sklearn import svm
from sklearn.externals import joblib

# Fill in your Cloud Storage bucket name
BUCKET_NAME = 'atm-training'

data_filename = 'IMG_list_train1.joblib'
target_filename = 'train_data1.csv'
data_dir = 'gs://atm_training'

# gsutil outputs everything to stderr so we need to divert it to stdout.
subprocess.check_call(['gsutil', 'cp', os.path.join(data_dir, data_filename), data_filename], stderr=sys.stdout)
subprocess.check_call(['gsutil', 'cp', os.path.join(data_dir, target_filename), target_filename], stderr=sys.stdout)

with open(data_filename, 'rb') as jl:
    data = joblib.load(jl)

target = pd.read_csv(target_filename).values
target = target.reshape((target.size,))

# Train the model
classifier = svm.SVC(gamma='auto', verbose=True)
classifier.fit(data, target)

# Export the classifier to a file
model_filename = 'model.joblib'
joblib.dump(classifier, model_filename)

# Upload the saved model file to Cloud Storage
gcs_model_path = os.path.join('gs://', BUCKET_NAME,
    datetime.datetime.now().strftime('atm_%Y%m%d_%H%M%S'), model_filename)
subprocess.check_call(['gsutil', 'cp', model_filename, gcs_model_path],
    stderr=sys.stdout)