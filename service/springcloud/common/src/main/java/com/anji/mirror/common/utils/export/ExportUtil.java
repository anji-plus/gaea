package com.anji.mirror.common.utils.export;

import com.alibaba.excel.EasyExcel;
import com.anji.mirror.common.enums.ExportTypeEnum;
import com.anji.mirror.common.enums.FileStatusEnum;
import com.anji.mirror.common.model.ExportVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 导出工具
 *
 * @author anji gaea teams
 */
public class ExportUtil {

    private static Logger logger = LoggerFactory.getLogger(ExportUtil.class);


    public static void export(ExportVO exportVO, Class clazz) {
        exportVO.setFileCreateTime(LocalDateTime.now());
        // 生成文件唯一性标识
        String fileId = UUID.randomUUID().toString();

        String dictPath = exportVO.getFilePath();
        exportVO.setFileStatus(FileStatusEnum.CREATING.getCodeValue());
        exportVO.setFileId(fileId);
        logger.info("------>正在导出<-----:fileId：{}", exportVO.getFileId());

        try {
            if (exportVO.getExportType().equals(ExportTypeEnum.SIMPLE_EXCEL.getCodeValue())) {
                //easyexcel
                exportVO.setFilePath(dictPath + fileId + ".xlsx");
                File file = new File(exportVO.getFilePath());
                EasyExcel.write(file, clazz).sheet(exportVO.getFileTitle()).doWrite(exportVO.getList());

            } else if (exportVO.getExportType().equals(ExportTypeEnum.SIMPLE_PDF.getCodeValue())) {
                //itext
                exportVO.setFilePath(dictPath + fileId  + ".pdf");
                

            } else if (exportVO.getExportType().equals(ExportTypeEnum.JASPER_TEMPLATE_EXCEL.getCodeValue())) {
                //jasper excel
                exportVO.setFilePath(dictPath + fileId  + ".xlsx");
                JasperUtil.jasperExcel(exportVO);
            } else if (exportVO.getExportType().equals(ExportTypeEnum.JASPER_TEMPLATE_PDF.getCodeValue())) {
                //jasper pdf
                exportVO.setFilePath(dictPath + fileId  + ".pdf");
                JasperUtil.jasperPDF(exportVO);
            }
            exportVO.setFileStatus(FileStatusEnum.SUCCESS.getCodeValue());
            exportVO.setFileFinishTime(LocalDateTime.now());
            logger.info("------>导出成功<-----:路径：{}", exportVO.getFilePath());
        } catch (Exception e) {
            logger.error("----->导出失败<-----",e);
            exportVO.setRemark(e.getMessage());
            exportVO.setFileStatus(FileStatusEnum.FAILED.getCodeValue());
            exportVO.setFileFinishTime(LocalDateTime.now());
        }

    }
}
