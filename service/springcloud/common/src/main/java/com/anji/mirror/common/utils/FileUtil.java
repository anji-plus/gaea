package com.anji.mirror.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by raodeming on 2020/10/7.
 */
public class FileUtil {
    /**
     * @param path 文件夹路径
     * @return java.util.List<java.io.File>
     * @description 不使用递归的方法调用
     * @author anji gaea teams
     * @date 2019/6/14 17:34
     * @version V1.0
     */
    public static List<File> traverseFolder1(String path) {
        List<File> fileList = new ArrayList<>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    fileList.add(file2);
                    System.out.println("文件:" + file2.getAbsolutePath());
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        fileList.add(file2);
                        System.out.println("文件:" + file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        return fileList;
    }

    /**
     * @param path 文件夹路径
     * @return java.util.List<java.io.File>
     * @description 使用递归的方法调用
     * @author anji gaea teams
     * @date 2019/6/14 17:35
     * @version V1.0
     */
    public static List<File> traverseFolder2(String path) {
        List<File> fileList = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        List<File> child = traverseFolder2(file2.getAbsolutePath());
                        if (null != child && child.size() > 0) {
                            fileList.addAll(child);
                        }

                    } else {
                        fileList.add(file2);
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        return fileList;
    }

    /**
     * @param path 文件夹路径
     * @return java.util.List<java.io.File>
     * @description 使用递归的方法调用，并判断文件名是否以.suffix结尾
     * @author anji gaea teams
     * @date 2019/6/14 17:35
     * @version V1.0
     */
    public static List<File> getFileList(String path, String suffix) {
        List<File> fileList = new ArrayList<>();
        File dir = new File(path);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                // 判断是文件还是文件夹
                if (files[i].isDirectory()) {
                    // 获取文件绝对路径
                    List<File> fileList1 = getFileList(files[i].getAbsolutePath(), suffix);
                    if (fileList1.size() > 0) {
                        fileList.addAll(fileList1);
                    }
                    // 判断文件名是否以.jpg结尾
                } else if (fileName.endsWith(suffix)) {
                    String strFileName = files[i].getAbsolutePath();
                    System.out.println("---" + strFileName);
                    fileList.add(files[i]);
                } else {
                    continue;
                }
            }
        }
        return fileList;
    }


    public static ArrayList<File> getFiles(String path) throws Exception {
//目标集合fileList
        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
//如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else {
//如果文件是普通文件，则将文件句柄放入集合中
                    fileList.add(fileIndex);
                }
            }
        }
        return fileList;
    }
}
