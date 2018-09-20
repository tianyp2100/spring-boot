Spring boot学习笔记4
============================
## 笔记点：
1. 支持Tomcat的http/https(ssl/tls)的双协议（双端口）（v8.5.16）
2. 支持Freemarker模板的Html+Css+Js页面
## JDK自带工具keytool生成ssl证书
##### JDK自带生成SSL证书的工具：keytool
##### HTTPS其实是有两部分组成：HTTP + SSL / TLS
#### 系统参数
```
Microsoft Windows [版本 10.0.14393]
(c) 2016 Microsoft Corporation。保留所有权利。
```
#### JDK参数
```
java version "1.8.0_121"
Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)
```
#### 生成证书
##### winndows
```
keytool -genkey -alias loveshare -keypass 123456 -keyalg RSA -keysize 1024 -validity 520 -storepass 123456 -keystore D:/rsakey/loveshare.keystore
```
##### mac
```
cd /Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home/bin
keytool -genkey -alias loveshare -keypass 123456 -keyalg RSA -keysize 1024 -validity 520 -storepass 123456 -keystore ~/loveshare.keystore
```
##### 使用keytool命令生成证书：
```
keytool -genkey 
-alias loveshare(别名) 
-keypass 123456(别名密码) 
-keyalg RSA(算法) 
-keysize 1024(密钥长度) 
-validity 520(有效期，天单位) 
-keystore D:/keys/loveshare.keystore(指定生成证书的位置和证书名称) 
-storepass 123456(获取keystore信息的密码)
```
#### 执行过程
![生成证书](http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/github/jdk-keytool.jpg)
#### 此处端口：
```
http  5204
https 7204
```

|功能|链接|
|---|---|
|Validator数据验证 + i18n国际化的提示|http://192.168.1.104:5204/note4/swagger-ui.html|
|安全关闭spring-boot服务|curl -X POST http://192.168.1.104:5204/note4/shutdown|
|Http|http://192.168.1.119:5204/note4/test.json|
|Https|https://192.168.1.119:7204/note4/test.json|
|web页面1|http://192.168.1.119:5204/note4/|
|web页面2|http://192.168.1.119:5204/note4/gy.html|
|web页面3|https://192.168.1.119:7204/note4/gy.girl|
|附件：证书文件夹|rsakey/loveshare.keystore|

#### spring boot 启动日志
```
......
2017-08-04 16:58:51.743 - INFO  - 13616 - [main ] - org.apache.catalina.core.StandardEngine                          : Starting Servlet Engine: Apache Tomcat/8.5.16
......
2017-08-04 16:58:53.174 - INFO  - 13616 - [main ] - o.s.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer  : Tomcat started on port(s): 5204 (http) 7204 (https)
......
```
