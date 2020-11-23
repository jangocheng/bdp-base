class Response(object):

    def __init__(self):
        self.result = {"data": -1, "code": 0, "message": ""}

    def success(self, data):
        self.result["data"] = data
        return str(self.result)

    def success_no_data(self):
        return str(self.result)

    def fail(self, message):
        self.result["message"] = message
        self.result["code"] = 1
        return str(self.result)
