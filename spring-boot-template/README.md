项目模板生成命令：
```
cd demo-proj
mvn clean
mvn archetype:create-from-project
cd  target\generated-sources\archetype
pom.xml根节点追加如下内容，地址做相应修改
  <distributionManagement>
      <repository>
          <id>my-nexus-releases</id>
          <url>http://172.95.65.37:8071/nexus/content/repositories/releases/</url>
      </repository>
      <snapshotRepository>
          <id>my-nexus-snapshots</id>
          <url>http://172.95.65.37:8071/nexus/content/repositories/snapshots/</url>
      </snapshotRepository>
  </distributionManagement>

mvn deploy
```
=========================================================
客户端修改如下文件：.m2/archetype-catalog.xml，追加repository，地址做相应修改。
```
  <archetypes>
    <archetype>
      <groupId>com.cdj.propertyweb</groupId>
      <artifactId>property-web-archetype</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <description>property-web-archetype</description>
      <repository>
		http://172.95.65.37:8071/nexus/content/repositories/snapshots
	  </repository>
    </archetype>
  </archetypes>
```
  
执行如下命令，生成新项目
```
mvn archetype:generate -DarchetypeCatalog=local
```

