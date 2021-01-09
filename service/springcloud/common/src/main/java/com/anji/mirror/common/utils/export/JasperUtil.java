package com.anji.mirror.common.utils.export;

import com.anji.mirror.common.config.ExportTemplateContext;
import com.anji.mirror.common.model.ExportVO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raodeming on 2020/11/26.
 */
public class JasperUtil {

    private static Logger logger = LoggerFactory.getLogger(JasperUtil.class);

    public static JasperPrint jasper(ExportVO exportVO) throws Exception {
        String reportId = exportVO.getReportTemplateId();
        //获取报表模板
        logger.info("获取报表模板reportId=" + reportId);
        JasperReport jasperReport = ExportTemplateContext.getInstance().getJasperTemplate(reportId);
        if (jasperReport == null) {
            throw new RuntimeException("报表编号[" + reportId + "]对应的模板不存在");
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(exportVO.getList(), false);
        return JasperFillManager.fillReport(jasperReport, exportVO.getParameters(), dataSource);
    }


    public static void jasperPDF(ExportVO exportVO) throws Exception {
        String filePath = exportVO.getFilePath();
        //导出pdf
        JasperExportManager.exportReportToPdfFile(jasper(exportVO), filePath);
    }

    public static void jasperExcel(ExportVO exportVO) throws Exception {
        String filePath = exportVO.getFilePath();
        // 创建Jasper Excel导出类
        JRXlsxExporter exporter = new JRXlsxExporter();
        // 设置导出配置项
        SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
        conf.setWhitePageBackground(false);
        conf.setDetectCellType(true);
        exporter.setConfiguration(conf);
        // 设置输入项
        ExporterInput exporterInput = new SimpleExporterInput(jasper(exportVO));
        exporter.setExporterInput(exporterInput);
        // 设置输出项
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(filePath);
        exporter.setExporterOutput(exporterOutput);
        //导出报表
        exporter.exportReport();
        exporterOutput.close();
    }
}
