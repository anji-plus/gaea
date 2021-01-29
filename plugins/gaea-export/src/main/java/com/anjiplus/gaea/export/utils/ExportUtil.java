package com.anjiplus.gaea.export.utils;

import com.anjiplus.gaea.export.enums.ExportTypeEnum;
import com.anjiplus.gaea.export.enums.FileStatusEnum;
import com.anjiplus.gaea.export.event.GaeaExportApplicationEvent;
import com.anjiplus.gaea.export.vo.ExportOperation;
import com.github.anji.plus.gaea.utils.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 导出工具类
 *
 * @author
 */
public class ExportUtil {

    private static Logger logger = LoggerFactory.getLogger(ExportUtil.class);


    public static String getFileIdUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * jasper方式 导出格式为文件
     *
     * @param exportOperation
     */
    public static void exportByFilePathJasper(ExportOperation exportOperation) {
        exportByFilePath(exportOperation, null);
    }

    /**
     * jasper方式 导出数据流
     *
     * @param exportOperation
     * @return
     */
    public static byte[] exportByteInfoJasper(ExportOperation exportOperation) {
        return exportByteInfo(exportOperation, null);
    }

    /**
     * easyExcel方式 导出格式为文件
     *
     * @param exportOperation 参数
     * @param clazz           excel中list中的类型
     */
    public static void exportByFilePathSimple(ExportOperation exportOperation, Class clazz) {
        exportByFilePath(exportOperation, clazz);
    }

    /**
     * asyExcel方式 导出格式为文件流
     *
     * @param exportOperation 参数
     * @param clazz           excel中list中的类型
     * @return
     */
    public static byte[] exportByteInfoSimple(ExportOperation exportOperation, Class clazz) {
        return exportByteInfo(exportOperation, clazz);
    }

    /**
     * easyExcel方式 导出格式为文件
     *
     * @param exportOperation 参数
     * @param clazz           excel中list中的类型
     */
    private static void exportByFilePath(ExportOperation exportOperation, Class clazz) {
        String fileId = getFileIdUUID();
        String dictPath = exportOperation.getFilePath();
        exportOperation.setFileId(fileId);
        exportOperation.setFileCreateTime(LocalDateTime.now());
        exportOperation.setFileStatus(FileStatusEnum.CREATING.getCodeValue());
        logger.info("------>正在导出文件<-----:fileId：{}", exportOperation.getFileId());
        try {
            if (exportOperation.getExportType().equals(ExportTypeEnum.SIMPLE_EXCEL.getCodeValue())) {
                //easyexcel
                exportOperation.setFilePath(dictPath + fileId + ".xlsx");
                EasyExcelUtil.excelExportByFilePath(exportOperation, clazz);
            } else if (exportOperation.getExportType().equals(ExportTypeEnum.JASPER_TEMPLATE_EXCEL.getCodeValue())) {
                //jasper excel
                exportOperation.setFilePath(dictPath + fileId + ".xlsx");
                JasperUtil.jasperExcelByFilePath(exportOperation);
            } else if (exportOperation.getExportType().equals(ExportTypeEnum.JASPER_TEMPLATE_PDF.getCodeValue())) {
                //jasper pdf
                exportOperation.setFilePath(dictPath + fileId + ".pdf");
                JasperUtil.jasperPDFByFilePath(exportOperation);
            }
            exportOperation.setFileStatus(FileStatusEnum.SUCCESS.getCodeValue());
            exportOperation.setFileFinishTime(LocalDateTime.now());
            logger.info("------>导出文件成功<-----:路径：{}", exportOperation.getFilePath());
        } catch (Exception e) {
            logger.error("----->导出文件失败<-----", e);
            exportOperation.setRemark(e.getMessage());
            exportOperation.setFileStatus(FileStatusEnum.FAILED.getCodeValue());
            exportOperation.setFileFinishTime(LocalDateTime.now());
        } finally {
            //发布事件
            logger.info("------>导出文件发布事件<-----");
            ApplicationContextUtils.publishEvent(new GaeaExportApplicationEvent(ExportUtil.class, exportOperation));
        }
    }

    /**
     * easyExcel方式 导出输出流
     *
     * @param exportOperation 参数
     * @param clazz           excel中list中的类型
     * @return
     */
    private static byte[] exportByteInfo(ExportOperation exportOperation, Class clazz) {
        String fileId = getFileIdUUID();
        exportOperation.setFileId(fileId);
        exportOperation.setFileCreateTime(LocalDateTime.now());
        exportOperation.setFileStatus(FileStatusEnum.CREATING.getCodeValue());
        logger.info("------>正在导出byte<-----:fileId：{}", exportOperation.getFileId());
        byte[] resultByte = null;
        try {
            if (exportOperation.getExportType().equals(ExportTypeEnum.SIMPLE_EXCEL.getCodeValue())) {
                //easyexcel
                resultByte = EasyExcelUtil.excelExportByte(exportOperation, clazz);
            } else if (exportOperation.getExportType().equals(ExportTypeEnum.JASPER_TEMPLATE_EXCEL.getCodeValue())) {
                //jasper excel
                resultByte = JasperUtil.jasperExcelOutByte(exportOperation);
            } else if (exportOperation.getExportType().equals(ExportTypeEnum.JASPER_TEMPLATE_PDF.getCodeValue())) {
                //jasper pdf
                resultByte = JasperUtil.jasperPDFOutByte(exportOperation);
            }
            exportOperation.setFileStatus(FileStatusEnum.SUCCESS.getCodeValue());
            exportOperation.setFileFinishTime(LocalDateTime.now());
            logger.info("------>导出byte成功<-----");
        } catch (Exception e) {
            logger.error("----->导出byte失败<-----", e);
            exportOperation.setRemark(e.getMessage());
            exportOperation.setFileStatus(FileStatusEnum.FAILED.getCodeValue());
            exportOperation.setFileFinishTime(LocalDateTime.now());
        }
        return resultByte;
    }
}
