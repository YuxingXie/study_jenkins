安装gitLab

https://about.gitlab.com/install/#centos-7

失败了，还好有docker镜像版本可以安装。
```text
docker pull gitlab/gitlab-ce

docker run -d --hostname 192.168.80.128 -p 443:443 --name gitlab --restart always -v $GITLAB_HOME/config:/etc/gitlab:z -v $GITLAB_HOME/logs:/var/log/gitlab:z -v $GITLAB_HOME/data:/var/opt/gitlab:z --shm-size 256m gitlab/gitlab-ce

```
