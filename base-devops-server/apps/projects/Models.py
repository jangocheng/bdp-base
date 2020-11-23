#!/usr/bin/env python
# -*- coding: utf-8 -*-
from apps.config.Db import db
import datetime

"""
@author: wlhbdp
@file: models
"""
# from flask import Flask
# from flask_sqlalchemy import SQLAlchemy
#
# app = Flask(__name__)
# app.config['SQLALCHEMY_DATABASE_URI'] = "mysql+pymysql://root:123456@node02:3306/devops"
# app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
# db = SQLAlchemy(app)

class Projects(db.Model):
    project = db.Column(db.String(255), primary_key=True, unique=True, comment="项目名")
    domain = db.Column(db.String(255), unique=False, comment="域名")
    type = db.Column(db.String(255), unique=False, comment="项目类型")
    language = db.Column(db.String(255), unique=False, comment="开发语言")
    nodes = db.Column(db.Integer, unique=False, comment="节点数")
    status = db.Column(db.Integer, unique=False, comment="状态")
    groups = db.Column(db.String(255), unique=False, comment="分组")
    createtime = db.Column(db.DateTime, unique=False, default=datetime.datetime.now(), comment="创建时间")
    description = db.Column(db.String(1024), unique=False, comment="描述")
    attribution = db.Column(db.String(255), unique=False, comment="所属部门")
    developer = db.Column(db.String(255), unique=False, comment="开发负责人")
    operations = db.Column(db.String(255), unique=False, comment="运维负责人")
    git = db.Column(db.String(255), unique=False, comment="git仓库地址")

    def __init__(self, project, domain, type, language, nodes, status, groups, createtime, description, attribution, developer, operations, git):
        self.project = project
        self.domain = domain
        self.type = type
        self.language = language
        self.nodes = nodes
        self.status = status
        self.groups = groups
        self.createtime = createtime
        self.description = description
        self.attribution = attribution
        self.developer = developer
        self.operations = operations
        self.git = git

    def __repr__(self):
        return '<Project %r %r %r %r %i %r %r %r %r %r %r %r>' % (self.project, self.domain, self.type, self.language, self.nodes, self.status, self.groups, self.createtime, self.attribution, self.developer, self.operations, self.git)


class ProjectInfo():
    def findAll(self):
        return Projects.query.all()

    def findFilter(self, prjectname):
        return Projects.query.filter_by(project=prjectname).first()

    def insert(self, project, domain=None, type=None, language=None, nodes=0, status=0, groups=None, description=None, attribution=None, developer=None, operations=None, git=None):
        pj = Projects(project, domain, type, language, nodes, status, groups, None, description, attribution, developer, operations, git)
        db.session.add(pj)
        db.session.commit()
        return "ok"


if __name__ == "__main__":
    db.create_all()