package com.anji.mirror.common.config;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExportTemplateContext {

    private static Logger logger= LoggerFactory.getLogger(ExportTemplateContext.class);

    private static Map<String, JasperReport> jasperReportCache = new HashMap<String, JasperReport>();

    private static ExportTemplateContext instance;

    private ExportTemplateContext(){
    }

    public static ExportTemplateContext getInstance() {
        if(instance == null||jasperReportCache==null || jasperReportCache.isEmpty()){
            synchronized (ExportTemplateContext.class){
                if(instance == null){
                    instance = new ExportTemplateContext();
                    logger.info("instance为空，重新初始化。");
                }
                if(jasperReportCache==null || jasperReportCache.isEmpty()){
                    logger.info("jasperReportCache为空，从文件中编译模板。");
                }
                instance.init();
            }
        }
        return instance;
    }

    public void init() {
        synchronized(jasperReportCache){
            //获取目录下所有的IReport模板文件
            /*String dirPath = "META-INF/ireport";
            List<String> templateFileList = ResourcesFileReader.getResourceFile(dirPath, ".jrxml");

            //编译报表模板文件加到缓存里
            for (String templateFile : templateFileList) {
                logger.info("待编译的模板"+templateFile);
                String fn = new File(templateFile).getName();
                String reportId = fn.substring(0, fn.indexOf("."));
                logger.info("开始编译"+reportId+"模板");

                InputStream jrxmlTempate=null;
                try {
                    jrxmlTempate = ExportTemplateContext.class.getResourceAsStream(templateFile);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlTempate);
                    jasperReportCache.put(reportId, jasperReport);
                    logger.info("编译模板成功"+reportId);
                } catch (Exception e) {
                    logger.error("编译模板失败!", e);
                } finally {
                    try {
                        if(jrxmlTempate != null)
                            jrxmlTempate.close();
                    } catch (Exception e) {
                        logger.error("关闭流时异常", e);
                    }
                }
            }*/
            try{
                jasperReportCache.clear();
                ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
                Resource[] resources=resolver.getResources("classpath*:jaspers/*.jrxml");
                for (Resource resource : resources) {
                    if(resource.exists()){
                        String jrxmlFileName=resource.getFilename();
                        logger.info("模板文件:"+jrxmlFileName);
                        String reportId = jrxmlFileName.substring(0, jrxmlFileName.indexOf("."));
                        InputStream jrxmlTempate=resource.getInputStream();
                        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlTempate);
                        jasperReportCache.put(reportId, jasperReport);
                        logger.info("编译模板成功"+reportId);
                    }
                }
                logger.info("所有模板已加载完毕,共"+jasperReportCache.size()+"个");

            }catch(Exception e){
                logger.error("编译模板失败!", e);
            }
        }
    }

    public JasperReport getJasperTemplate(String reportId){
        if(jasperReportCache==null || jasperReportCache.isEmpty()){
            logger.info("模板缓存为空，重新加载");
            getInstance();
        }else{
            logger.debug("模板缓存正常，从模板缓存中加载以提高速度");
        }
        return jasperReportCache.get(reportId);
    }

    public Map<String, JasperReport> getJasperReportCache() {
        return jasperReportCache;
    }
}
