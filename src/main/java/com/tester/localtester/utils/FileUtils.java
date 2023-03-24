package com.tester.localtester.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileUtils {

    //public static void main(String[] args) {
    public String getActualFile(String fileDirectory, String key) {
        File folder = new File(fileDirectory);
        File[] listOfFiles = folder.listFiles();
        String fileName = null;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileName = listOfFiles[i].getName();
                if (fileName.contains(key)) {
                    return fileName;
                }

            }
        }
        return fileName;
    }

    public File[] getFileByName(String directory, String fileName) {
        File dir = new File(directory);

        File[] matches = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.contains(fileName);
            }
        });
        return matches;

    }
}
