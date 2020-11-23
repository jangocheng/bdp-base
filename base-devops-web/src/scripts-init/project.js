import $ from "jquery";
import 'datatables.net-bs4';
import 'bootstrapvalidator';

$("#projectType").change(function () {
    // var obj = document.getElementByIdx_x("testSelect");
    // var index = obj.selectedIndex;
    // var value = obj.options[index].value;
    var value = $("#projectType").val();
    var dm = document.getElementById("master-domain");
    if (value == "WEB") {
        document.getElementById("div-domain-1").removeAttribute("hidden");
        dm.innerText = ".platform.com";
    } else if (value == "API") {
        document.getElementById("div-domain-1").removeAttribute("hidden");
        dm.innerText = ".api.master";
    } else if (value == "SVC") {
        dm.innerText = "";
        document.getElementById("div-domain-1").setAttribute("hidden", "hidden");
    };
});

// $("#attribution").change(function () {
//     var value = $("#attribution").val();
//     var dm = document.getElementById("projectDomain");
//     var patt = new RegExp(".bdata");
//     if (value == "数据技术部") {
//         if (patt.test(dm.value)) {
//             // dm.value = dm.value + ".bdata";
//             return true;
//         } else {
//             dm.value = dm.value + ".bdata";
//         };
//     } else {
//         if (patt.test(dm.value)) {
//             dm.value = dm.value.split(".bdata")[0];
//         };
//     };
// });


$(document).ready(function () {
    $('#pt').DataTable({
        "deferRender": true,
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            }
        },
        ajax: {
            url: '/api/Projects',
            dataSrc: 'data'
        },
        columnDefs: [
            {
                "targets": 0,
                "render": function (data, type, full, meta) {
                    var content = "<a href='/project-detail.html?project=" + data.project + "'>" + data.project; + "</a>";
                    return content;
                }
            },
            {
                "targets": 5,
                "render": function (data) {
                    if (data.status == 0) {
                        var content = "<button class='btn btn-outline-success'>正常</button>";
                    } else if (data.status == 1) {
                        var content = "<button class='btn btn-outline-warning'>预下线</button>";
                    } else {
                        var content = "<button class='btn btn-outline-danger'>下线</button>";
                    }
                    return content;
                }
            },
            {
                "targets": 8,
                "render": function (data) {
                    var content = "<button id='btn-p-edit' class='mb-2 mr-2 btn btn-info'>编辑</button>";
                    return content;
                }
            }
        ],
        columns: [
            { className: "text-center", "data": null },
            { className: "text-center", data: "domain" },
            { className: "text-center", data: "type" },
            { className: "text-center", data: "language" },
            { className: "text-center", data: "nodes" },
            { className: "text-center", data: null },
            { className: "text-center", data: "groups" },
            { className: "text-center", data: "createtime" },
            { className: "text-center", data: null }
        ]
    });
    $('#create-p-from')
        .bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                project: {
                    message: '项目名验证失败',
                    validators: {
                        notEmpty: {
                            message: '项目名不能为空'
                        },
                        stringLength: {
                            max: 23,
                            message: '项目名长度不能大于23位'
                        },
                        regexp: {
                            regexp: /^[a-z0-9\-]+$/,
                            message: '项目名只能包含小写字母、数字和中划线(-)'
                        }
                    }
                },
                groups: {
                    validators: {
                        notEmpty: {
                            message: '分组不能为空'
                        }
                    }
                },
                attribution: {
                    validators: {
                        notEmpty: {
                            message: '所属部门不能为空'
                        }
                    }
                },
                developer: {
                    validators: {
                        notEmpty: {
                            message: '开发负责人不能为空'
                        }
                    }
                },
                operations: {
                    validators: {
                        notEmpty: {
                            message: '运维负责人不能为空'
                        }
                    }
                },
            }
        })
        .on('success.form.bv', function (e) {
            e.preventDefault();
            var dm = document.getElementById("projectDomain");
            var msdm = document.getElementById("master-domain");
            dm.value = dm.value + msdm.innerText;
            var pd = $("#create-p-from").serialize();
            console.log(pd);
            $.ajax({
                type: 'POST',
                url: "/api/Projects",
                data: pd,
                dataType: "json",
                success: function () {
                    window.location.href = "/projects.html";
                    console.log(pd);
                }
            })
                .fail(function () {
                    alert("创建失败");
                });
        });
    $("#btn-p-edit").click(function (){
        alert("5555");
    });
    $(function () {
        var request = GetRequest();
        $.ajax({
            type: 'GET',
            url: "/api/ProjectDetail",
            data: request,
            dataType: "json",
            success: function (result) {
                if (result.data != null) {
                    $("#detail-p-from").find("#projectName").val(result.data.project);
                    $("#detail-p-from").find("#projectType option[value=" + result.data.type + "]").attr("selected", "selected");
                    $("#detail-p-from").find("#projectLan option[value=" + result.data.language + "]").attr("selected", "selected");
                    var domian = result.data.domain;
                    var projectDomain = "";
                    var prefixName = "";
                    if (domian.indexOf(".api.master") != -1) {
                        projectDomain = domian.substr(0, domian.indexOf(".api.master"));
                        prefixName = domian.substr(domian.indexOf(".api.master"), domian.length);
                    } else if (domian.indexOf(".platform.com") != -1) {
                        projectDomain = domian.substr(0, domian.indexOf(".platform.com"));
                        prefixName = domian.substr(domian.indexOf(".platform.com"), domian.length);
                    } else {
                        projectDomain = domian;
                    }
                    $("#detail-p-from").find("#projectDomain").val(projectDomain);
                    $("#detail-p-from").find("#master-domain").html(prefixName);
                    $("#detail-p-from").find("#GitAddress").val(result.data.git);
                    $("#detail-p-from").find("#attribution").val(result.data.attribution);
                    $("#detail-p-from").find("#groups").val(result.data.groups);
                    $("#detail-p-from").find("#developer").val(result.data.developer);
                    $("#detail-p-from").find("#operations").val(result.data.operations);
                    $("#detail-p-from").find("#description").html(result.data.description);
                }
            }
        });
    });
});

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("?");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

