#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask_restful import Resource, reqparse, request
from apps.projects.Models import ProjectInfo

"""
@author: wlhbdp
@file: ProjectDetails
"""


class ProjectDetail(Resource):
    def __init__(self):
        self.dbs = ProjectInfo()

    def get(self):
        projectname = request.args.get('project')
        if projectname:
            item = self.dbs.findFilter(projectname)
            data = {}
            data['data'] = {"project": item.project, "domain": item.domain, "type": item.type,
                            "language": item.language,
                            "nodes": item.nodes, "status": item.status, "groups": item.groups, "git": item.git,
                            "attribution": item.attribution, "developer": item.developer,
                            "operations": item.operations, "description": item.description,
                            "createtime": item.createtime.strftime("%Y-%m-%d %H:%M:%S")}
            return data
        else:
            data = {"data": None, "msg": "project not None"}
            return data