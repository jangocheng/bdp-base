#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask_restful import Resource, reqparse
from flask import request
from apps.projects.Models import ProjectInfo

"""
@author: wlhbdp
@file: projects
"""


class Project(Resource):
    def __init__(self):
        self.dbs = ProjectInfo()

    def get(self):
        dl = []
        item = self.dbs.findAll()
        for i in item:
            d = {"project": i.project, "domain": i.domain, "type": i.type, "language": i.language, "nodes": i.nodes,
                 "status": i.status, "groups": i.groups, "createtime": i.createtime.strftime("%Y-%m-%d %H:%M:%S")}
            dl.append(d)
        data = {"data": dl}
        return data

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('project', type=str, required=True)
        parser.add_argument('domain', type=str)
        parser.add_argument('type', type=str, required=True)
        parser.add_argument('language', type=str, required=True)
        parser.add_argument('groups', type=str, required=True)
        parser.add_argument('description', type=str)
        parser.add_argument('attribution', type=str)
        parser.add_argument('developer', type=str)
        parser.add_argument('operations', type=str)
        args = parser.parse_args(strict=True)
        self.dbs.insert(project=args['project'], domain=args['domain'], type=args['type'], language=args['language'],
                        groups=args['groups'], description=args['description'], attribution=args['attribution'],
                        developer=args['developer'], operations=args['operations'])
        return args


if __name__ == "__main__":
    a = Project()
    print(a.post())
