/**
 * Created by Tony on 2017/8/4.
 */
$(function () {
    // jsUploadFile("/note5/disk/upload.file", "upload", {"type": 10, "source": 5, "desc": "上传文件测试", "own": true});

});
var verified = false;
function changeFile() {
    var exts = ["rar", "zip", "xls", "doc", "pdf", "jpg", "jpeg", "png", "docx", "xlsx", "iso", "mp4"];
    var file = $("#changeFile").get(0).files[0];
    if (!file) {
        warmtips9("err", "您未选择文件");
        return false;
    }
    //低版本浏览器未测试，未支持
    var name = file.name;
    var extension = name.substring(name.lastIndexOf(".") + 1);
    var filesize = file.size;
    // 文件格式
    if (exts.indexOf(extension.toLowerCase()) == -1) {
        warmtips9("err", "文件格式错误");
        return false;
    }
    //文件大小 1M = 1024 * 1024 = 1048576
    if (filesize / (1048576) > 1024) {
        warmtips9("err", "请上传小于1G的文件");
        return false;
    }
    verified = true;
    uploadFile();
}
function uploadFile() {
    if (!verified) {
        warmtips9("err", "请您选择合适的文件后再上传");
        return false;
    }
    var url = $("input[name='target']:checked").val() == "disk" ? '/note5/disk-upload.file' : '/note5/oss-upload.file';
    var own = $("input[name='own']:checked").val() == "no" ? "false" : "true";
    var formData = new FormData();
    formData.append("file", $("#changeFile").get(0).files[0]);
    formData.append("type", 3);  //文件类型：1.视频 2.音频 3.图片 4.压缩文件 5:办公文件
    formData.append("source", 3); //文件平台来源：1.家庭 2.学校 3.公司 4.户外
    formData.append("desc", "上传文件测试");
    formData.append("own", own);
    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        headers: {
            uAT: "B490B5EBF6F3CD402E515D22BCDA1598"
        },
        beforeSend: function () {
            NProgress.start();
        },
        xhr: function () {
            var xhr = $.ajaxSettings.xhr();
            if (progressFunction && xhr.upload) {
                xhr.upload.addEventListener("progress", progressFunction, false);
            }
            return xhr;
        },
        complete: function () {
            NProgress.done();
        },
        success: function (resData) {
            if (isNotNoData(resData)) {
                var code = resData.code;
                var msg = resData.message;
                if (code === 200) {
                    warmtips9("ok", msg);
                    $("#resmsg").text(json2String(resData));
                } else {
                    warmtips9("err", msg);
                }
            } else {
                warmtips9("err", "上传失败，请您重新上传");
            }
        },
        error: function (returndata) {
            warmtips9("err", "请求超时或网络已断开");
        }
    });
}
/**
 * 上传文件
 * @param url 地址
 * @param clickEleId 触发上传事件的元素id
 * @param reqData json格式请求参数数据，例如：{"uAT":"09697364E7954780BC0A8A61FA1087C11ADM"}
 */
function jsUploadFile(url, clickEleId, reqData) {
    // var exts = ["rar", "zip", "xls", "doc", "pdf", "jpg", "jpeg", "png", "docx", "xlsx"];
    new AjaxUpload(document.getElementById(clickEleId), {
        responseType: "json",
        action: url,
        name: "file",
        data: reqData,
        autoSubmit: true,
        onSubmit: function (file, ext) {
            NProgress.start();
        },
        onChange: function (file, extension) {
            //文件为空
            if (!file) {
                warmtips9("err", "您未选择文件");
                return false;
            }
            //文件格式
            // if (exts.indexOf(extension.toLowerCase()) == -1) {
            //     warmtips9("err", "文件格式错误");
            //     return false;
            // }
            //文件大小,ie9后台返回验证,其他前台验证
            var browsernv = getBrowserNV();
            var userAgent = browsernv.substring(0, browsernv.indexOf("."));
            var ageVersion = userAgent.replace(/[^0-9]/ig, "");
            if (!(userAgent.indexOf("ie") != -1 && parseInt(ageVersion) <= 9)) {
                var file = this._input;
                filesize = file.files[0].size;
                if (filesize / (1024 * 1024) > 1024) {
                    warmtips9("err", "请上传小于1G的文件");
                    return false;
                }
            }
        },
        onComplete: function (file, response) {
            NProgress.done();
            if (isNotNoData(response)) {
                var code = response.code;
                var msg = response.message;
                if (code === 200) {
                    warmtips9("ok", json2String(response));
                } else {
                    warmtips9("err", msg);
                }
            } else {
                warmtips9("err", "上传失败，请您重新上传");
            }
        }
    });
}

//监听附件上传进度情况（0.05-0.1秒执行一次）
function progressFunction(event) {
    if (event.lengthComputable) {
        var total = event.total; //总大小
        var loaded = event.loaded; //已上传大小
        var percent = Math.floor(100 * loaded / total);     //已经上传的百分比
        $("#bar").width(percent + '%');
        $("#percent").html(percent + '%');
        $("#loaded").text((loaded / 1048576).toFixed(2))
        $("#total").text((total / 1048576).toFixed(2))
    }
}

function warmtips(flag, msg) {
    close_warmtips();
    if (flag === "err") {
        $("#warmtips").css({"background": "#FFD2D2", "color": "#FF0000"});
    }
    if (flag === "warn") {
        $("#warmtips").css({"background": "#FFFFB3", "color": "#FF8040"});
    }
    if (flag === "ok") {
        $("#warmtips").css({"background": "#D5FFD5", "color": "#008000"});
    }
    if (flag === "loading") {
        $("#warmtips").css({"background": "#C6FFFF", "color": "#8000FF"});
    }
    $("#warmtips").html(msg);
    setInterval("close_warmtips()", 8000);
}

function warmtips9(flag, msg) {
    close_warmtips();
    if (flag === "err") {
        $("#warmtips").css({"background": "#FFD2D2", "color": "#FF0000"});
    }
    if (flag === "warn") {
        $("#warmtips").css({"background": "#FFFFB3", "color": "#FF8040"});
    }
    if (flag === "ok") {
        $("#warmtips").css({"background": "#D5FFD5", "color": "#008000"});
    }
    if (flag === "loading") {
        $("#warmtips").css({"background": "#C6FFFF", "color": "#8000FF"});
    }
    $("#warmtips").html(msg);
}

function close_warmtips() {
    $("#warmtips").text("");
    $("#warmtips").css({"background": "#F9F9F9", "color": "#F9F9F9"});
}