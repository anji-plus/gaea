package com.anji.mirror.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 * 将t_dict表中的字典，生成枚举类
 * @author anji gaea teams
 * @since 2020-08-20
 */
public class DictToEnumGenerator {
    private static Logger logger = LoggerFactory.getLogger(DictToEnumGenerator.class);

    //生成文件所在项目路径
    public static String modelDir = "D:\\anji-code\\gitlab\\haitongCloud\\auth-service\\";
    public static String packageName = "com.anji.mirror.auth.enums";
    public static String author = "anji mirror teams";

    public static void main(String[] args) {
        //不为空时，表示生成指定的字典枚举
        String onlyDictName = "FILE_STATUS";
        generate(onlyDictName);
    }


    public static void generate(String onlyDictName){
        try {
            String createFileDir = modelDir + "src/main/java/" + packageName.replace(".","/") + "/";

            DataSourceConfig dataSourceConfig = DataSourceBuild.getInstance().getSource();
            Connection connection = dataSourceConfig.getConn();

            String sql="SELECT dict_name,item_name,item_desc,item_value,dict_desc from t_dict";
            if(StringUtils.isNotBlank(onlyDictName)){
                sql = sql + " where dict_name = '"+onlyDictName+"'";
            }
            sql += " ORDER BY dict_name asc,sort asc";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();

            List<String[]> list=new ArrayList<String[]>();

            while(resultSet.next()){
                String[] rows=new String[5];
                rows[0]=resultSet.getString(1);//dict_name
                rows[1]=resultSet.getString(2);//code
                rows[2]=resultSet.getString(3);//code_desc
                rows[3]=resultSet.getString(4);//code_value
                rows[4]=resultSet.getString(5);//dict_desc
                list.add(rows);
            }
            resultSet.close();
            preparedStatement.close();


            String lastDicname="";
            StringBuffer constantContent=new StringBuffer();

            for (int i=0;i<list.size();i++) {
                String[] row1=list.get(i);
                String dictName1=row1[0];
                String code1=row1[1];
                String codeDesc1=row1[2];
                String codeValue1=row1[3];
                String dictDesc1=row1[4];

                boolean codeValueIsInt = false;
                try{
                    Integer.parseInt(codeValue1);
                    codeValueIsInt = true;
                }catch(Exception e){
                    codeValueIsInt = false;
                }

                if(i>0 && (!lastDicname.equals(dictName1) || i ==list.size()-1)){
                    //写上一个
                    if(i ==list.size()-1){
                        if(codeValueIsInt){
                            constantContent.append("    "+ code1.toUpperCase() +"("+ codeValue1+ ",\"" + codeDesc1+ "\"),\r\n");
                        }else{
                            constantContent.append("    "+ code1.toUpperCase() +"(\""+ codeValue1+ "\",\"" + codeDesc1+ "\"),\r\n");
                        }
                    }

                    boolean lastCodeValueIsInt = false;
                    String dataType = "String";
                    try{
                        Integer.parseInt(list.get(i-1)[3]);
                        lastCodeValueIsInt = true;
                        dataType = "int";
                    }catch(Exception e){
                        lastCodeValueIsInt = false;
                        dataType = "String";
                    }

                    String className = NamingStrategy.underlineToCamel(lastDicname);
                    className = NamingStrategy.capitalFirst(className);
                    String filePath = createFileDir + className+"Enum.java";
                    logger.info("正在生成枚举类: {}",filePath);
                    constantContent.insert(0,"public enum "+className+"Enum {\r\n");
                    constantContent.insert(0,"package "+packageName+";\r\n");

                    constantContent.append("    ;");
                    constantContent.append("\r\n\r\n");
                    constantContent.append("    private "+dataType+" codeValue;");
                    constantContent.append("\r\n");
                    constantContent.append("    private String codeDesc;");
                    constantContent.append("\r\n\r\n");

                    constantContent.append("    private "+className+"Enum("+dataType+"  codeValue, String codeDesc) {\r\n");
                    constantContent.append("        this.codeValue = codeValue;\r\n");
                    constantContent.append("        this.codeDesc = codeDesc;\r\n");
                    constantContent.append("    }\r\n\r\n");

                    constantContent.append("    public "+dataType+"   getCodeValue(){ return this.codeValue;}\r\n\r\n");

                    constantContent.append("    public String getCodeDesc(){ return this.codeDesc;}\r\n\r\n");


                    constantContent.append("    //根据codeValue获取枚举\r\n");
                    constantContent.append("    public static "+className+"Enum parseFromCodeValue("+dataType+" codeValue){\r\n");
                    constantContent.append("        for ("+className+"Enum e : "+className+"Enum.values()){\r\n");
                    if(lastCodeValueIsInt){
                        constantContent.append("            if(e.codeValue == codeValue){ return e;}\r\n");
                    }else{
                        constantContent.append("            if(e.codeValue.equals(codeValue)){ return e;}\r\n");
                    }
                    constantContent.append("        }\r\n");
                    constantContent.append("        return null;\r\n");
                    constantContent.append("    }\r\n\r\n");

                    constantContent.append("    //根据codeValue获取描述\r\n");
                    constantContent.append("    public static String getCodeDescByCodeBalue("+dataType+" codeValue){\r\n");
                    constantContent.append("        "+className+"Enum enumItem = parseFromCodeValue(codeValue);\r\n");
                    constantContent.append("        return enumItem == null ? \"\" : enumItem.getCodeDesc();\r\n");
                    constantContent.append("    }\r\n\r\n");


                    constantContent.append("    //验证codeValue是否有效\r\n");
                    constantContent.append("    public static boolean validateCodeValue("+dataType+" codeValue){ return parseFromCodeValue(codeValue)!=null;}\r\n\r\n");

                    constantContent.append("    //列出所有值字符串\r\n");
                    constantContent.append("    public static String getString(){\r\n");
                    constantContent.append("        StringBuffer buffer = new StringBuffer();\r\n");
                    constantContent.append("        for ("+className+"Enum e : "+className+"Enum.values()){\r\n");
                    constantContent.append("            buffer.append(e.codeValue).append(\"--\").append(e.getCodeDesc()).append(\", \");\r\n");
                    constantContent.append("        }\r\n");
                    constantContent.append("        buffer.deleteCharAt(buffer.lastIndexOf(\",\"));\r\n");
                    constantContent.append("        return buffer.toString().trim();\r\n");
                    constantContent.append("    }\r\n\r\n");

                    constantContent.append("\r\n");
                    constantContent.append("}");

                    OutputStreamWriter constantOut = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
                    constantOut.write(constantContent.toString());
                    constantOut.flush();
                    constantOut.close();

                    //生成新文件
                    constantContent=new StringBuffer();
                }

                if(codeValueIsInt){
                    constantContent.append("    "+ code1.toUpperCase() +"("+ codeValue1+ ",\"" + codeDesc1+ "\"),\r\n");
                }else{
                    constantContent.append("    "+ code1.toUpperCase() +"(\""+ codeValue1+ "\",\"" + codeDesc1+ "\"),\r\n");
                }

                lastDicname = dictName1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
