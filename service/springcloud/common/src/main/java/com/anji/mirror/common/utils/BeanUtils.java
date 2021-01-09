package com.anji.mirror.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class BeanUtils extends org.springframework.beans.BeanUtils{

    public static void copyProperties(Object source, Object target, boolean ignoreNullValue ){
        if(ignoreNullValue){
            String[] ignoreProperties = getNullPropertyNames(source);
            org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
        }else{
            org.springframework.beans.BeanUtils.copyProperties(source, target);
        }
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
