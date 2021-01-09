package easyExcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基础数据类
 *
 * @author anji gaea teams
 **/
@Data
public class ChangzhanData {
    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private String time;

    @ExcelProperty(index = 3)
    @NumberFormat("#.##%")
    private BigDecimal rent;


    @ExcelProperty(index = 4)
    @NumberFormat("#.##%")
    private double guding;


    @ExcelProperty(index = 5)
    @NumberFormat("#.##%")
    private BigDecimal biandong;
}
