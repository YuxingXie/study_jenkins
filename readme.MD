## 安装gitLab

https://about.gitlab.com/install/#centos-7

失败了，还好有docker镜像版本可以安装。

安装过程见 https://github.com/YuxingXie/linux

## 把这个项目push到gitlab仓库

因为这个项目已经在github上了，所以需要增加一个远程仓库地址。


首先在在gitlab上新建项目“study_jenkins”，项目名记得和GitHub上一样,并创建一个master分支。

然后添加远程仓库地址：
```text
git remote add gitlab http://192.168.80.128/gitlab-instance-87af5798/study_jenkins.git
#如果加错了可用如下命令删除
git remote remove gitlab
```
用git remote -v命令查看下：
```text
git remote -v
gitlab  http://192.168.80.128/gitlab-instance-87af5798/study_jenkins.git (fetch)
gitlab  http://192.168.80.128/gitlab-instance-87af5798/study_jenkins.git (push)
origin  https://github.com/YuxingXie/study_jenkins.git (fetch)
origin  https://github.com/YuxingXie/study_jenkins.git (push)
```
github上的仓库名叫origin，gitlab的仓库名叫gitlab。

这个时候用git pull,push等命令只会从GitHub推拉，要pull push成功，需要加仓库名：
```text
git pull gitlab
```
sss