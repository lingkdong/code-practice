#redis
##redis cluster 
redis 镜像 https://hub.docker.com/_/redis
```shell script
docker pull redis:6.2.0 ##获取指定版本 redis镜像
docker rmi imageId ##删除镜像
docker rm containId1 containId2 containId3 ##删除容器
docker create --name redis-node-1 --net host --privileged=true -v /data/soft_test/redis/share/redis-node-1:/data redis:6.2.0 --cluster-enabled --appendonly yes --port 7701
```