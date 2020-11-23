FROM master/library/python:3.6
WORKDIR /app/
COPY model_predict /app/model_predict
COPY common /app/common
COPY model /app/model
COPY controller.py /app/controller.py
COPY requirements.txt /app/requirements.txt
RUN  pip3 install -r /app/requirements.txt
CMD python3 controller.py