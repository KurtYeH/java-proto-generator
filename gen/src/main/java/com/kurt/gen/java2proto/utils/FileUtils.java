package com.kurt.gen.java2proto.utils;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

/**
 * @author yh
 * @create 2022/5/20 10:40 上午
 * @desc
 */
public class FileUtils {

    public static List<String> getAllFile(File file) {

        List<String> allFiles = Lists.newArrayList();
        File[] files = file.listFiles();

        if (files == null) {
            return allFiles;
        }
        for (File temp : files) {
            if (file.isDirectory()) {
                getAllFile(temp);
            }
            allFiles.add(temp.getName());
        }
        return allFiles;
    }


}
