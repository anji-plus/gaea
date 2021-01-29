package com.anjiplus.gaea.export.utils;

import com.alibaba.excel.EasyExcel;
import com.anjiplus.gaea.export.vo.ExportOperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 功能描述：
 * EasyExcel 相关操作工具类
 *
 * @Author: peiyanni
 * @Date: 2021/1/28 10:56
 */
public class EasyExcelUtil {

    /**
     * 简单模式下
     * 根据filePath导出文件
     *
     * @param exportOperation
     */
    public static void excelExportByFilePath(ExportOperation exportOperation, Class clazz) {
        File file = new File(exportOperation.getFilePath());
        EasyExcel.write(file, clazz).sheet(exportOperation.getFileTitle()).doWrite(exportOperation.getList());
    }

    /**
     * 简单模式下导出文件流格式
     *
     * @return
     */
    public static byte[] excelExportByte(ExportOperation exportOperation, Class clazz) throws Exception {
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream, clazz).sheet(exportOperation.getFileTitle()).doWrite(exportOperation.getList());
            byte[] resultByte = outputStream.toByteArray();
            return resultByte;
        } catch (Exception e) {

        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
        return null;
    }
}
