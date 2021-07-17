#### mizhousoft-bmc
业务管理云平台框架

#####  修改版本号流程

1.  只更新父模块的版本号;
	```shell
	mvn versions:set -DnewVersion=1.2.0-SNAPSHOT
	```
2.  更新子模块和父模块一样的版本号;
	```shell
	mvn -N versions:update-child-modules
	```
3.  提交更新
	```shell
	mvn versions:commit
	```

#####  发布版本流程

1. 修改 parent version 版本号；

2. 参考修改版本号流程，修改 version 版本号；

3. 修改 commons.version 版本号； 

4. 修改 bmc.version 版本号； 

5. 修改 mizhousoft.boot.version 版本号； 

6. 要把 bmc-boot 删除从 modules 中删除掉，因为是在最后一个工程上传文件，但是最后一个工程不执行上传；

7. 执行命令
	```shell
	mvn clean deploy -Possrh
	```
