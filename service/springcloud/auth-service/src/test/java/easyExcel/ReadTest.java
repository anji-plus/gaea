package easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.anji.mirror.auth.AuthApplication;
import com.anji.mirror.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;


@Slf4j
//@SpringBootTest(classes = AuthApplication.class)
//@RunWith(SpringRunner.class)
public class ReadTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTest.class);

//    @Autowired
//    private UserService userService;

    @Test
    public void indexOrNameRead() {
        String fileName = ReadTest.getPath() + File.separator + "demo" + File.separator +"aaa.xlsx";
        // 这里默认读取第一个sheet
        try {
            EasyExcel.read(fileName, ChangzhanData.class, new IndexOrNameDataListener(3)).sheet().headRowNumber(2).doRead();
        } catch (Exception exception) {
            if (exception.getCause() instanceof ExcelDataConvertException) {
                ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception.getCause();
                LOGGER.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                        excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
            }
        }

    }


    public static String getPath() {
        return ReadTest.class.getResource("/").getPath();
    }
}
