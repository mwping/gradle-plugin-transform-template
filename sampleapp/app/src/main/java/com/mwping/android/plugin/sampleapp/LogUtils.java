package com.mwping.android.plugin.sampleapp;

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
class LogUtils {
    public static LogTrace start(String className, String methodName) {
        return new LogTrace(className, methodName).start();
    }
}
