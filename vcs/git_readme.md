

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

git rm --cached		删除暂存区的文件，但是会保留工作区的文件，并将删除提交到暂存区
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
git restore --staged是将暂存区的文件与HEAD中保持一致，但不会更改文件(工作区)的内容
````

# git checkout命令详解

````
git checkout -- <file>		将暂存区的文件覆盖工作区的文件，从而把误删的文件清除
````

# git diff命令详解

````
git diff 或者git diff file			本地工作区和暂存区的diff信息
git diff --cached 					 暂存区和版本库的diff信息
git diff commit1 commit2 /b1 b2		 不同commit/分支的diff信息
````



# git rm --cache与git restore --staged区别

````
git rm --cached file：从索引/暂存区中删除文件的副本，而不触及工作树副本。提议的下一次提交现在缺少该文件。如果当前提交有该文件，而实际上此时您确实进行了下一次提交，则上一次提交和新提交之间的区别在于该文件已消失。

git restore --staged file：Git 将文件从HEAD 提交复制到索引中，而不涉及工作树副本。索引副本和 HEAD 副本现在匹配，无论它们之前是否匹配。现在进行的新提交将具有与当前提交相同的文件副本。

如果当前提交缺少该文件，这具有从索引中删除该文件的效果。所以在这种情况下它和git rm --cached做同样的事情。

git reset <em>file</em>：这会将文件的HEAD 版本复制到索引中，就像git restore --staged <em>file</em>。
````



# 忽略某些文件

**1.需要忽略的文件没有提交**

a)如果只是个人想要忽略，则在项目根路径的.git\info\exclude中添加内容，文件内容见c)

b)如果想要项目所有成员都忽略，则在仓库根目录新建.gitignore文件，添加忽略内容。gitignore文件可参考：https://github.com/github/gitignore

c)文件内容参考

````
/.idea/
/**/target/
````

文件内容规则：

````
1.以斜杠/开头表示目录；
2.以星号*通配多个字符；
3.以问号?通配单个字符
4.以方括号[]包含单个字符的匹配列表；
5.以叹号!表示不忽略(跟踪)匹配到的文件或目录；

此外，git 对于 .ignore 配置文件是按行从上到下进行规则匹配的，意味着如果前面的规则匹配的范围更大，则后面的规则将不会生效

注意：
“#” 表示注释
“!”  表示取消忽略
空行不作匹配
若匹配语句中无“/ ” ,便将其视为一个 glob匹配，如'abc'可以匹配 ' abc' , 'cd/abc' , 'ef/abcde.txt'
若匹配语句中有'/ ' ,便视为一个路径匹配，如'abc/'可以匹配 ' abc' , 'cd/abc' ，但是无法匹配 'ef/abcde.txt'
若匹配语句以'/ ' 开始，便视为匹配当前目录，如'/abc'可以匹配 ' abc' 但无法匹配 'cd/abc' 
** 表示匹配零到多级目录
````

2.**需要忽略的文件已经提交**

````
2.1编辑.gitignore文件。
2.2然后如果是单个文件，可以使用如下命令从仓库中删除：
	git rm --cached logs/xx.log
	如果是整个目录：
	git rm --cached -r logs
	如果文件很多，那么直接
	git rm --cached -r .
	如果提示某个文件无法忽略，可以添加-f参数强制忽略。
	git rm -f --cached logs/xx.log
2.3 然后
	git add .
	git commit -m "Update .gitignore"

把被忽略的某个文件强制添加回去：
git add -f filename

ignore规则检查：
git check-ignore
````



# 相关问题

1.已经将.idea文件提交到远程仓库，如何取消

答：使用git rm -cache -r .idea将文件从暂存区删除，并推到远程

2.如何忽略.idea文件夹？

答：安装.gitignore插件，右击.idea文件夹，选择git，选择add to ignore，最终项目根路径所在目录的git\info\exclude中会增加配置信息
