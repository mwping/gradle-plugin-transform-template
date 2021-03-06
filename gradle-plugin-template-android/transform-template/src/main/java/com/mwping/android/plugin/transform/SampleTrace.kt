package com.mwping.android.plugin.transform

import com.mwping.android.plugin.transform.config.SampleTraceConfigManager.traceClassName
import com.mwping.android.plugin.transform.config.SampleTraceConfigManager.traceManagerClassName
import com.mwping.android.plugin.transform.config.SampleTraceConfigManager.traceStartMethodDesc
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
private const val LOG_START_METHOD_NAME_START = "start"
private const val LOG_START_METHOD_NAME_STOP = "stop"

class SampleTrace(private val adviceAdapter: AdviceAdapter) {
    private val classType: Type = Type.getObjectType(traceClassName)
    private var timerLocalIndex = -1
    fun start(className: String, methodName: String) {
        timerLocalIndex = this.adviceAdapter.newLocal(classType)
        this.adviceAdapter.push(className)
        this.adviceAdapter.push(methodName)
        this.adviceAdapter.invokeStatic(
            Type.getObjectType(traceManagerClassName),
            Method(LOG_START_METHOD_NAME_START, traceStartMethodDesc)
        )
        this.adviceAdapter.storeLocal(timerLocalIndex)
    }

    fun stop() {
        check(timerLocalIndex != -1) { "LogTrace.stop called without calling LogTrace.start" }
        this.adviceAdapter.loadLocal(timerLocalIndex)
        this.adviceAdapter.invokeVirtual(classType, Method(LOG_START_METHOD_NAME_STOP, "()V"))
    }
}