package com.mwping.android.plugin.sampleapp

import android.util.Log

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
class LogTrace(val className: String, val methodName: String) {
    var startMs = 0L
    var stopMs = 0L

    fun start(): LogTrace {
        startMs = System.currentTimeMillis()
        return this
    }

    fun stop() {
        stopMs = System.currentTimeMillis()
        Log.d(
            "LogTrace::",
            "className=$className, methodName=$methodName: \ntime: ${stopMs - startMs}ms"
        )
    }
}