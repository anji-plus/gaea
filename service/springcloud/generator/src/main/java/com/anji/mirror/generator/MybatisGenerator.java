package com.anji.mirror.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.*;

/**
 * mybatis plus代码生成器，自动生成controler po vo service mapper等
 * @author anji gaea teams
 * @since 2020-08-20
 */
public class MybatisGenerator {
    //生成文件所在项目路径
//    public static String modelDir = "D:\\ultrajiaming\\code\\haitong\\cloud\\analysis-service\\";
    public static String modelDir = "/Users/kean_qi/Desktop/lua/";
//    public static String modelDir = "D:/project/cloudht/business-service/";
    public static String packageName = "com.haitong.nla.push";
    public static String author = "anji haitong teams";

    public static void main(String[] args) {
        testGenerator();

        moveMapperXml();
    }


    public static void testGenerator() {

        AutoGenerator autoGenerator = new AutoGenerator();
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(author)
                .setOutputDir(modelDir + "src/main/java")//设置输出路径
                .setOpen(true)
                .setFileOverride(true)//设置文件覆盖
                .setIdType(IdType.AUTO)//设置主键生成策略
                .setServiceName("%sService")//service接口的名称
                .setBaseResultMap(true)//基本结果集合
                .setBaseColumnList(true)//设置基本的列StringPool.DOT_XML
                .setEnableCache(false)
                .setControllerName("%sController")
                .setEntityName("%sPO");


        //配置数据源
        DataSourceConfig dataSourceConfig = DataSourceBuild.getInstance().getSource();


        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//设置全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)//数据库表映射到实体的命名策略
                .setTablePrefix("t_")//设置表名前缀
                .setEntityLombokModel(true)
                .setInclude(new String[] {"t_push_history"})//只生成某个表
//                .setExclude(new String[] {"t_region","t_menu_action","t_role_menu_action","t_user_org","t_user_role"})
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
        ;

        //包名配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageName)
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("domain.po")
        ;

        /**
         * 注入自定义配置
         */
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig abc = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");

                this.setMap(map);
            }
        };

        //自定义文件输出位置（非必须）
        List<FileOutConfig> fileOutList = new ArrayList<>();
        fileOutList.add(new FileOutConfig("template/entityVo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String createFileDir = modelDir + "src/main/java/" + packageName.replace(".","/") + "/domain/vo/" + tableInfo.getEntityName().replace("PO","VO") + ".java";
                return createFileDir;
            }
        });

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController("template/controller.java.vm");
        tc.setEntity("template/entity.java.vm");
        tc.setMapper("template/mapper.java.vm");
        tc.setXml("template/mapper.xml.vm");
        tc.setService("template/service.java.vm");
        tc.setServiceImpl("template/serviceImpl.java.vm");
        autoGenerator.setTemplate(tc);

        abc.setFileOutConfigList(fileOutList);
        autoGenerator.setCfg(abc);

        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        autoGenerator.execute();
    }

    public static void moveMapperXml(){
        try{
            String sourceDir = modelDir + "src/main/java/" + packageName.replace(".","/") + "/mapper/xml/";
            String destDir = modelDir + "src/main/resources/mybatis/mapper/";

            File sourceFiles = new File(sourceDir);
            File destFiles = new File(destDir);
            if(sourceFiles.exists() == false){
                return;
            }
            if(destFiles.exists() == false){
                destFiles.mkdir();
            }
            File[] fileList = sourceFiles.listFiles();
            Arrays.stream(fileList).forEach((file)->{
                String fileName = file.getName();
                File newfile = new File(destDir+fileName);
                if(newfile.exists()){
                    newfile.delete();
                }
                file.renameTo(newfile);
            });

            sourceFiles.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
