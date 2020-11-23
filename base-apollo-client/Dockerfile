FROM master/library/centos:7
WORKDIR /app/run/
COPY run /app/run
COPY requirements.txt /app/requirements.txt
COPY pyapollo-master /app/lib/pyapollo-master
RUN rm -f /etc/yum.repos.d/*.repo /etc/localtime && \
  ln -s  /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
  for i in {CentOS-Base.repo,epel.repo,ius.repo,bdata.repo};do curl -s -o /etc/yum.repos.d/$i http://master/bdata/centos7/$i;done && \
  yum clean all && yum makecache && \
  yum install -y python36 python36-pip python36-devel gcc python36-mysql openssl-devel && \
  pip3 install -r /app/requirements.txt -i https://mirrors.aliyun.com/pypi/simple/ && \
  cd /app/lib/pyapollo-master/ && python3 setup.py install
CMD python3 controller.py ${CONFIG_URL} ${RUN_ENV}