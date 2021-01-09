package easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.anji.mirror.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板的读取类
 *
 * @author anji gaea teams
 */
public class IndexOrNameDataListener extends AnalysisEventListener<ChangzhanData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexOrNameDataListener.class);

    private UserService userService;
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private  int BATCH_COUNT;
    List<ChangzhanData> list = new ArrayList<ChangzhanData>();

    public IndexOrNameDataListener(int BATCH_COUNT) {
        this.BATCH_COUNT = BATCH_COUNT;
    }

    public IndexOrNameDataListener(UserService userService, int BATCH_COUNT) {
        this.userService = userService;
        this.BATCH_COUNT = BATCH_COUNT;
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException (Exception exception, AnalysisContext context) throws ExcelDataConvertException {
        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            LOGGER.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
            throw excelDataConvertException;
        }
    }

    @Override
    public void invoke(ChangzhanData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        LOGGER.info("存储数据库成功！");
    }
}
