# Maven使用

## 	optional与scope标签的异同

* 相同点：当optional设置为true，scope设置为provided（该依赖不会被打进包)，都能消除传递依赖。
* 不同点：scope为provided注重的逻辑为当前依赖只在编译时存在，运行时由jvm或者容器提供，常见的为servlet-api;而optional设置为true，强调的逻辑才是其他项目引入本模块时默认不提供本依赖，常见的如spring的热部署包。

## 	dependencyManagement标签的使用

​	mavne提供的一种管理依赖版本号的方式，在父项目中通过dependencyManagement标签生命的依赖，子项目引用该依赖时可以不写版本号，默认使用父类中写的版本号。注意：<font color='red'>dependencyManagement只是依赖的生命，不会实际引入依赖</font>

## 	maven依赖机制

​	当maven引入的依赖存在冲突时，会使用哪个依赖呢？主要有两个原则

* 短路径长度优先原则

  A->B->C->X(1.0),A->D->X(2.0)，将会使用后面路径较短的X(2.0)依赖

* 先声明有限原则

  当依赖引入的长度一样时，使用先声明的依赖

## 单元测试ut行覆盖率

引入maven-surefire-report插件，[使用方法](https://blog.csdn.net/xiaokanfuchen86/article/details/124793566)