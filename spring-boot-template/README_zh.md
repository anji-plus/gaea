项目模板生成命令：
```
cd demo-proj
mvn clean
mvn archetype:create-from-project
cd  target\generated-sources\archetype
pom.xml根节点追加如下内容，地址做相应修改
  <distributionManagement>
      <repository>
          <id>nexus-releases</id>
          <url>http://10.108.10.53:8081/repository/maven-releases/</url>
      </repository>
      <snapshotRepository>
          <id>nexus-snapshots</id>
          <url>http://10.108.10.53:8081/repository/maven-snapshots/</url>
      </snapshotRepository>
  </distributionManagement>

mvn install
mvn deploy
```
=========================================================
客户端修改如下文件：.m2/repository/archetype-catalog.xml，内容如下。
```
  <archetypes>
    <archetype>
      <groupId>com.anjiplus.template</groupId>
      <artifactId>anjiplus-template-archetype</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <description>web-archetype</description>
      <repository>
		http://10.108.10.53:8081/repository/maven-snapshots/
	  </repository>
    </archetype>
  </archetypes>
```
  
执行如下命令，生成新项目
```
mvn archetype:generate -DarchetypeCatalog=local -DgroupId=com.anjiplus -DartifactId=demo-proj -Dpackage=com.anjiplus.demo -Dbasedir=/java/workspace -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=com.anji-plus -DarchetypeArtifactId=spring-boot-template-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DinteractiveMode=false
```

https://blog.csdn.net/aosica321/article/details/51079804
http://maven.apache.org/archetype/maven-archetype-plugin/advanced-usage.html
https://blog.csdn.net/qq_35448165/article/details/103721481

注意：生成的archetype包含模板项目中所有的文档，避免冗余，可将多余文件删除，并对其项目结构做一些整理！！！
a、修改archetype目录下pom.xml，将archetype的名称修改成你喜欢的；
b、可将文件夹改成_rootArtifactId_，这样生成项目结构时，这个目录名称就会变成新的项目名称了；
c. pom.xml 依赖包 参数修改

mvn archetype:generate -DarchetypeCatalog=local -DgroupId=com.anjiplus -DartifactId=anjiplus-lowcode -Dpackage=com.anjiplus.lowcode -Dbasedir=c:/java/workspace -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=com.anjiplus.template -DarchetypeArtifactId=anjiplus-template-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DinteractiveMode=false