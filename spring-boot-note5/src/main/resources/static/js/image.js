/**
 * Created by tony on 2017/8/6.
 */
$(function () {
    loadImgVerified();
    $("#img-verified").click(function () {
        loadImgVerified();
    });
    $("#img-verified-refresh").click(function () {
        loadImgVerified();
    });
    $(".btn").click(function () {
        loadImgQRcode();
    });
});

function loadImgVerified() {
    $("#img-verified").attr("src", "http://192.168.2.152:5205/note5/img-verified.image?time=" + timestampsuffix());
}
function loadImgQRcode() {
    var url = $("#url").val();
    $("#img-qrcode").attr("src", "http://192.168.2.152:5205/note5/img-qrcode.image?url=" + url + "&time=" + timestampsuffix());
}