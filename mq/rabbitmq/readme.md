# Rabbit Mq相关

## 启动方式

````
1.进入rabbitmq的sbin目录，cmd命令运行以下二者之一
	rabbitmq-server -detached		后台运行
	rabbitmq-server					非后台运行
	关闭rabbitmq服务命令：rabbitmqctl stop
2.同样在上面的目录，cmd命令运行：
	rabbitmq-plugins enable rabbitmq_management 启动
	rabbitmq-plugins disable rabbitmq_management 关闭
2.运行应用程序rabbitmq-service start	

````

## 占用端口号

````
Rabbit mq端口号		5672
Rabbit mq web端口号	15672
````

