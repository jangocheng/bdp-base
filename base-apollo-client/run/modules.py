#!/usr/bin/env python
# -*- coding: utf-8 -*-

from datetime import time
from pyapollo import ApolloClient
from os import path, makedirs
import pymysql
import logging
import paramiko
import requests
import json
import socket

"""
@author: wlhbdp
@file: modules
"""


class E2M():
    def __init__(self, config_host, run_env):
        config_appid = "base-python-mall"
        client = ApolloClient(app_id=config_appid, config_server_url=config_host)
        self.mysql_host = client.get_value("python.mysql.host", "127.0.0.1")
        self.mysql_user = client.get_value("python.mysql.user", "root")
        self.mysql_password = client.get_value("python.mysql.password", "123456")
        self.mysql_database = client.get_value("python.mysql.database", "mall_woke")
        self.mysql_table = client.get_value("python.mysql.table", "mall_woke_attendance")
        self.sftp_host = client.get_value("python.sftp.host", "127.0.0.1")
        self.sftp_port = client.get_value("python.sftp.prot", "22")
        self.sftp_user = client.get_value("python.sftp.user", "root")
        self.sftp_pass = client.get_value("python.sftp.pass", "123456")
        self.dingtalk_token = client.get_value("python.dingtalk.token", "123456")
        self.dingtalk_at = client.get_value("python.dingtalk.at", "123456")
        self.run_env = run_env
        self.hostname = socket.gethostname()
        log_dir = "../logs/"
        if not path.exists(log_dir):
            makedirs(log_dir)
        logging.basicConfig(handlers=[logging.FileHandler(log_dir + "app.log", encoding="utf-8")], level=logging.INFO,
                            format="%(asctime)s.%(msecs)03d %(levelname)s | [%(threadName)s] %(name)s [%(lineno)d] | %(filename)s %(funcName)s %(message)s",
                            datefmt="%Y-%m-%d %H:%M:%S")

    def ExeclTime2Time(self, x):
        x = int(x * 24 * 3600)
        my_time = time(x // 3600, (x % 3600) // 60, x % 60)
        return my_time.strftime("%H:%M:%S")

    def InsertMysql(self, val):
        db = pymysql.connect(self.mysql_host, self.mysql_user, self.mysql_password, self.mysql_database)
        cursor = db.cursor()
        sql = "INSERT INTO {table} VALUES ({value});".format(table=self.mysql_table, value="%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s")
        try:
            cursor.executemany(sql, val)
            db.commit()
        except Exception as e:
            db.rollback()
            logging.error(str(e) + str(val))
            self.DingtalkNotice("Project：base-python-mall", "#### Project：base-python-mall\n\n> Env：[%s]\n\n> Host：%s\n\n> Module：InsertMysql\n\n> Status：数据写入失败\n\n> Message：%s\n\n" % (self.run_env, self.hostname, e))
        db.close()

    def SftpDownload(self, filename):
        try:
            host = self.sftp_host
            port = int(self.sftp_port)
            sf = paramiko.Transport(sock=(host, port))
            sf.connect(username=self.sftp_user, password=self.sftp_pass)
            sftp = paramiko.SFTPClient.from_transport(sf)
            data_dir = "../data/"
            if not path.exists(data_dir):
                makedirs(data_dir)
            sftp.lstat("/upload/" + filename)
            sftp.get("/upload/" + filename, data_dir + filename)
            sf.close()
            return 0
        except Exception as e:
            logging.error(e)
            self.DingtalkNotice("Project：base-python-mall", "#### Project：base-python-mall\n\n> Env：[%s]\n\n> Host：%s\n\n> Module：SftpDownload\n\n> Status：%s 下载失败\n\n> Message：%s\n\n" % (self.run_env, self.hostname, filename, e))
            return 1

    def DingtalkNotice(self, title, message):
        headers = {'Content-Type': 'application/json;charset=utf-8'}
        api_url = "https://oapi.dingtalk.com/robot/send?access_token=" + self.dingtalk_token
        at_list = self.dingtalk_at.split(",")
        for at in at_list:
            message += "@" + at
        json_data = {
            "msgtype": "markdown",
            "markdown": {
                "title": title,
                "text": message
            },
            "at": {
                "atMobiles": at_list,
                "isAtAll": False
            }
        }
        requests.post(api_url, json.dumps(json_data), headers=headers)
