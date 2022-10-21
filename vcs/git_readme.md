

# git基础命令：

    git remote add <name> <url>         建立与远程仓库连接
    git remote rm <name>                取消与远程仓库建立连接
    
    git log --pretty=oneline			日志，按q结束
    git reset --hard 81c9aa
    git push -f                         强制回退版本
    
    git revert -n 81c9aa
    git commit -m "xxx"
    git push                            撤销某个版本
    
    git push --set-upstream origin master   每次提交当前分支到远程分支,需要这样设置<master>是远程名，大多数用的origin
    git push --force <远程主机名> <本地分支名>:<远程分支名>
    
    git commit -A 						commit所有包括没有add的文件

# git三大分区

工作区            暂存区         commit history版本库											远程仓库	

​			->add		     ->commit                  					->push

# git撤销add

````
git reset HEAD 撤销上一次add所有内容
git reset HEAD XXX 	撤销指定文件的add
````

# git撤销commit

````
1.git reset --soft HEAD^

--mixed(默认)		不删除工作空间改动代码，撤销commit，并且撤销add，默认参数
--soft			 不删除工作空间改动代码，撤销commit，不撤销add
--hard			 删除工作空间改动代码，撤销commit，撤销add
````

**方法一：git rm(适用于将commit的文件删除)**

1.被提交到仓库的某个文件需要删除，可以使用git rm命令

````
git rm <file> 			从工作区和暂存区删除某个文件
git commit -m ""		再次提交到仓库
````

2.如果只想从暂存区删除文件，本地工作区不做改变.

````
git rm --cache <file>	
````

3.如果在工作区删错了文件，可以使用git checkout将暂存区的文件覆盖工作区的文件，从而把误删的文件清除

````
git checkout -- <file>
````

4.用git rm删除文件，同时还会将这个删除操作记录下来；用rm删除文件，删除的仅仅是本地物理文件，没有将其从git的记录中删除

5.git add和git rm有相似的功能，但git add仅能记录添加、改动的动作，删除的动作需要靠git rm来完成。

**方法二：git  reset**

* git reset ：回滚到某次提交
* git reset --sort:此次提交之后的修改会被退回到暂存区
* git reset --hard:此次提交之后的修改不做任何保留，git status查看工作区是没有记录的

如果需要删除的commit是最新的，那么可以通过git reset命令将代码回滚到之前某次提交的状态，但一定要将现有代码做好备份，否则回滚这些变动都会消失

​	1）回滚代码

````
git log							//查询要回滚的commit id
git reset --hard commit_id		//head就会指向此次的提交记录
git push origin HEAD --force	//强制推送到远端
````

2）误删恢复

````
git reflog						git log只能查看HEAD及之前的版本信息
git reset --hard hash
````

**方法三：git rebase**

适用情形：当两个分支不在一条线上，需要执行merge操作时使用该命令

1）撤销提交

如果中间的某次commit需要删除(撤销),可以通过git rebase命令实现，方法如下

````
git log						//查找需要删除的前一次提交的commit_id
git rebase -i commit_id     //将commit_id替换为复制的值，-i代表交互模式
进入vim编辑模式，将要删除的commit前面的pick改为drop
保存并退出vim
````

2）解决冲突

该命令执行时极有可能出现reabase冲突，可以通过以下方法解决

````
git diff					//查看冲突
//手动解决冲突（冲突位置已在文件中标明)
git add <file>或git add -A 添加
git rebase --continue		//继续rebase
//若还在rebase状态，则重复2、3、4直至rebase完成出现applying字样
git push
````

**方法四：git revert**

* git revert:放弃某次提交
* git revert之前的提交仍会保留在git log中，而此次撤销会作为一次新的提交
* git revert -m:用于对merge节点的操作，-m指定具体某个提交点

1）撤销提交

要撤销中间某次提交时，使用git revert 也是一个很好的选择

````
git log					//查找需要撤销的commit id
git revert commit_id 	//撤销这次提交
````

2）撤销merge节点提交

如果这次提交是merge节点的话，则需要加上-m指令

````
git revert commit_id m 1	//第一个提交点
//手动解决冲突
git add -A
git commit -m ""
git revert commit_id -m 2	//第二个提交点
//重复解决冲突和add
git push
````

# git修改comment

````
git commit --amend		此时进入vim编辑器，修改完保存
````

# git rm命令详解

````
git rm == rm + git add			删除工作区文件并提交到暂存区
git rm删除的文件必须是没有经过修改的，也就是说必须要和当前版本库内容一致，git commit后版本库中此文件记录也会被删除

1.当工作区中的文件经过修改后，添加-f参数表示强制删除工作区文件，并将删除添加到暂存区
2.当工作区中的文件经过修改，使用git add命令添加到暂存区后，再使用git rm命令时，使用-f参数表示强制删除工作区和暂存区的文件，并将删除添加到暂存区

git rm --cache		删除暂存区的文件，但是会保留工作区的文件，并将删除提交到暂存区
因此：文件从暂存区中删除掉，即不会被提交到版本库中，也就是说此文件被取消了版本控制
注意：--cache参数删除的文件必须是已经被追踪的文件，即之前被版本控制的文件
````

# git add命令详解

````
git add -u (git add -update)		提交所有被删除和修改的文件到暂存区
git add .							提交所有修改和新建的数据到暂存区
git add -A (git add -all)			提交所有被删除、修改、替换、新增的文件到数据暂存区
````

# git restore命令详解

````
git restore xxx 使得在工作空间但是不在暂存区的文件撤销修改（内容恢复到没修改之前的状态)
git restore --stage是将暂存区的文件从暂存区撤出，但不会更改文件的内容
````

# git checkout命令详解

````
git checkout -- <file>		将暂存区的文件覆盖工作区的文件，从而把误删的文件清除
````

# 相关问题

1.已经将.idea文件提交到远程仓库，如何取消

答：使用git rm -cache -r .idea将文件从暂存区删除，并推到远程

2.如何忽略.idea文件夹？

答：安装.gitignore插件，右击.idea文件夹，选择git，选择add to ignore
