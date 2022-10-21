# Oracle数据库相关

## 	安装过程

​	cdb->参数配置->pdb->表空间->用户->导入dmp

## 	相关命令

````
select * from v$version						查看数据库版本
select name,open_mode from v$pdbs			查看所有的pdb
SELECT instance_name FROM v$instance;		查询当前数据库实例

alter table KSE.KSB_SERVICE add OPERASTRATEGY varchar(32) default 'Multiple' not null; 	新增字段带默认值和非空约束
comment on column KSE.KSB_SERVICE.OPERASTRATEGY is '操作策略, Multiple:綜合, Dispatch:任务调度';	注释
alter table KSE.KSB_SERVICE drop column OPERASTRATEGY 	删除一列
````

## 数据库启动方式

````
1、startup nomount

非安装启动，这种方式启动下可执行：重建控制文件、重建数据库

读取init.ora文件，启动instance，即启动SGA和后台进程，这种启动只需要init.ora文件。

2、startup mount dbname

安装启动，这种方式启动下可执行：

数据库日志归档、

数据库介质恢复、

使数据文件联机或脱机，

重新定位数据文件、重做日志文件。

执行“nomount”，然后打开控制文件，确认数据文件和联机日志文件的位置，

但此时不对数据文件和日志文件进行校验检查。

3、startup open dbname

先执行“nomount”，然后执行“mount”，再打开包括Redo log文件在内的所有数据库文件，

这种方式下可访问数据库中的数据。

4、startup，等于以下三个命令

startup nomount

alter database mount

alter database open

5、startup restrict

约束方式启动

这种方式能够启动数据库，但只允许具有一定特权的用户访问

非特权用户访问时，会出现以下提示：

ERROR：

ORA-01035: Oracle只允许具有 RESTRICTED SESSION 权限的用户使用

6、startup force

强制Oracle数据库启动方式

当不能关闭数据库时，可以用startup force来完成数据库的关闭

先关闭数据库，再执行正常启动数据库命令

7、startup pfile=参数文件名

带初始化参数文件的启动方式

先读取参数文件，再按参数文件中的设置启动数据库

例：startup pfile=E:Oracleadminoradbpfileinit.ora

8、startup EXCLUSIVE

以上的相关内容就是对Oracle数据库启动方式的介绍，望你能有所收获。
````





## 数据库导出

````
1. 进入sql plus查看orcale数据库字符集
   命令：select userenv('language') from dual;
   
   远程数据库结果：
   	USERENV('LANGUAGE')
	----------------------------------------------------
	SIMPLIFIED CHINESE_CHINA.AL32UTF8
	
2. 设置导出编码
   命令：export NLS_LANG=SIMPLIFIED CHINESE_CHINA.AL32UTF8;
3. 导出
   本地命令：exp kse/caecaodb@iis owner=kse file=E:\\resource\\dev\\database_dmp\\01Code_CKG\\databasekse.dmp
   远程命令：exp kse/caecaodb@172.30.1.14/iis file=E:\\resource\\dev\\database_dmp\\01Code_CKG\\databasekse.dmp
	
````

## 数据库导入

````
1.导入
	命令：imp kse/caecaodb@gssDb file="E:\\resource\\dev\\database_dmp\\01Code_CKG\\databasekse.dmp" full=y
	命令：imp gss_pdb_can/caecaodb@localhost:1521/gssDb fromuser=kse touser=kse file=E:\\resource\\dev\\database_dmp\\01Code_CKG\\databasekse.dmp

2.如果没有建立表空间，执行：
3.如果没有建立用户，执行：
        create user kse identified by caecaodb
        default tablespace USERS
        temporary tablespace TEMP
        profile DEFAULT
        password expire;
        -- Grant/Revoke role privileges 
        grant connect to kse;
        grant dba to kse;
        grant resource to kse;
        -- Grant/Revoke system privileges 
        grant alter any table to kse;
        grant create any table to kse;
        grant delete any table to kse;
        grant drop any table to kse;
        grant execute any procedure to kse;
        grant export full database to kse;
        grant grant any object privilege to kse;
        grant import full database to kse;
        grant insert any table to kse;
        grant manage tablespace to kse;
        grant select any table to kse;
        grant unlimited tablespace to kse;
        grant update any table to kse;
        
        执行完后edit用户，重新输入密码

````

## 查询建表语句

````java
1.select table_name,dbms_metadata.get_ddl('TABLE','KSB_JOB')from dual,user_tables where table_name='KSB_JOB'
2.使用idea的database,找到对应的表后,点击上方的go to ddl.
````

## ORA-01219: 数据库或可插入数据库未打开: 仅允许在固定表或视图中查询 oracle

````
1.执行以下命令
	select open_mode from v$database;
	alter database open;
	select con_id,dbid,NAME,OPEN_MODE from v$pdbs;
2.上面执行正常，然后切换容器
    alter pluggable database orclpdb open;
    alter session set container=orclpdb;
````

