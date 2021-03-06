package com.mwping.android.plugin.transform.config

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
data class SampleTraceConfig(
    val traceClass: String,
    val traceManagerClass: String,
    val includes: List<ClassMethodInfo>?
)