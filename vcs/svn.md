# svn基础命令

```
svn ls svn_repository_url --depth immediates	列出指定url下所有分支
svn info										列出分支信息
svn status										列出分支状态 A:add新增 C:conflict冲突 D:删除
												M:modify修改 G:modify and merged 修改并已经合并
												u:update 从服务器更新 R:relplace从服务器替换 I:ignore忽略
												？问号代表不在SVN管理范围!代表本地代码与服务器不一致
												L:已锁定
												
												
												
svn status --no-ignore							显示svn忽略的文件
svn propset svn:ignore *.class .				忽略指定文件 第二个参数是文件或文件夹，第二个参数说明是在哪个目录
												结合起来就是，忽略指定目录下的wen'jian
svn propset svn:ignore -r *.class .				递归忽略

svn revert -R * 								放弃所有本地修改



```

# svn提交时忽略.idea文件夹和*.iml文件

情形一：文件没有纳入版本管理

​		措施：tortoiseSVN->Setting->general->global ignore pattern中填入*.iml,(如果要忽略target文件夹，填入target *.settings .classpath .project中间用空格隔开)

![](D:\dev\idea\snippets-lab\idea\images\image-20221009110312254.png)

情形二：文件已纳入版本管理

措施: 

创建一个changelist，建议命名为ignore，将不想要被提交的文件拖动到ignore中(或者右击move to another changelist),提交时只提交default

# svn合并不同仓库的代码错误

错误提示：Server SSL certificate verification failed: certificate issued for a different hostname, issuer is not trusted

解决方案：

1.setting->version control->subversion 开启交互模式

![](D:\dev\idea\snippets-lab\idea\images\image-20221019171337240.png)

2.回到svn仓库页面

进入方式：svn(vcs) ->get from version control

![image-20221019171749713](D:\dev\idea\snippets-lab\idea\images\image-20221019171749713.png)

3. svn ls https://*/svn/ 

   会提示输入本地电脑用户的密码，以及svn的用户名和密码

   或者svn ls https://*/svn/  --username chenguo

# 忽略target文件夹

选中指定项目svn->subverse->edit properties

![image-20221019204642164](D:\dev\idea\snippets-lab\idea\images\image-20221019204642164.png)

如上，property name填写svn:ignore，下面value追加上target（最后面的update properties recursively表示在执行目录的子目录也应用该规则）最后如果target文件夹是深黄色代表忽略成功

# idea提示do you want to schedulue the following file for deletion from subversion

原因：项目删除的文件已经加入svn，询问是否计划从schedule删除文件。

# svn status提示local missing or deleted or moved away, incoming dir edit upon merge Summary

![image-20221019210921859](D:\dev\idea\snippets-lab\idea\images\image-20221019210921859.png)

这种情况通常发生在其他人删除文件并提交后，使用者编辑文件后发生。作为良好的svn公民，您需要在提交之前进行更新。现在您有冲突，意识到从工作副本删除文件是正确的做法。svn希望最终看到文件已删除。

1.命令解决冲突

````
svn resolve main
````

2.手动解决冲突，然后输入以下命令通知svn服务器冲突已解决

````
svn resolved main
````

3.如下

````
mkdir main 				如果是文件可以用touch main
svn revert main
rm main					idea下terminal选择power shell可以使用linux命令
````



# svn总是提示authentication required

步骤1：检查setting->version control->subversion配置

path to subversion选择本地安装的svn的bin目录中的可执行文件svn.exe，

如果没有则需要重新安装，安装时注意勾选command line client tools

![img](D:\dev\idea\snippets-lab\idea\images\70)

* ![](D:\dev\idea\snippets-lab\idea\images\image-20221019222352215.png)

enable interative mode :打开交互模式，即提示信息

Use custom configuration directory:使用自定义的配置目录，不建议更改（不勾选）

步骤二：

cmd输入svn ls 仓库地址			然后输入本机用户密码接着输入svn用户名及密码

步骤三：

检查弹出框下面是完整url，查看是否有中文或者乱码

![image-20221020092409424](D:\dev\idea\snippets-lab\idea\images\image-20221020092409424.png)

步骤四：

svn->get from version control 将中文乱码改过来

# idea合并代码时提示There are local changes that will intersect with merge changes.如下

![image-20221020103223610](D:\dev\idea\snippets-lab\idea\images\image-20221020103223610.png)

原因：本地代码与合并过来的代码有冲突

shelve local changes：搁置本地代码，选择该项会将本地改变暂存起来，可以在subverse的self标签查看，如果想要恢复可以右击选择unshelve.日常使用时也可以右击local change 选择shelve进行暂存

inspect changes：检查，idea会提示你哪些本地的改动会与远程仓库冲突

![image-20221020104413178](D:\dev\idea\snippets-lab\idea\images\image-20221020104413178.png)

合并时有多个merge未处理时可以手动，把changlist合并到changes里面，并把空的changelist删除

![image-20221020104822394](D:\dev\idea\snippets-lab\idea\images\image-20221020104822394.png)

# svn合并不同项目做法

1）进入subversion working copies information,点击configure branches

![image-20221020111252181](D:\dev\idea\snippets-lab\idea\images\image-20221020111252181.png)

2）将需要合并进来的远程仓库url加入进来

![image-20221020111427646](D:\dev\idea\snippets-lab\idea\images\image-20221020111427646.png)

3）点击merge from，选择刚刚填写的branch locations对应的项目名

![image-20221020111539725](D:\dev\idea\snippets-lab\idea\images\image-20221020111539725.png)

4）选择quick manual select，即可将指定的commit合并进来

![image-20221020111641935](D:\dev\idea\snippets-lab\idea\images\image-20221020111641935.png)

5）记得勾选左侧复选框，完成点击右下角的merge。后续解决冲突见上一节。

![image-20221020111748296](D:\dev\idea\snippets-lab\idea\images\image-20221020111748296.png)
