package com.lazy.files.action;

import com.lazy.files.util.Utility;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;

public class PrintFileTypes implements IFileOperations {

    public PrintFileTypes() {
        System.out.println("Performing Print File Types Operation!!");

    }

    /**
     * Input Parameters: Scanner scr, String[] directoryPaths
     * Takes an input array of directories (Either full path or relative path from current location).
     * If no input is passed i.e. the input directoryPath is of size less than 2, program will use scanner object to prompt
     * user to provide the directory paths which can be multiple locations separated by comma.
     * NullPointerException is thrown if the directoryPaths is null
     * Output: This method will read the directory structure and all the file extensions from the input directory and its
     * subdirectories and print them on the console
     * If the input path is not a directory or does not exist, message is displayed
     */
    @Override
    public void performOperation(Scanner scr, String[] args) {
        String[] directoryPaths = readInputs(scr, args);
        for (String strPath : directoryPaths) {
            Set<String> fileTypes = new HashSet<>();
            File rootPath = new File(strPath.trim());
            if (Utility.isValidDirectory(rootPath)) {
                StringJoiner joiner = new StringJoiner(", ");
                recursiveLoop(rootPath.listFiles(), fileTypes);
                fileTypes.forEach(joiner::add);
                System.out.println(strPath + " - " + joiner);
            } else {
                //This program is to print the file structure, new path, non directory will not be useful
                printMessage(String.format("%nInput path %s is not a valid directory!%n",
                        rootPath.getAbsolutePath()), false, null);
            }
        }
    }

    private void recursiveLoop(File[] children, Set<String> fileTypes) {
        if (children != null) {
            for (File file : children) {
                if (file.isDirectory()) {
                    recursiveLoop(file.listFiles(), fileTypes);
                } else {
                    String fileName = file.getName();
                    int lastDotIndex = fileName.lastIndexOf(".");
                    if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
                        fileTypes.add(fileName.substring(lastDotIndex + 1));
                    }
                }
            }
        }
    }
}
