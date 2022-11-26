## 安装gitLab

https://about.gitlab.com/install/#centos-7

失败了，还好有docker镜像版本可以安装。

安装过程见 https://github.com/YuxingXie/linux

安装后gitlab访问地址为http://192.168.80.128 。

用户名/密码：root/12345678

## 把本项目push到gitlab仓库

因为这个项目已经在github上了，所以需要增加一个远程仓库地址。


首先在在gitlab上新建项目“study_jenkins”，项目名记得和GitHub上一样,并创建一个master分支。

然后添加远程仓库地址：
```text
git remote add gitlab http://192.168.80.128/root/study_jenkins.git
#如果加错了可用如下命令删除
git remote remove gitlab
```
用git remote -v命令查看下：
```text
git remote -v
gitlab  http://192.168.80.128/root/study_jenkins.git (fetch)
gitlab  http://192.168.80.128/root/study_jenkins.git (push)
origin  https://github.com/YuxingXie/study_jenkins.git (fetch)
origin  https://github.com/YuxingXie/study_jenkins.git (push)
```
github上的仓库名（可能不应该叫仓库名而叫remote name）叫origin，gitlab的remote name叫gitlab。

这里还有一个更好的方法，就是gitlab可以从github直接import项目，我直接把github上的study_jenkins项目import过来就好了。

这个时候用git pull,push等命令只会从GitHub推拉，要pull push成功，需要加仓库名：
```text
#只会推到github
git push
#指定推到gitlab
git pull gitlab master:master
```
所以需要push两次，希望之后有更好的方案。

## centos安装Jenkins服务器
官网：https://www.jenkins.io/zh/
### 1. 下载jenkins
下载jenkins到/root/software/：
```text
cd /root/software/
curl -o jenkins.war https://mirror.xmission.com/jenkins/war-stable/2.346.3/jenkins.war
```

### 2. 安装jdk

```text
yum search java|grep jdk
yum install -y java-1.8.0-openjdk
```
### 3. 运行Jenkins

```text
#可指定端口，默认8080
java -jar jenkins.war
# 或者后台运行
nohup java -jar jenkins.war &
```

注意下面这段打印：
```text
Jenkins initial setup is required. An admin user has been created and a password generated.
Please use the following password to proceed to installation:

2c9b11b02243496a9299f650ce304489

This may also be found at: /root/.jenkins/secrets/initialAdminPassword

```
浏览器访问http://192.168.80.128:8080/ ，输入上面的密码“2c9b11b02243496a9299f650ce304489”，进入新手入门页面。
点击“安装的插件”，稍等片刻安装完成。

安装完成后，进入“创建第一个管理员用户”，我使用root:123456。然后进入一个暂时搞不懂的工作台页面。

注意：不要轻易升级jenkins，说不定就启动不了。有可能是需要jdk版本不一样。

### 4. jenkins dashboard

jenkins的工作台页面出现了一些搞不懂的东西，现在来搞懂它。
![](https://github.com/YuxingXie/study_jenkins/blob/master/assets/img/jenkins001.png)
![](http://192.168.80.128/root/study_jenkins/-/blob/master/assets/img/jenkins001.png)
jenkins用于流水线或者说自动化的构建。就是说我们的代码提交到gitlab，它帮我们编译、打包、部署，流水线作业，全部完成。

#### 4.1 item

一个item即一个任务。点击左侧“新建item”，进入页面。有freestyle project和pipeline两种风格的任务。

#### 4.2 Manage Jenkins

系统配置下有如下按钮

##### 系统配置：配置全局设置和路径

现在点进去看一下，基本还看不明白。

##### 全局工具配置：工具配置，包括它们的位置和自动安装器

点进去看一下，这个比较好理解一些。既然jenkins是帮我们一条龙编译、打包、部署，肯定离不开这些相关的工具。
比如maven或gradle，jdk环境，连接代码仓库和生产环境的工具等应该都需要的。

因为本研究项目初始化的时候是一个gradle构建的项目，而视频教学使用maven，这个还得靠我自己研究。我先自己配置了一下gradle。
![](https://github.com/YuxingXie/study_jenkins/blob/master/assets/img/jenkins002.png)
![](http://192.168.80.128/root/study_jenkins/-/blob/master/assets/img/jenkins002.png)
jdk也配置一下：
![](https://github.com/YuxingXie/study_jenkins/blob/master/assets/img/jenkins003.png)
![](http://192.168.80.128/root/study_jenkins/-/blob/master/assets/img/jenkins003.png)
##### 插件管理
添加、删除、禁用或启用Jenkins功能扩展插件。
##### 插件管理节点管理
添加、删除、控制和监视系统运行任务的节点。

### 5. 配置并手动运行一次gradle构建任务

看完了视频作者jenkins构建的maven项目过程，我感觉gradle应该类似吧。自己动手试了一下手动启动一个任务让jenkins编译打包，结果是成功的，不信你看：

![](https://github.com/YuxingXie/study_jenkins/blob/master/assets/img/jenkins004.png)
<div align="center">图四：jenkins执行gradle clean和build指令</div>


![](https://github.com/YuxingXie/study_jenkins/blob/master/assets/img/jenkins005.png)
<div align="center">图五：jenkins打好的jar包</div>

过程还好不算太坎坷，下面一步步介绍。

jdk,git这些的安装配置不讲了，因为视频作者讲过。

#### 5.1 第一步：新建任务

选“构建一个自由风格的软件项目”，名称叫“gradleBuild”吧。

#### 5.2 第二步：配置源码管理

让jenkins知道代码从哪里拉过来。在“源码管理”中选“git”，把仓库地址，用户名密码，拉取的分支都填进去。
![](https://github.com/YuxingXie/study_jenkins/blob/master/assets/img/jenkins006.png)

#### 5.3 第三步：配置构建环境

因为使用gradle，点击“增加构建步骤”，gradle选7.5.1,没别的原因，保持和我开发环境上一样。
在tasks里面填上“clean build”。
![](https://github.com/YuxingXie/study_jenkins/blob/master/assets/img/jenkins007.png)

#### 5.4 第四步：启动任务
回到dashboard,可以看到刚建好的任务“gradleBuild”，点击右侧的绿色三角形按钮，开始构建。
结果见图五、图六。成功！


## 搭建测试服务器

之前我的gitlab服务器、jenkins服务器都放在同一个服务器192.168.80.128上，现在测试服务器我仍放同一个服务器上。



##### 从git服务器拉代码

##### 使用maven构建

##### 使用gradle构建
