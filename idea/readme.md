# idea使用

## 获取与激活

获取：官网下载2021.3.3版本

激活：见微信里面收藏或者百度网盘软件->idea破解->idea激活(推荐)

## 设置全局maven地址及仓库地址
https://github.com/chenguo-design/snippets-lab/blob/master/idea/images/image-20221003113840864.png

1. 打开idea，关闭所有已经打开的项目，回到idea欢迎界面。
2. 点击左侧customize ![](D:\dev\idea\snippets-lab\idea\images\image-20221003113840864.png)

3. 在build,execution,deployment->build tools->maven中进行相关设置![](D:\dev\idea\snippets-lab\idea\images\image-20221003114045906.png)

## idea中用指定外部程序打开文件

1. 进入setting->tools->external tools。

2. 点击左上角+号

   ![](D:\dev\idea\snippets-lab\idea\images\image-20221003115456948.png)

3. 填入程序对应的文件，参数，工作目录会自动填充。<img src="D:\dev\idea\snippets-lab\idea\images\image-20221003115823859.png" style="zoom:200%;" />

4. 右击文件，即可选择用刚才添加的程序打开该文件

![d](D:\dev\idea\snippets-lab\idea\images\image-20221003120147049.png)

## idea项目文件结构

1. **.idea文件夹**

	![](D:\dev\idea\snippets-lab\idea\images\image-20221003121507492.png)

		encoding.xml: 指定项目中文件的编码方式
		
		jarRepositories.xml: 远程仓库配置文件，指定远程仓库的id，name，以及url
		
		workspace.xml: maven相关配置就存放在这里面

2. **iml文件**

   模块配置文件，打开project structure->module，选中某个模块，对模块相关的配置都会以文件的形式存在iml文件中（如果项目中iml文件缺失，可以到项目根目录，运行mvn idea:module命令，其中可设置的主要有以下内容：

   - 文件夹类型: 主要有sources, Tests，Resources, TestResources, Excluded
   - 编译文件的存放位置
   - 模块依赖

## web项目artifacts类型及区别

- **war**

  将工程打成war包，然后上传到服务器发布，默认存放在target目录下（整个projects存放的是所有模块的输出目录，也会存放该war包，默认是在项目根目录的out文件夹下，可在project structure的project标签中更改）

  ![](D:\dev\idea\snippets-lab\idea\images\image-20221003154726382.png)

- **war exploded**

  将web工程以当前文件夹的形式上传到服务器，二者的输出目录结构大致如下：

  ![](D:\dev\idea\snippets-lab\idea\images\image-20221003160015664.png)

## spring MVC项目404常见原因分析

1. **idea设置问题**

   * 进入project structure->module 下面的web这个facets的web resource directories是否报红，即使不报红也要检查是否对应项目中web文件夹（部分项目是webapp文件夹），尤其<font color='red'>注意是不是下面的WEB-INF目录</font>。![](D:\dev\idea\snippets-lab\idea\images\image-20221003194121592.png)
   * 进入project structure->artifacts，点击tomcat发布的artifact，查看output layout中是否有对应模块的web facets resources。如果没有点击output layout下面的+号，选择java ee facets resources。![](D:\dev\idea\snippets-lab\idea\images\image-20221003192634754.png)

   - 检查tomcat要部署的artifacts与刚才设置的是否一样，路径填写是否正确（注意tomcat中application context是否进行过修改）

2. 如果是对静态资源的访问，检查web.xml的url-pattern中是否填写的/或者/*，如果是则需要配置对静态资源放行，可以用如下方式中的任一种

   * 第一种<mvc:resource location="/XX/" mapping="/XX/**" />
   * 第二种<mvc:default-servlet-handler />

3. 如果是通过requestMapping的方式，检查是否配置了<mvc:annotation-driven /> （默认RequestMappingHandler是没有加入spring容器的)

## Spring多模块项目提示没有为某个项目指定jdk

提示内容：java: JDK isn't specified for module 

解决方法1：project structure->moudule->dependencies选择jdk（不要选择projectSDK)![](D:\dev\idea\snippets-lab\idea\images\image-20221010191800393.png)

解决方法2：重启项目，刷新maven（如果不行，则把.idea文件夹删掉，再重启项目，刷新maven)

## idea的build、build artifact以及maven的compile有什么区别？

build会编译指定的module或整个project的源文件和资源文件，build artifact会将project-structure中配置的包含的资源都编译到artifact中，因此依赖到的其他模块也会有一起bian'yi

## idea关闭更新提示

setting->Appearance&Behavior->System Setting->Updates把所有的勾都去掉

## windows杀死指定端口进程命令

1. 输入命令netstat -ano | findstr  port			其中只需要将最后的port修改为端口号
2. 输入命令taskkill /f /pid pid                             其中最后的pid是通过上面命令查出来的进程号

## download pre-built shared indexes是什么意思？

用来减少索引时间，可在setting->tools->shared indexes中设置。如果索引异常可以通过file->invalidate caches来清理缓存与索引，选择clear downloaded shared indexes
