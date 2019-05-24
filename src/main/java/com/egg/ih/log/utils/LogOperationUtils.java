package com.egg.ih.log.utils;

import com.egg.ih.log.dto.HiOperDTO;

/**
 * @author Administrator
 */
public class LogOperationUtils {
    private static final ThreadLocal<HiOperDTO> threadLocal = new ThreadLocal<>();

    public static void set(HiOperDTO log){
        threadLocal.set(log);
    }

    public static HiOperDTO remove(){
        return threadLocal.get();
    }
}
