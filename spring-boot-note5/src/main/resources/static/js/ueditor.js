/**
 * Created by tony on 2017/8/6.
 */
var ue;
$(function () {
    ue = UE.getEditor('editor');
    placeholder(ue, "请在此输入文字..."); //只能文本
    editor = ue;

    //Error: Cannot set property 'innerHTML' of undefined
    ue.addListener("ready", function () {
        // editor准备好之后才可以使用
        initData();
    });
});

function initData() {
    var data = '<h1 style="font-family: &quot;PingFang SC&quot;; white-space: normal;">百度Ueditor富文本编辑器</h1><p>服务器端实现代码：<span style="color: red; font-family: &quot;PingFang SC&quot;;">Java + Spring boot + Aliyun OSS</span></p><p><span style="color: red; font-family: &quot;PingFang SC&quot;;"><br/></span></p><p>若部署为前段Web页面和服务器端Api接口分离部署，则一定要实现严格的跨域配置。包括，js、数据、iframe。</p><p>此项目为web和api同项目部署：已实现编辑器加载配置、图片、涂鸦上传，api数据支持jsonp，其他可自定义扩展。</p><hr/><p>以下为测试数据：<br/></p><p>2017-08-06 15:22:25</p><hr/><p><img src="http://opened-test.oss-cn-hangzhou.aliyuncs.com/resources/ueditor/scrawl/58955731934545aaa49f581e61cfa98d_scrawl.jpg" width="144" height="120" title="" alt=""/></p><hr/><p><img src="http://opened-test.oss-cn-hangzhou.aliyuncs.com/resources/ueditor/image/041a2452d6234ca18e1583db5ebde354girl.jpg" title="girl.jpg" alt="girl.jpg" width="765" height="435"/></p>';
    ue.setContent(data);
}

function sendData() {
    var data = ue.getContent();
    alert(data);
    console.log(data);
}