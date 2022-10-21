## 项目介绍

## 	1.组件

​		Spring Cloud+SSM+Orcale+Consul+RabbitMq

## 	2.环境准备

​		安装idea，orcale数据库，Plsql客户端，Consul客户端，RabbitMq客户端

## 	3.启动步骤

* ````
  consul：服务注册与发现
  	启动consul:			consul.exe agent -dev
      查询节点信息:			 consul members -detailed
      
      web地址				localhost:8500
      停止服务			   consul leave
  ````
  
* ```
  rabbitmq:消息队列
      1.进入rabbitmq的sbin目录，cmd命令运行以下二者之一
         rabbitmq-server -detached     后台运行
         rabbitmq-server                非后台运行
         关闭rabbitmq服务命令：rabbitmqctl stop
      2.同样在上面的目录，cmd命令运行：
         rabbitmq-plugins enable rabbitmq_management 启动
         rabbitmq-plugins disable rabbitmq_management 关闭
      2.运行应用程序rabbitmq-service start 
  
      Rabbit mq端口号		5672
      Rabbit mq web端口号	15672
  ```

* ````
  redis:
  	进入redis安装目录输入
  	redis-server
  ````

  
