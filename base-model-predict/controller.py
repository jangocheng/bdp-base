import json
import logging
from pandas.io.json import json_normalize
from flask import Flask
from flask import request
from model_predict.predict_way import Predict
from common.res_utils import Response

app = Flask(__name__)


@app.route(rule="/actuator/health", methods=['POST', 'GET', 'HEAD', 'PUT', 'DELETE'])
def health():
    res = Response()
    return res.success_no_data()


@app.route(rule="/predict", methods=['POST'])
def predict():
    data = None
    res = Response()
    try:
        # 把请求参数格式化成json
        raw_data = request.get_data()
        data_json = json.loads(raw_data)
        # json数据转换成dataFrame数据
        data = json_normalize(data_json["data"])
        model_name = data_json["modelName"]
        model_compress_way = data_json["modelCompressWay"]

        # 根据模型类型调用预测模型
        result = switch_predict(model_compress_way, model_name, data)
        output_type = data_json["outputType"]
        output_name = data_json["outputName"]

        # 处理返回结果
        if output_type == "array":
            final_result = get_value(result, output_name)
            return res.success(final_result)
        else:
            return res.success(result[output_name])
    except Exception as e:
        error = "模型测试失败！error message:" + str(e)
        logging.error(error)
        return res.fail(error)


# 预测模型的选择
def switch_predict(model_compress_way, model_name, data):
    switch = {
        "joblib": Predict.predict_by_joblib(model_name, data)
    }
    return switch[model_compress_way]


def get_value(result, output_name):
    indexes_str = output_name.split(",")
    indexes = list(map(int, indexes_str))

    for index in indexes:
        result = result[index]

    return result


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
