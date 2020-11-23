#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask import Flask
from flask_restful import Api
from apps.projects.Projects import Project
from apps.projects.ProjectDetails import ProjectDetail
from apps.config.Db import db

"""
@author: wlhbdp
@file: app
"""

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = "mysql+pymysql://root:123456@node02:3306/devops"
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_POOL_RECYCLE'] = 999
db.init_app(app)
api = Api(app)
api.add_resource(Project, '/api/Projects')
api.add_resource(ProjectDetail, '/api/ProjectDetail')

if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0")
