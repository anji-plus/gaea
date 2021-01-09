package itextpdf;


import com.anji.mirror.common.utils.export.Excel2Pdf;
import com.anji.mirror.common.utils.export.ExcelObject;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raodeming on 2020/11/24.
 */
public class pdfTest {

    @Test
    public void test1() throws Exception {
        String pathOfXls = "D:\\1111111.xls";
        String pathOfPdf = "D:\\awa.pdf";

        FileInputStream fis = new FileInputStream(pathOfXls);
        List<ExcelObject> objects = new ArrayList<ExcelObject>();
        objects.add(new ExcelObject("导航1",fis));
        FileOutputStream fos = new FileOutputStream(pathOfPdf);
        Excel2Pdf pdf = new Excel2Pdf(objects, fos);
        pdf.convert();
    }
}
