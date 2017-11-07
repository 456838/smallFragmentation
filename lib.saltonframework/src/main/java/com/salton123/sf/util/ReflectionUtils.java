package com.salton123.sf.util;

import android.os.Parcelable;

import java.io.Serializable;

public class ReflectionUtils {

    public static boolean implementsInterface(Class<?> clazz, Class<?> aInterface) {
        
        Class<?>[] itfs = clazz.getInterfaces();
        if (itfs.length == 0) {
            return false;
        }
        for (Class<?> e : itfs) {
            if (e.equals(aInterface)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean implementsSerializable(Class<?> clazz) {
        return implementsInterface(clazz, Serializable.class);
    }
    
    public static boolean implementsParcelable(Class<?> clazz) {
        return implementsInterface(clazz, Parcelable.class);
    }
    
}
