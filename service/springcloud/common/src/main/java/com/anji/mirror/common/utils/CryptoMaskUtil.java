package com.anji.mirror.common.utils;

import com.anji.mirror.common.annotation.CryptoMask;
import com.anji.mirror.common.enums.CryptoEnum;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CryptoMaskUtil {

    //缓存已配置加密的对象字段
    public static Map<Class, List<Field>> encryptFieldMap = new ConcurrentHashMap<>();
    //缓存已配置解密的对象字段
    public static Map<Class, List<Field>> decryptFieldMap = new ConcurrentHashMap<>();
    //缓存已配置脱敏的对象字段
    public static Map<Class, List<Field>> maskFieldMap = new ConcurrentHashMap<>();

    /**
     * 对象进行敏感字段加密
     *
     * @param item
     * @return
     */
    public static void itemEncrypt(Object item) throws IllegalAccessException {
        //加密
        List<Field> encryptFieldList = encryptFieldMap.get(item.getClass());
        //加密逻辑
        if (!CollectionUtils.isEmpty(encryptFieldList)) {
            for (Field field : encryptFieldList) {
                // 设置private类型允许访问
                field.setAccessible(Boolean.TRUE);
                //加密
                cryptoField(item, field, true);
                field.setAccessible(Boolean.FALSE);
            }
        } else {
            itemCryptoMaskHandler(item, CryptoEnum.ENCRYPT.getCodeValue());
        }
    }
    /**
     * 对象进行敏感字段解密
     *
     * @param item
     * @return
     */
    public static void itemDecrypt(Object item) throws IllegalAccessException {
        //解密
        List<Field> decryptFieldList = decryptFieldMap.get(item.getClass());
        //加密逻辑
        if (!CollectionUtils.isEmpty(decryptFieldList)) {
            for (Field field : decryptFieldList) {
                // 设置private类型允许访问
                field.setAccessible(Boolean.TRUE);
                //加密
                cryptoField(item, field, false);
                field.setAccessible(Boolean.FALSE);
            }
        } else {
            itemCryptoMaskHandler(item, CryptoEnum.DECRYPT.getCodeValue());
        }
    }

    /**
     * 对象进行敏感字段脱敏处理
     *
     * @param item
     * @return
     */
    public static void itemMask(Object item) throws IllegalAccessException {
        //脱敏
        List<Field> maskFieldList = maskFieldMap.get(item.getClass());
        if (!CollectionUtils.isEmpty(maskFieldList)) {
            for (Field field : maskFieldList) {
                // 设置private类型允许访问
                field.setAccessible(Boolean.TRUE);
                //脱敏
                maskField(item, field, field.getAnnotation(CryptoMask.class));
                field.setAccessible(Boolean.FALSE);
            }
        } else {
            itemCryptoMaskHandler(item, CryptoEnum.MASK.getCodeValue());
        }
    }

    /**
     * 对象进行敏感字段解密脱敏处理
     *
     * @param item
     * @return
     */
    public static void itemDecryptMask(Object item) throws IllegalAccessException {
        //解密
        List<Field> decryptFieldList = decryptFieldMap.get(item.getClass());
        //脱敏
        List<Field> maskFieldList = maskFieldMap.get(item.getClass());
        if (!CollectionUtils.isEmpty(maskFieldList) && !CollectionUtils.isEmpty(decryptFieldList)) {
            for (Field field : decryptFieldList) {
                // 设置private类型允许访问
                field.setAccessible(Boolean.TRUE);
                //解密
                cryptoField(item, field, false);
                field.setAccessible(Boolean.FALSE);
            }

            for (Field field : maskFieldList) {
                // 设置private类型允许访问
                field.setAccessible(Boolean.TRUE);
                //脱敏
                maskField(item, field, field.getAnnotation(CryptoMask.class));
                field.setAccessible(Boolean.FALSE);
            }
        } else {
            itemCryptoMaskHandler(item, CryptoEnum.DECRYPT.getCodeValue());
            itemCryptoMaskHandler(item, CryptoEnum.MASK.getCodeValue());
        }
    }

    /**
     * 反射对对象加密 解密 脱敏
     *
     * @param item
     * @return
     */
    public static void itemCryptoMaskHandler(Object item, String handlerType) throws IllegalAccessException {
        // 捕获类中的所有字段
        Field[] fields = item.getClass().getDeclaredFields();
        // 初始化
        List<Field> list = new ArrayList<>(fields.length);
        // 遍历所有字段
        for (Field field : fields) {
            // 若该字段被SensitiveField注解,则进行加密
            Class<?> fieldType = field.getType();
            //对象为String类型时
            if (fieldType == String.class) {
                if (null != AnnotationUtils.findAnnotation(field, CryptoMask.class)) {
                    CryptoMask crypto = field.getAnnotation(CryptoMask.class);
                    // 设置private类型允许访问
                    field.setAccessible(Boolean.TRUE);
                    if (CryptoEnum.ENCRYPT.getCodeValue().equals(handlerType)) {
                        if (crypto.needEncrypt()) {
                            cryptoField(item, field, true);
                            //缓存字段
                            list.add(field);
                        }
                    }

                    if (CryptoEnum.DECRYPT.getCodeValue().equals(handlerType)) {
                        if (!crypto.needEncrypt()) {
                            cryptoField(item, field, false);
                            //缓存字段
                            list.add(field);
                        }
                    }

                    if (CryptoEnum.MASK.getCodeValue().equals(handlerType)) {
                        if (crypto.needMask()) {
                            maskField(item, field, field.getAnnotation(CryptoMask.class));
                            //缓存字段
                            list.add(field);
                        }
                    }

                    field.setAccessible(Boolean.FALSE);
                }
            }
        }
        if(!CollectionUtils.isEmpty(list)) {
            if (CryptoEnum.ENCRYPT.getCodeValue().equals(handlerType)) {
                encryptFieldMap.put(item.getClass(), list);
            }

            if (CryptoEnum.DECRYPT.getCodeValue().equals(handlerType)) {
                decryptFieldMap.put(item.getClass(), list);
            }

            if (CryptoEnum.MASK.getCodeValue().equals(handlerType)) {
                maskFieldMap.put(item.getClass(), list);
            }
        }
    }


    /**
     * List对象进行敏感字段加密处理
     *
     * @param item
     * @return
     */
    public static void itemListEncrypt(Object item) throws IllegalAccessException {
        if (List.class.isAssignableFrom(item.getClass())) {
            List list = (List)item;
            if(list != null && !list.isEmpty()){
                for (int i=0; i < list.size(); i++) {
                    itemEncrypt(list.get(i));
                }
            }
        }
    }


    /**
     * List对象进行敏感字段解密处理
     *
     * @param item
     * @return
     */
    public static void itemListDecrypt(Object item) throws IllegalAccessException {
        if (List.class.isAssignableFrom(item.getClass())) {
            List list = (List)item;
            if(list != null && !list.isEmpty()){
                for (int i=0; i < list.size(); i++) {
                    itemDecrypt(list.get(i));
                }
            }
        }
    }


    /**
     * List对象进行敏感字段脱敏处理
     *
     * @param item
     * @return
     */
    public static void itemListMask(Object item) throws IllegalAccessException {
        if (List.class.isAssignableFrom(item.getClass())) {
            List list = (List)item;
            if(list != null && !list.isEmpty()){
                for (int i=0; i < list.size(); i++) {
                    itemMask(list.get(i));
                }
            }
        }
    }

    /**
     * List对象进行敏感字段解密并脱敏处理
     *
     * @param item
     * @return
     */
    public static void itemListDecryptMask(Object item) throws IllegalAccessException {
        if (List.class.isAssignableFrom(item.getClass())) {
            List list = (List)item;
            if(list != null && !list.isEmpty()){
                for (int i=0; i < list.size(); i++) {
                    itemDecryptMask(list.get(i));
                }
            }
        }
    }

    private static void cryptoField(Object item, Field field, boolean isEncrypted) throws IllegalAccessException {
        if(null == item){
            return;
        }
        //加密
        if (isEncrypted) {
            field.set(item, AESUtil.aesEncrypt((String) field.get(item)));
        //解密
        } else {
            field.set(item, AESUtil.aesDecrypt((String) field.get(item)));
        }
    }

    /**
     * 进行脱敏
     */
    private static void maskField(Object item, Field field, CryptoMask annotation) throws IllegalAccessException {
        if(null == annotation){
            return;
        }
        field.set(item, StringUtils.mask((String) field.get(item), annotation.start(), annotation.end()));
    }



    public static void main(String[] args) throws IllegalAccessException {
//        HelpPO2 helpPO2 = new HelpPO2();
//        helpPO2.setHelpTitle("11111111");
//        HelpPO2 helpPO22 = new HelpPO2();
//        helpPO22.setHelpTitle("22222222");
//        List<HelpPO2> helpPO2s = new ArrayList<>();
//        helpPO2s.add(helpPO2);
//
//        List<String> tests = new ArrayList<>();
//        tests.add("3333333");
//
//
//        HelpPO1 helpPO1 = new HelpPO1();
//        helpPO1.setHelpPO2(helpPO2);
//        helpPO1.setHelpPO2List(helpPO2s);
//        helpPO1.setTests(tests);
//        helpPO1.setHelpTitle("aaaaaaaaaa");
//
//        handleItemForPOList(helpPO2s,true);
//        System.out.println(helpPO2s);
//        handleItemForPOList(helpPO2s,false);
//        System.out.println(helpPO2s);
   }
}
