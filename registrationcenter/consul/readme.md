# Consul相关

## 	服务启动

​	命令：consul.exe agent -dev  		可视化web端口：8500

## 默认配置：

consul的注册中心安装在服务器上，因此使用只需要注册客户端

````
spring设置								consul			默认值
spring.cloud.consul.host								127.0.0.1
spring.cloud.consul.port								8500
spring.cloud.cousul.discovery.service-name	id			${appName}-${serverPort}
spring.cloud.consul.discovery.instanceId	address
````

