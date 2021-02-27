# redis
## redis cluster
redis 镜像 https://hub.docker.com/_/redis
```shell script
##获取指定版本 redis镜像
docker pull redis:6.2.0
##删除镜像
docker rmi imageId
##删除容器
docker rm containId1 containId2 containId3
##创建6台主从服务器
docker create --name redis-node-1 --net host --privileged=true -v /data/soft_test/redis/share/redis-node-1:/data redis:6.2.0 --cluster-enabled --appendonly yes --port 7701
docker create --name redis-node-2 --net host --privileged=true -v /data/soft_test/redis/share/redis-node-2:/data redis:6.2.0 --cluster-enabled --appendonly yes --port 7702
docker create --name redis-node-3 --net host --privileged=true -v /data/soft_test/redis/share/redis-node-3:/data redis:6.2.0 --cluster-enabled --appendonly yes --port 7703
docker create --name redis-node-4 --net host --privileged=true -v /data/soft_test/redis/share/redis-node-4:/data redis:6.2.0 --cluster-enabled --appendonly yes --port 7704
docker create --name redis-node-5 --net host --privileged=true -v /data/soft_test/redis/share/redis-node-5:/data redis:6.2.0 --cluster-enabled --appendonly yes --port 7705
docker create --name redis-node-6 --net host --privileged=true -v /data/soft_test/redis/share/redis-node-6:/data redis:6.2.0 --cluster-enabled --appendonly yes --port 7706
##启动 服务器
docker run -i -t redis-node-1 /bin/bash
docker start redis-node-1 redis-node-2 redis-node-3 redis-node-4 redis-node-5 redis-node-6
##查看容器
docker ps
##创建集群 每个 master 分配一个salve
src/redis-cli --cluster create 127.0.0.1:7701 127.0.0.1:7702 127.0.0.1:7703 127.0.0.1:7704 127.0.0.1:7705 127.0.0.1:7706 --cluster-replicas 1
##执行结果
cas 1
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460  //分配槽位
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 127.0.0.1:7705 to 127.0.0.1:7701
Adding replica 127.0.0.1:7706 to 127.0.0.1:7702
Adding replica 127.0.0.1:7704 to 127.0.0.1:7703
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 0c88ce3f315db3c82356a1c5efe251de602bac7b 127.0.0.1:7701
   slots:[0-5460] (5461 slots) master //三个主节点 分别分配的槽位[XXX-XXX]
M: 2af2c2671570517bfb7d907f5b08993e4602a610 127.0.0.1:7702
   slots:[5461-10922] (5462 slots) master
M: 3e3c551e22ad19a67f1d6a05b452cf568a348d3d 127.0.0.1:7703
   slots:[10923-16383] (5461 slots) master
S: 1bc336f8bb9a11451410c6fe91a9d4762e1e3daa 127.0.0.1:7704 //三个从节点
   replicates 3e3c551e22ad19a67f1d6a05b452cf568a348d3d
S: 3dd88c81c5940238411f6aeddfa10e6d33714569 127.0.0.1:7705
   replicates 0c88ce3f315db3c82356a1c5efe251de602bac7b
S: a486f2bfa667ec4af350f74a8cb655ce158bd735 127.0.0.1:7706
   replicates 2af2c2671570517bfb7d907f5b08993e4602a610
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
.
>>> Performing Cluster Check (using node 127.0.0.1:7701)
M: 0c88ce3f315db3c82356a1c5efe251de602bac7b 127.0.0.1:7701
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: a486f2bfa667ec4af350f74a8cb655ce158bd735 127.0.0.1:7706
   slots: (0 slots) slave
   replicates 2af2c2671570517bfb7d907f5b08993e4602a610
M: 2af2c2671570517bfb7d907f5b08993e4602a610 127.0.0.1:7702
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
M: 3e3c551e22ad19a67f1d6a05b452cf568a348d3d 127.0.0.1:7703
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 3dd88c81c5940238411f6aeddfa10e6d33714569 127.0.0.1:7705
   slots: (0 slots) slave
   replicates 0c88ce3f315db3c82356a1c5efe251de602bac7b
S: 1bc336f8bb9a11451410c6fe91a9d4762e1e3daa 127.0.0.1:7704
   slots: (0 slots) slave
   replicates 3e3c551e22ad19a67f1d6a05b452cf568a348d3d
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.//一共 16384个槽位

##进入集群
src/redis-cli -p 7701 -c
##查看集群状态
cluster info

cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6//当前节点数
cluster_size:3
cluster_current_epoch:6
cluster_my_epoch:1
cluster_stats_messages_ping_sent:613
cluster_stats_messages_pong_sent:587
cluster_stats_messages_sent:1200
cluster_stats_messages_ping_received:582
cluster_stats_messages_pong_received:613
cluster_stats_messages_meet_received:5
cluster_stats_messages_received:1200

##查看节点状态
cluster nodes

##redis 集群添加master节点 第一个节点表示新增节点 第二个表示 原集群中任意节点
src/redis-cli --cluster add-node 127.0.0.1:7707 127.0.0.1:7702

>> Adding node 127.0.0.1:7707 to cluster 127.0.0.1:7702
>>> Performing Cluster Check (using node 127.0.0.1:7702)
M: 2af2c2671570517bfb7d907f5b08993e4602a610 127.0.0.1:7702
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
M: 3e3c551e22ad19a67f1d6a05b452cf568a348d3d 127.0.0.1:7703
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 3dd88c81c5940238411f6aeddfa10e6d33714569 127.0.0.1:7705
   slots: (0 slots) slave
   replicates 0c88ce3f315db3c82356a1c5efe251de602bac7b
S: 1bc336f8bb9a11451410c6fe91a9d4762e1e3daa 127.0.0.1:7704
   slots: (0 slots) slave
   replicates 3e3c551e22ad19a67f1d6a05b452cf568a348d3d
S: a486f2bfa667ec4af350f74a8cb655ce158bd735 127.0.0.1:7706
   slots: (0 slots) slave
   replicates 2af2c2671570517bfb7d907f5b08993e4602a610
M: 0c88ce3f315db3c82356a1c5efe251de602bac7b 127.0.0.1:7701
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
>>> Send CLUSTER MEET to node 127.0.0.1:7707 to make it join the cluster.//新加入的节点
## 检查集群
src/redis-cli --cluster check 127.0.0.1:7701
...
M: 1d1ebca409076c39a1e53d3cd705a8bca880d345 127.0.0.1:7707
...
##重新分配节点
src/redis-cli --cluster reshard 127.0.0.1:7701
id：要分配的节点 1d1ebca409076c39a1e53d3cd705a8bca880d345
all:所以节点重新洗牌

##为7707添加slave7708
src/redis-cli --cluster add-node 127.0.0.1:7708 127.0.0.1:7702 --cluster-slave --cluster-master-id 1d1ebca409076c39a1e53d3cd705a8bca880d345

src/redis-cli --cluster check 127.0.0.1:7701127.0.0.1:7701 (0c88ce3f...) -> 0 keys | 4096 slots | 1 slaves.
127.0.0.1:7702 (2af2c267...) -> 0 keys | 4096 slots | 1 slaves.
127.0.0.1:7703 (3e3c551e...) -> 0 keys | 4096 slots | 1 slaves.
127.0.0.1:7707 (1d1ebca4...) -> 0 keys | 4096 slots | 1 slaves.
[OK] 0 keys in 4 masters.
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 127.0.0.1:7701)
M: 0c88ce3f315db3c82356a1c5efe251de602bac7b 127.0.0.1:7701
   slots:[1365-5460] (4096 slots) master
   1 additional replica(s)
S: e853d07a78cabd0fa8c1a3e450dc4c356db824b2 127.0.0.1:7708
   slots: (0 slots) slave
   replicates 1d1ebca409076c39a1e53d3cd705a8bca880d345
S: a486f2bfa667ec4af350f74a8cb655ce158bd735 127.0.0.1:7706
   slots: (0 slots) slave
   replicates 2af2c2671570517bfb7d907f5b08993e4602a610
M: 2af2c2671570517bfb7d907f5b08993e4602a610 127.0.0.1:7702
   slots:[6827-10922] (4096 slots) master
   1 additional replica(s)
M: 3e3c551e22ad19a67f1d6a05b452cf568a348d3d 127.0.0.1:7703
   slots:[12288-16383] (4096 slots) master
   1 additional replica(s)
M: 1d1ebca409076c39a1e53d3cd705a8bca880d345 127.0.0.1:7707
   slots:[0-1364],[5461-6826],[10923-12287] (4096 slots) master
   1 additional replica(s)
S: 3dd88c81c5940238411f6aeddfa10e6d33714569 127.0.0.1:7705
   slots: (0 slots) slave
   replicates 0c88ce3f315db3c82356a1c5efe251de602bac7b
S: 1bc336f8bb9a11451410c6fe91a9d4762e1e3daa 127.0.0.1:7704
   slots: (0 slots) slave
   replicates 3e3c551e22ad19a67f1d6a05b452cf568a348d3d
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.

##删除slave4 7708删除从节点
src/redis-cli --cluster del-node 127.0.0.1:7708 e853d07a78cabd0fa8c1a3e450dc4c356db824b2

##删除7707的槽
src/redis-cli --cluster reshard 127.0.0.1:7701

>>> Performing Cluster Check (using node 127.0.0.1:7701)
M: 0c88ce3f315db3c82356a1c5efe251de602bac7b 127.0.0.1:7701
   slots:[1365-5460] (4096 slots) master
   1 additional replica(s)
S: a486f2bfa667ec4af350f74a8cb655ce158bd735 127.0.0.1:7706
   slots: (0 slots) slave
   replicates 2af2c2671570517bfb7d907f5b08993e4602a610
M: 2af2c2671570517bfb7d907f5b08993e4602a610 127.0.0.1:7702
   slots:[6827-10922] (4096 slots) master
   1 additional replica(s)
M: 3e3c551e22ad19a67f1d6a05b452cf568a348d3d 127.0.0.1:7703
   slots:[12288-16383] (4096 slots) master
   1 additional replica(s)
M: 1d1ebca409076c39a1e53d3cd705a8bca880d345 127.0.0.1:7707
   slots:[0-1364],[5461-6826],[10923-12287] (4096 slots) master
S: 3dd88c81c5940238411f6aeddfa10e6d33714569 127.0.0.1:7705
   slots: (0 slots) slave
   replicates 0c88ce3f315db3c82356a1c5efe251de602bac7b
S: 1bc336f8bb9a11451410c6fe91a9d4762e1e3daa 127.0.0.1:7704
   slots: (0 slots) slave
   replicates 3e3c551e22ad19a67f1d6a05b452cf568a348d3d
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
How many slots do you want to move (from 1 to 16384)? 4096//移动4096个槽位
What is the receiving node ID? 0c88ce3f315db3c82356a1c5efe251de602bac7b //7701来接手
Please enter all the source node IDs.
  Type 'all' to use all the nodes as source nodes for the hash slots.
  Type 'done' once you entered all the source nodes IDs.
Source node #1: 1d1ebca409076c39a1e53d3cd705a8bca880d345//从7707 中移除
Source node #2: done

[root@VM_0_10_centos redis-6.2.0]# src/redis-cli --cluster check 127.0.0.1:7701
127.0.0.1:7701 (0c88ce3f...) -> 0 keys | 8192 slots | 1 slaves.
127.0.0.1:7702 (2af2c267...) -> 0 keys | 4096 slots | 1 slaves.
127.0.0.1:7703 (3e3c551e...) -> 0 keys | 4096 slots | 1 slaves.
127.0.0.1:7707 (1d1ebca4...) -> 0 keys | 0 slots | 0 slaves.//7707现在没有槽位了
[OK] 0 keys in 4 masters.
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 127.0.0.1:7701)
M: 0c88ce3f315db3c82356a1c5efe251de602bac7b 127.0.0.1:7701
   slots:[0-6826],[10923-12287] (8192 slots) master
   1 additional replica(s)
S: a486f2bfa667ec4af350f74a8cb655ce158bd735 127.0.0.1:7706
   slots: (0 slots) slave
   replicates 2af2c2671570517bfb7d907f5b08993e4602a610
M: 2af2c2671570517bfb7d907f5b08993e4602a610 127.0.0.1:7702
   slots:[6827-10922] (4096 slots) master
   1 additional replica(s)
M: 3e3c551e22ad19a67f1d6a05b452cf568a348d3d 127.0.0.1:7703
   slots:[12288-16383] (4096 slots) master
   1 additional replica(s)
M: 1d1ebca409076c39a1e53d3cd705a8bca880d345 127.0.0.1:7707
   slots: (0 slots) master
S: 3dd88c81c5940238411f6aeddfa10e6d33714569 127.0.0.1:7705
   slots: (0 slots) slave
   replicates 0c88ce3f315db3c82356a1c5efe251de602bac7b
S: 1bc336f8bb9a11451410c6fe91a9d4762e1e3daa 127.0.0.1:7704
   slots: (0 slots) slave
   replicates 3e3c551e22ad19a67f1d6a05b452cf568a348d3d
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...

##删除7707
src/redis-cli --cluster del-node 127.0.0.1:7707 1d1ebca409076c39a1e53d3cd705a8bca880d345

>>> Sending CLUSTER FORGET messages to the cluster...
>>> Sending CLUSTER RESET SOFT to the deleted node.
[root@VM_0_10_centos redis-6.2.0]# src/redis-cli --cluster check 127.0.0.1:7701
127.0.0.1:7701 (0c88ce3f...) -> 0 keys | 8192 slots | 1 slaves.
127.0.0.1:7702 (2af2c267...) -> 0 keys | 4096 slots | 1 slaves.
127.0.0.1:7703 (3e3c551e...) -> 0 keys | 4096 slots | 1 slaves.
[OK] 0 keys in 3 masters.
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 127.0.0.1:7701)
M: 0c88ce3f315db3c82356a1c5efe251de602bac7b 127.0.0.1:7701
   slots:[0-6826],[10923-12287] (8192 slots) master
   1 additional replica(s)
S: a486f2bfa667ec4af350f74a8cb655ce158bd735 127.0.0.1:7706
   slots: (0 slots) slave
   replicates 2af2c2671570517bfb7d907f5b08993e4602a610
M: 2af2c2671570517bfb7d907f5b08993e4602a610 127.0.0.1:7702
   slots:[6827-10922] (4096 slots) master
   1 additional replica(s)
M: 3e3c551e22ad19a67f1d6a05b452cf568a348d3d 127.0.0.1:7703
   slots:[12288-16383] (4096 slots) master
   1 additional replica(s)
S: 3dd88c81c5940238411f6aeddfa10e6d33714569 127.0.0.1:7705
   slots: (0 slots) slave
   replicates 0c88ce3f315db3c82356a1c5efe251de602bac7b
S: 1bc336f8bb9a11451410c6fe91a9d4762e1e3daa 127.0.0.1:7704
   slots: (0 slots) slave
   replicates 3e3c551e22ad19a67f1d6a05b452cf568a348d3d
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
```
