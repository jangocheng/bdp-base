import joblib
import os


class Predict:

    @staticmethod
    def predict_by_joblib(model_name, data):
        file_path = os.getcwd() + "/model/" + model_name
        model = joblib.load(file_path)
        result = model.predict_proba(data)
        return result
