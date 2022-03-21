package com.mwping.android.plugin.transform.config

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
data class ClassMethodInfo(
    val className: String,
    val classMatchType: ClassMatchType,
    val includeMethods: List<String>?
)
