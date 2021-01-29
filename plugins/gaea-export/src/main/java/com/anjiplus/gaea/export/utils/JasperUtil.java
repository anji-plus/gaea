package com.anjiplus.gaea.export.utils;

import com.anjiplus.gaea.export.config.ExportTemplateContext;
import com.anjiplus.gaea.export.vo.ExportOperation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by raodeming on 2020/11/26.
 */
public class JasperUtil {

    private static Logger logger = LoggerFactory.getLogger(JasperUtil.class);

    public static JasperPrint jasper(ExportOperation exportOperation) throws Exception {
        String reportId = exportOperation.getReportTemplateId();
        //获取报表模板
        logger.info("获取报表模板reportId=" + reportId);
        JasperReport jasperReport = ExportTemplateContext.getInstance().getJasperTemplate(reportId);
        if (jasperReport == null) {
            throw new RuntimeException("报表编号[" + reportId + "]对应的模板不存在");
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(exportOperation.getList(), false);
        return JasperFillManager.fillReport(jasperReport, exportOperation.getParameters(), dataSource);
    }

    /**
     * 模板模式，导出pdf文件
     * @param exportOperation
     * @throws Exception
     */
    public static void jasperPDFByFilePath(ExportOperation exportOperation) throws Exception {
        String filePath = exportOperation.getFilePath();
        //导出pdf
        JasperExportManager.exportReportToPdfFile(jasper(exportOperation), filePath);
    }

    /**
     * 模板模式，输出文件流
     * @param exportOperation
     * @throws Exception
     */
    public static byte[] jasperPDFOutByte(ExportOperation exportOperation) throws Exception {
        //导出pdf文件流
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasper(exportOperation),outputStream);
        byte[] resultByte=outputStream.toByteArray();
        outputStream.close();
        return resultByte;
    }

    /**
     * 模板模式，导出excel文件
     * @param exportOperation
     * @throws Exception
     */
    public static void jasperExcelByFilePath(ExportOperation exportOperation) throws Exception {
        String filePath = exportOperation.getFilePath();
        // 创建Jasper Excel导出类
        JRXlsxExporter exporter = new JRXlsxExporter();
        // 设置导出配置项
        SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
        conf.setWhitePageBackground(false);
        conf.setDetectCellType(true);
        exporter.setConfiguration(conf);
        // 设置输入项
        ExporterInput exporterInput = new SimpleExporterInput(jasper(exportOperation));
        exporter.setExporterInput(exporterInput);
        // 设置输出项
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(filePath);
        exporter.setExporterOutput(exporterOutput);
        //导出报表
        exporter.exportReport();
        exporterOutput.close();
    }

    /**
     * 模板模式，导出excel文件流
     * @param exportOperation
     * @throws Exception
     */
    public static byte[] jasperExcelOutByte(ExportOperation exportOperation)throws Exception{
        // 创建Jasper Excel导出类
        JRXlsxExporter exporter = new JRXlsxExporter();
        // 设置导出配置项
        SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
        conf.setWhitePageBackground(false);
        conf.setDetectCellType(true);
        exporter.setConfiguration(conf);
        // 设置输入项
        ExporterInput exporterInput = new SimpleExporterInput(jasper(exportOperation));
        exporter.setExporterInput(exporterInput);
        // 设置输出项
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(outputStream);
        exporter.setExporterOutput(exporterOutput);
        //导出报表
        exporter.exportReport();
        byte[] resultByte=outputStream.toByteArray();
        outputStream.close();
        exporterOutput.close();
        return  resultByte;
    }
}
