/**
 * Created by tony on 2017/8/6.
 * ueditor富文本编辑器工具
 */
function placeholder (editor, justPlainText) {
    var _editor = editor;
    _editor.addListener("focus", function () {
        var localHtml = _editor.getPlainTxt();
        if (localHtml.trim() === justPlainText.trim()) {
            _editor.setContent("");
        }
    });
    _editor.addListener("blur", function () {
        var localHtml = _editor.getContent();
        if (!localHtml) {
            _editor.setContent(justPlainText);
        }
    });
    _editor.ready(function () {
        _editor.fireEvent("blur");
    });
};