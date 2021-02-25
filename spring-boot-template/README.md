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

mvn deploy
```
=========================================================
客户端修改如下文件：.m2/archetype-catalog.xml，追加repository，地址做相应修改。
```
  <archetypes>
    <archetype>
      <groupId>com.anji-plus</groupId>
      <artifactId>spring-boot-template-archetype</artifactId>
      <version>1.0-SNAPSHOT</version>
      <description>web-archetype</description>
      <repository>
		http://10.108.10.53:8081/repository/maven-snapshots/
	  </repository>
    </archetype>
  </archetypes>
```
  
执行如下命令，生成新项目
```
mvn archetype:generate -DarchetypeCatalog=local -DgroupId=com.anjiplus -DartifactId=demo-proj -Dpackage=com.anjiplus.demo -Dversion=1.0.0.SNAPSHOT -DarchetypeGroupId=com.anji-plus -DarchetypeArtifactId=spring-boot-template-archetype -DarchetypeVersion=1.0-SNAPSHOT
```

