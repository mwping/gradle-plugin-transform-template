package com.mwping.android.plugin.sampleapp

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
object LogUtils {
    @JvmStatic
    fun start(className: String, methodName: String): LogTrace {
        return LogTrace(className, methodName).start()
    }
}