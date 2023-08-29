package com.lazy.files.action;

import com.lazy.files.util.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * PrintDirectoryStructure is a class responsible for printing the file structure in the following format.
 * It implements the IFileOperations interface and provides the definition of performOperation method performing
 * the iteration of the input folder and its subdirectories and printing the directory structure on console and an
 * output file DirectoryStructure_%s.log where %s will be replaced with the input directory name
 * files
 * ├─action
 * │  ├─DirectoryStructurePrinter.java
 * │  ├─IFileOperations.java
 * │  └── PrintDirectoryStructure.java
 * ├─util
 * │  └── CombinedPrintStream.java
 * └── FilesMain.java
 */

public class PrintDirectoryStructure implements IFileOperations {
    private static final String HORIZONTAL_BRANCH = "├─";
    private static final String LAST_FILE = "└── ";
    private static final String VERTICAL_LINE = "│";// Unicode for │
    private static final String OUTPUT_FILE = "DirectoryStructure_%s.log";

    public PrintDirectoryStructure() {
        System.out.println("Performing Print Directory Structure Operation!!");
    }

    /**
     * Input Parameters: Scanner scr, String[] directoryPaths
     * Takes an input array of directories (Either full path or relative path from program location).
     * If no input is passed i.e. the input directoryPath is of size less than 2, program will use scanner object to prompt
     * user to provide the directory paths which can be multiple locations separated by comma.
     * NullPointerException is thrown if the directoryPaths is null
     * Output: This method will recursively print the directory structure in the below format on console and in the output
     * file with each file containing the directory structure of one input
     * files
     * ├─action
     * │  ├─DirectoryStructurePrinter.java
     * │  ├─IFileOperations.java
     * │  └── PrintDirectoryStructure.java
     * ├─util
     * │  └── CombinedPrintStream.java
     * └── FilesMain.java
     * If the input path is not a directory or does not exist, message is displayed
     */
    @Override
    public void performOperation(Scanner scr, String[] args) {
        String[] directoryPaths = readInputs(scr, args);
        for (String strPath : directoryPaths) {
            File rootPath = new File(strPath.trim());
            if (Utility.isValidDirectory(rootPath)) {
                String fileName = ".".equals(strPath.trim())? rootPath.getAbsoluteFile().getParentFile().getName()
                        : rootPath.getName();
                File outFile = new File(String.format(OUTPUT_FILE, fileName));
                try (PrintStream fileStream = new PrintStream(new FileOutputStream(outFile, false))) {
                    printMessage(fileName, false, fileStream);
                    recursiveLoop(rootPath.listFiles(), 0, fileStream);
                    printMessage("Output also printed to: " + outFile.getAbsolutePath(), false, null);
                } catch (IOException e) {
                    System.out.printf("!!Error Occurred!!\t %s %n", e.getMessage());
                }
            } else {
                //This program is to print the file structure, new path, non directory will not be useful
                printMessage(String.format("%nInput path %s is not a valid directory!%n",
                        rootPath.getAbsolutePath()), false, null);
            }
        }
    }

    private void recursiveLoop(File[] children, int depth, PrintStream fileStream) {
        if (children != null) {
            Arrays.sort(children, Comparator.comparing(File::isDirectory).reversed().thenComparing(File::getName));
            int maxLength = children.length;
            for (int i = 0; i < maxLength; i++) {
                File child = children[i];
                printIndent(depth, fileStream);
                String prefix = (i == maxLength - 1) ? LAST_FILE : HORIZONTAL_BRANCH;
                printMessage(prefix + child.getName(), false, fileStream);
                if (child.isDirectory()) {
                    recursiveLoop(child.listFiles(), depth + 1, fileStream);
                }
            }
        }
    }

    private void printIndent(int depth, PrintStream fileStream) {
        for (int i = 0; i < depth; i++) {
            // Two spaces for each level
            printMessage(VERTICAL_LINE + "  ", true, fileStream);
        }
    }


}
