package com.lazy.files.enums;


public enum FileOperations {
    DIR_STR("Operation to Print Directory Structure"),
    FILE_TYPES("To list all the file types in the Directory");

    private final String description;
    FileOperations(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static FileOperations getOperation(String input){
        if(input == null || input.trim().isEmpty()){
            return null;
        }
        for(FileOperations fileOperation: FileOperations.values()){
            if(fileOperation.name().equalsIgnoreCase(input)){
                return fileOperation;
            }
        }
        return null;
    }

    public static void printOperations(){
        System.out.println("Available operations are:");
        for(FileOperations opr: FileOperations.values()){
            System.out.println(opr.name()+" - "+ opr.getDescription());
        }
    }
}
