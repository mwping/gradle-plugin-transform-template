package config

import SampleTransformPlugin
import com.android.build.api.instrumentation.ClassData
import com.google.gson.Gson
import config.SampleTraceConfigManager.excludes
import config.SampleTraceConfigManager.includes

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
object SampleTraceConfigManager {
    private val traceConfig: SampleTraceConfig by lazy {
        Gson().fromJson(SampleTransformPlugin.configFile.readText(), SampleTraceConfig::class.java)
    }
    val traceClassName by lazy {
        traceConfig.traceClass.replace(".", "/")
    }
    val traceManagerClassName by lazy {
        traceConfig.traceManagerClass.replace(".", "/")
    }
    val traceStartMethodDesc by lazy {
        "(Ljava/lang/String;Ljava/lang/String;)L$traceClassName;"
    }
    val excludes by lazy {
        traceConfig.excludes
    }
    val includes by lazy {
        traceConfig.includes
    }
}

fun ClassData.isInstrumentable(): Boolean {
    if (className.startsWith("android.") || className.startsWith("androidx.")) {
        return false
    }
    if (excludes?.find {
            this.className.startsWith(it.className)
        } != null) {
        return false
    }
    return includes?.find {
        this.matchClass(it.className, it.classMatchType)
    } != null
}

fun ClassData.matchClass(targetClassName: String, matchType: MatchType): Boolean {
    return when (matchType) {
        MatchType.Extends -> {
            superClasses.contains(targetClassName)
        }
        else -> {
            this.className.startsWith(targetClassName)
        }
    }
}