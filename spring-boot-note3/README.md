Spring boot学习笔记3
============================
## 笔记点：
```
MyBatis Generator自动生成Dao、Model、Mapping插件
生成方式：
	1：git clone 此spring-boot-note3进行maven编译即可生成，在pom.xml同级命令行执行：mvn mybatis-generator:generate
	2：mybatis-generator-core-1.3.5.zip，本地磁盘直接生成
代码支持：
	1：MyBatis普通的Dao、Model、Mapping的生成
	2：Mapper专用MyBatis的Dao、Model、Mapping生成，mapper-spring-boot-starter
```
### 下载全功能mybatis-generator的文件

### IntelliJ IDEA编译
![编译mybatis-generator](http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/github/idea-mvn-generator.jpg.jpg)

### 本地磁盘直接生成

![我的mybatis-generator插件](http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/github/my-mybatis-generator-core-1.3.5.jpg)
MyBatis的Mapper生成器插件
============================================================
注：
我的操作目录：T:\tools\mybatis-generator-core-1.3.5\lib
生成方式有两种：1.普通 2.mapper专用
### 目录说明
```
	jar  jar包文件夹
	xml  配置文件夹
	src  自动生产代码文件
	testsql 测试sql目录
```
### windows系统

### 1：使用Mapper普通的MyBatis生成器插件
```
1.1.配置文件：xml/generatorConfig.1.xml
	a. 自定义数据库驱动地址，classPathEntry
	a. 自定义数据库，jdbcConnection：连接、用户名、密码
	b. 自定义targetProject、targetPackage目录
1.2.删除src下的所有文件，执行脚本：1.bat
    或Shift+鼠标右键，选择“在此处打开命令窗口(W)”后，执行:
	java -Dfile.encoding=UTF-8 -jar jar/mybatis-generator-core-1.3.5.jar -configfile xml/1/generatorConfig.xml -overwrite
```

### 2：使用Mapper专用的MyBatis生成器插件
```
2.1.配置文件：xml/generatorConfig.2.xml
	a. 自定义数据库驱动地址，classPathEntry
	a. 自定义数据库，jdbcConnection：连接、用户名、密码
	b. 自定义targetProject、targetPackage目录
2.2.删除src下的所有文件，执行脚本：cmd/2.bat
    或Shift+鼠标右键，选择“在此处打开命令窗口(W)”后，执行:
	javac -cp jar/mybatis-generator-core-1.3.5.jar;jar/mapper-3.4.2.jar; MyBatisGeneratorExe.java
	java -cp jar/mybatis-generator-core-1.3.5.jar;jar/mapper-3.4.2.jar; MyBatisGeneratorExe
```
