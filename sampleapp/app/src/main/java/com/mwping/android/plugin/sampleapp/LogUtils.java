package com.mwping.android.plugin.sampleapp;

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
class LogUtils {
    public static LogTrace start(String name) {
        return new LogTrace(name).start();
    }
}
