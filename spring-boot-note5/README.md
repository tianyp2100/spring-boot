 Spring boot学习笔记5
============================
## 笔记点：
```
1. 上传文件到磁盘、阿里云OSS对象存储
2. 上传文件大小限制、上传进度条
3. jetty服务器
4. 百度富文本编辑器Ueditor服务器Api
5. 基于GraphicsMagick、im4java、普通awt压缩图片
6. 图片和base64字符串转换
7. 算数图片验证码
8. 二维码
9. 高版本iphone和android在h5上web上传图片旋转bug解决
10. 自定义异常
11. java正则表达式
```

|功能|链接|
|---|---|
|百度Ueditor富文本编辑器|http://192.168.2.152:5205/note5/ueditor.html|
|文件上传页面(磁盘、阿里云对象存储)(大小、进度)|http://192.168.2.152:5205/note5/upload.html|
|上传资源文件到服务器磁盘|http://192.168.2.152:5205/note5/disk-upload.file|
|上传资源文件到阿里云OSS对象存储服务器(公有、私有)|http://192.168.2.152:5205/note5/oss-upload.file|
|获取阿里云私有链接签名地址|http://192.168.2.152:5205/note5/oss-sign-url.json|
|算数图片验证码、二维码|http://192.168.2.152:5205/note5/image.html|
|附件：jar包|jars/*.jarx，注：修改扩展名为jar即可|

### 图片验证码
![图片验证码](http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/default/img-verified-eg.jpeg)
### 二维码
![二维码](http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/default/defaultqrcode.jpeg)
### 上传页面
![上传页面](http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/github/image-upload-html.png)
