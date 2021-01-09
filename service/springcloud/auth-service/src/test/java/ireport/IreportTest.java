package ireport;

import com.anji.mirror.auth.AuthApplication;
import com.anji.mirror.auth.config.ExportTemplateContext;
import com.anji.mirror.common.model.ExportVO;
import com.anji.mirror.common.enums.ExportTypeEnum;
import com.anji.mirror.auth.service.ExportService;
import com.anji.mirror.common.utils.export.ExportUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raodeming on 2020/11/24.
 */
@Slf4j
//@SpringBootTest(classes = AuthApplication.class)
//@RunWith(SpringRunner.class)
public class IreportTest {

    @Test
    public void test1() {

        ExportVO exportVO = new ExportVO();
        exportVO.setReportTemplateId("BCB0101");
        exportVO.setFilePath("D:\\aaaa.xlsx");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cccName", "dandan");
        parameters.put("bankZhName", "啦啦");
        parameters.put("companyName", "连接拒绝");
        parameters.put("creatDate", "2020-11-11 11:11:11");
        parameters.put("containCcc", "ccc");


        List list = new ArrayList();
        Map map = new HashMap();
        map.put("totalPayment", 10);
        map.put("lastBal", 10);
        map.put("consumptionAmt", 10);
        map.put("adjustmentAmt", 10);
        map.put("interest", 10);
        map.put("repaymentAmt", 10);
        Map map2 = new HashMap();
        map2.put("totalPayment", 10);
        map2.put("lastBal", 10);
        map2.put("consumptionAmt", 10);
        map2.put("adjustmentAmt", 10);
        map2.put("interest", 10);
        map2.put("repaymentAmt", 10);
        list.add(map);
        list.add(map2);
        exportVO.setParameters(parameters);
        exportVO.setList(list);

        try {
            String reportId = exportVO.getReportTemplateId();
            String filePath = exportVO.getFilePath();
            //获取报表模板
            log.info("获取报表模板reportId=" + reportId);
            JasperReport jasperReport = ExportTemplateContext.getInstance().getJasperTemplate(reportId);
            if (jasperReport == null) {
                throw new RuntimeException("报表编号[" + reportId + "]对应的模板不存在");
            }
            JRDataSource dataSource = new JRBeanCollectionDataSource(exportVO.getList(), false);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, exportVO.getParameters(), dataSource);
            //导出pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/aaaaa.pdf");
            //导出excel
//            JRXlsxExporter exporter = new JRXlsxExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
//            SimpleXlsxReportConfiguration cfg = new SimpleXlsxReportConfiguration();
//            cfg.setOnePagePerSheet(true);
//            cfg.setSheetNames(new String[]{"sheet1"});
//            exporter.setConfiguration(cfg);
//            exporter.exportReport();

        } catch (Exception e) {
            log.error("export report file by jasperreport error:" + e);
        }


    }


    @Test
    public void test2() {
        ExportVO exportVO = new ExportVO();
        exportVO.setReportTemplateId("helloword");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "xiao ming 1你好");

        List<HashMap> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("Field_1", "Field1-" + i);
            item.put("Field_2", "Field2-" + i);
            list.add(item);
        }
        exportVO.setParameters(parameters);
        exportVO.setList(list);

        try {
            String reportId = exportVO.getReportTemplateId();
            String filePath = exportVO.getFilePath();
            //获取报表模板
            log.info("获取报表模板reportId=" + reportId);
            JasperReport jasperReport = ExportTemplateContext.getInstance().getJasperTemplate(reportId);
            if (jasperReport == null) {
                throw new RuntimeException("报表编号[" + reportId + "]对应的模板不存在");
            }
            JRDataSource dataSource = new JRBeanCollectionDataSource(exportVO.getList(), false);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, exportVO.getParameters(), dataSource);
            //导出pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/aaaaa.pdf");

            // 创建Jasper Excel导出类
            JRXlsxExporter exporter = new JRXlsxExporter();
            // 设置导出配置项
            SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
            conf.setWhitePageBackground(false);
            conf.setDetectCellType(true);
            exporter.setConfiguration(conf);
            // 设置输入项
            ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
            exporter.setExporterInput(exporterInput);
            // 设置输出项
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("D:\\aaaa.xlsx");
            exporter.setExporterOutput(exporterOutput);
            //导出报表
            exporter.exportReport();
            exporterOutput.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void test3() {
        ExportVO exportVO = new ExportVO();
        exportVO.setReportTemplateId("chart");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "xiao ming 1你好");

        List<HashMap> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("username1", "字段-" + i);
            item.put("username2", "啦啦-" + i);
            list.add(item);
        }

        List<HashMap> userList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("id", i);
            item.put("userName", "用户user-" + i);
            item.put("age", i);
            item.put("deptId", "dept-" + i);
            item.put("username1", "dept-" + i);
            item.put("username2", "dept-" + i);
            userList.add(item);
        }
        parameters.put("userList", userList);


        exportVO.setParameters(parameters);
        exportVO.setList(list);

        try {
            String reportId = exportVO.getReportTemplateId();
            String filePath = exportVO.getFilePath();
            //获取报表模板
            log.info("获取报表模板reportId=" + reportId);
            JasperReport jasperReport = ExportTemplateContext.getInstance().getJasperTemplate(reportId);
            if (jasperReport == null) {
                throw new RuntimeException("报表编号[" + reportId + "]对应的模板不存在");
            }
            JRDataSource dataSource = new JRBeanCollectionDataSource(exportVO.getList(), false);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, exportVO.getParameters(), dataSource);
            //导出pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/aaaaa.pdf");

            // 创建Jasper Excel导出类
            JRXlsxExporter exporter = new JRXlsxExporter();
            // 设置导出配置项
            SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
            conf.setWhitePageBackground(false);
            conf.setDetectCellType(true);
            exporter.setConfiguration(conf);
            // 设置输入项
            ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
            exporter.setExporterInput(exporterInput);
            // 设置输出项
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("D:\\aaaa.xlsx");
            exporter.setExporterOutput(exporterOutput);
            //导出报表
            exporter.exportReport();
            exporterOutput.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test4() {
        ExportVO exportVO = new ExportVO();
        exportVO.setReportTemplateId("testChartReport");
        Map<String, Object> parameters = new HashMap<>(16);
        exportVO.setParameters(parameters);
        exportVO.setList(this.getUserCountList());

        try {
            String reportId = exportVO.getReportTemplateId();
            String filePath = exportVO.getFilePath();
            //获取报表模板
            log.info("获取报表模板reportId=" + reportId);
            JasperReport jasperReport = ExportTemplateContext.getInstance().getJasperTemplate(reportId);
            if (jasperReport == null) {
                throw new RuntimeException("报表编号[" + reportId + "]对应的模板不存在");
            }
            JRDataSource dataSource = new JRBeanCollectionDataSource(exportVO.getList(), false);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, exportVO.getParameters(), dataSource);
            //导出pdf
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/aaaaa.pdf");

            // 创建Jasper Excel导出类
            JRXlsxExporter exporter = new JRXlsxExporter();
            // 设置导出配置项
            SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
            conf.setWhitePageBackground(false);
            conf.setDetectCellType(true);
            exporter.setConfiguration(conf);
            // 设置输入项
            ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
            exporter.setExporterInput(exporterInput);
            // 设置输出项
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("D:\\aaaa.xlsx");
            exporter.setExporterOutput(exporterOutput);
            //导出报表
            exporter.exportReport();
            exporterOutput.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private ExportService exportService;

    @Test
    public void test5(){
        ExportVO exportVO = new ExportVO();
        exportVO.setReportTemplateId("testChartReport");
        Map<String, Object> parameters = new HashMap<>(16);
        exportVO.setParameters(parameters);
        exportVO.setList(this.getUserCountList());
        exportVO.setFilePath("D:\\test.pdf");
        exportVO.setFileTitle("饼图报表");
        exportVO.setCreaterUserid(1L);
        exportVO.setCreaterUsername("admin");
        exportVO.setResultStartTime(LocalDateTime.now());
        exportVO.setResultEndTime(LocalDateTime.now());
        exportVO.setExportType(ExportTypeEnum.JASPER_TEMPLATE_PDF.getCodeValue());

        exportService.export(exportVO);

    }








    /**
     * 获取公司名称和员工数量的list列表
     */
    private List<Map> getUserCountList() {

        List<Map> userCountList = new ArrayList<>(10);

        Map map1 = new HashMap();
        map1.put("company", "众安保险");
        map1.put("count", 1000L);
        Map map2 = new HashMap();
        map2.put("company", "万事达");
        map2.put("count", 300L);
        userCountList.add(map1);
        userCountList.add(map2);
        return userCountList;
    }



}