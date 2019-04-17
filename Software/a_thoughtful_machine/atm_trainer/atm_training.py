import datetime
import os
import subprocess
import sys
import pandas as pd
from sklearn import svm
from sklearn.externals import joblib
from sklearn.tree import DecisionTreeClassifier
import numpy as np
"""

gcloud ml-engine jobs submit training "atm_all" --job-dir="gs://atm_training/job_dir_all" --package-path="./atm_trainer" --module-name="atm_trainer.atm_training" --region=us-east1 --runtime-version=1.13 --python-version=3.5 --scale-tier=BASIC

"""
BUCKET_NAME = 'atm_training'

data_filename = 'IMG_list_all.joblib'
target_filename = 'all_data.csv'
data_dir = 'gs://atm_training'

# gsutil outputs everything to stderr so we need to divert it to stdout.
subprocess.check_call(['gsutil', 'cp', os.path.join(data_dir, data_filename), data_filename], stderr=sys.stdout)
subprocess.check_call(['gsutil', 'cp', os.path.join(data_dir, target_filename), target_filename], stderr=sys.stdout)

with open(data_filename, 'rb') as jl:
    data = joblib.load(jl)

target = pd.read_csv(target_filename).values

# Train the model
classifier = DecisionTreeClassifier()
classifier.fit(data, target)

# Export the classifier to a file
model_filename = 'model.joblib'
joblib.dump(classifier, model_filename)

# Upload the saved model file to Cloud Storage
gcs_model_path = os.path.join('gs://', BUCKET_NAME,
    datetime.datetime.now().strftime('atm_%Y%m%d_%H%M%S'), model_filename)
subprocess.check_call(['gsutil', 'cp', model_filename, gcs_model_path],
    stderr=sys.stdout)