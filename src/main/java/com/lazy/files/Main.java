package com.lazy.files;

import java.util.Scanner;
import com.lazy.files.action.IFileOperations;
import com.lazy.files.action.PrintDirectoryStructure;
import com.lazy.files.action.PrintFileTypes;
import com.lazy.files.enums.FileOperations;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try (Scanner scr = new Scanner(System.in)) {
            FileOperations operation = validateParameters(args, scr);
            IFileOperations file = getFileOperationsInstance(operation);
            assert file != null;
            file.performOperation(scr,args);
            System.out.println("--------------------------");
        }
    }

    private static IFileOperations getFileOperationsInstance(FileOperations operation){
        IFileOperations file = null;
        switch (operation){
            case DIR_STR :
                file = new PrintDirectoryStructure();
                break;
            case FILE_TYPES:
                file = new PrintFileTypes();
            default:
                break;
        }
        return file;
    }

    private static FileOperations validateParameters(String[] args, Scanner scr){
        FileOperations operation = args.length == 0 ? null: FileOperations.getOperation(args[0]);
        while(operation == null){
            FileOperations.printOperations();
            System.out.println("Please enter your choice:");
            operation = FileOperations.getOperation(scr.nextLine());
        }
        return operation;
    }
}