package com.tester.localtester.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileUtils {

    //public static void main(String[] args) {
    public String getActualFile(String fileDirectory, String key) {

        File folder = new File(fileDirectory);
        File[] listOfFiles = folder.listFiles();
        String fileName = null;
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                fileName = listOfFile.getName();
                if (fileName.equals(key))
                    return fileName;
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
