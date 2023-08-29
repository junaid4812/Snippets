package com.lazy.files.util;

import java.io.File;

public class Utility {

    public static boolean isValidDirectory(File file) {
        return  file.exists() && file.isDirectory();
    }
}
