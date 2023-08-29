package com.lazy.files.action;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public interface IFileOperations {
    void performOperation(Scanner scr, String[] args);

    default String[] readInputs(Scanner scr, String[] args) {
        String input;
        //First element is the operation hence should be ignored.
        if (args.length < 2) {
            do {
                System.out.println("Please provide either full path or relative path from current location.");
                System.out.println("Multiple locations separated by comma can be passed.");
                input = scr.nextLine();
            } while (input == null || input.trim().isEmpty());
            return input.split(",");
        } else {
            return Arrays.copyOfRange(args, 1, args.length);
        }
    }

    default void printMessage(String msg, boolean noLine, PrintStream fileStream) {
        String updatedMessage = noLine ? msg : (msg + System.lineSeparator());
        System.out.print(updatedMessage);
        if (fileStream != null) {
            fileStream.print(updatedMessage);
        }
    }
}
