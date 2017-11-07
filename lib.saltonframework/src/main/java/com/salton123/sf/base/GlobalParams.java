package com.salton123.sf.base;

import android.os.Environment;

import java.io.File;

public abstract class GlobalParams {
    public static final String BASE_FOLDER ="NewSalton";
    public static final String BASE_FILE_PATH= Environment.getExternalStorageDirectory()+ File.separator+BASE_FOLDER;
    public static final String DEFAULT_PLUGIN_PATH =BASE_FILE_PATH+ File.separator+"plugin"+File.separator;
    public static final boolean IS_DEBUGGABLE = true;

}
