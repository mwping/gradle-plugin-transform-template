package config

import SampleTransformPlugin
import com.android.build.api.instrumentation.ClassData
import com.google.gson.Gson
import config.SampleTraceConfigManager.classMapping
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
    val includes by lazy {
        traceConfig.includes
    }

    val classMapping = hashMapOf<ClassData, ClassMethodInfo>()

}

fun ClassData.isInstrumentable(): Boolean {
    if (className.startsWith("android.") || className.startsWith("androidx.")) {
        return false
    }
    return includes?.find {
        this.matchClass(it.className, it.classMatchType)
    }?.also {
        classMapping[this] = it
    } != null
}

fun ClassData.matchClass(targetClassName: String, classMatchType: ClassMatchType): Boolean {
    return when (classMatchType) {
        ClassMatchType.Extends -> {
            superClasses.contains(targetClassName)
        }
        else -> {
            this.className.startsWith(targetClassName)
        }
    }
}