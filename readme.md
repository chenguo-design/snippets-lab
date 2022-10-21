## 使用须知

````
git中图片路径如：https://github.com/chenguo-design/snippets-lab/blob/master/idea/images/image-20221003113840864.png
本人本地图片前缀:D:\dev\idea
git远程图片前缀:https://github.com/chenguo-design/

D:\dev\idea\snippets-lab\idea\images\image-20221003113840864.png

ctrl+shift+R开启正则表达式：
source:\(D:\\dev\\idea\\snippets-lab\\idea\\images\\(.*?)\)
dest:\(https://github.com/chenguo-design/snippets-lab/blob/master/idea/images/$1\)

反过来则是：
source:\(https://github.com/chenguo-design/snippets-lab/blob/master/idea/images/(.*?)\)
dest:\(D:\\dev\\idea\\snippets-lab\\idea\\images\\$1\)
````

## github中无法查看图片，解决方式

	1.打开网站 https://ping.eu/nslookup
	2.输入raw.githubusercontent.com
	3.找到出来的形如下面的数据
	 	raw.githubusercontent.com has address 185.199.111.133
		raw.githubusercontent.com has address 185.199.110.133
		raw.githubusercontent.com has address 185.199.108.133
		raw.githubusercontent.com has address 185.199.109.133
	4.在本地host中追加（c:/windows/system32/drivers/etc
		185.199.111.133 raw.githubusercontent.com
		185.199.110.133 raw.githubusercontent.com
		185.199.108.133 raw.githubusercontent.com
		185.199.109.133 raw.githubusercontent.com 

## github访问慢

````
1.打开网站http://tool.chinaz.com/dns
2.输入github.com,将出来的ip放入host文件
````
remote 改一下
