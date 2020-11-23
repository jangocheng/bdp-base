#!/usr/bin/env python
# -*- coding: utf-8 -*-

import datetime
from modules import E2M
import xlrd
import sys
import logging
import socket

"""
@author: wlhbdp
@file: controller
"""


def main(config_url, run_env):
    try:
        e2m = E2M(config_url, run_env)
        logging.info("Application Running start")
        date_now = datetime.datetime.now().strftime("%Y%m%d")
        filename = "kaoqin" + date_now + ".xlsx"
        sftp_status = e2m.SftpDownload(filename)
        if sftp_status != 0:
            exit(1)
        book = xlrd.open_workbook("../data/" + filename)
        sheet = book.sheet_by_index(0)
        f = 0
        sql_value = []
        for sheet_list in range(1, sheet.nrows):
            data = sheet.row_values(sheet_list)
            user_name = data[2]
            work_type = data[4]
            to_company = data[5]
            from_company = data[6]
            is_work = data[7]
            not_work_reason = data[8]
            reason_detail = data[9]
            if data[1].strip() == "":
                logging.warning("emp_no Can not be empty. data is %s" % data)
                continue
            else:
                emp_no = data[1]

            if not isinstance(data[3], float) and data[3].strip() == "":
                logging.warning("work_date Can not be empty. data is %s" % data)
                continue
            else:
                work_date = xlrd.xldate_as_datetime(data[3], 0).strftime("%Y-%m-%d")

            if data[10]:
                work_time = e2m.ExeclTime2Time(data[10])
            else:
                work_time = ''

            if data[11]:
                start_work_time = xlrd.xldate_as_datetime(data[11], 0).strftime("%Y-%m-%d %H:%M:%S")
            else:
                start_work_time = ''

            if data[12]:
                off_work_time = e2m.ExeclTime2Time(data[12])
            else:
                off_work_time = ''

            if data[13]:
                end_work_time = xlrd.xldate_as_datetime(data[13], 0).strftime("%Y-%m-%d %H:%M:%S")
            else:
                end_work_time = ''
            create_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            sql = (0, emp_no, user_name, work_date, work_type, to_company, from_company, is_work, not_work_reason,
                   reason_detail, work_time, start_work_time, off_work_time, end_work_time, create_time)
            if f < 500:
                sql_value.append(sql)
                f += 1
                continue
            else:
                e2m.InsertMysql(sql_value)
                f = 1
                sql_value = []
                sql_value.append(sql)
                continue
        e2m.InsertMysql(sql_value)
        logging.info("Application Running completed.")
    except Exception as e:
        logging.error(e)
        hostname = socket.gethostname()
        e2m.DingtalkNotice("Project：base-python-mall", "#### Project：base-python-mall\n\n> Env：[%s]\n\n> Host：%s\n\n> Module：Main\n\n> Status：执行失败\n\n> Message：%s\n\n" % (run_env, hostname, e))
        exit(1)


if __name__ == "__main__":
    if len(sys.argv) >= 3:
        config_url = sys.argv[1]
        run_env = sys.argv[2]
        main(config_url, run_env)
    else:
        print("Usage: controller.py apollo_config_url run_env")
        exit(1)
