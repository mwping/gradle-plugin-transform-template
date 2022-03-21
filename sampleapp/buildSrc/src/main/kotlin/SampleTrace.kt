import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

/**
 * weiping@atlasv.com
 * 2022/3/21
 */
private const val TRACE_CLASS_NAME = "com/mwping/android/plugin/sampleapp/LogTrace"
private const val LOG_UTILS_CLASS_NAME = "com/mwping/android/plugin/sampleapp/LogUtils"
private const val LOG_START_METHOD_NAME_START = "start"
private const val LOG_START_METHOD_NAME_STOP = "stop"
private const val LOG_START_METHOD_DESC =
    "(Ljava/lang/String;)Lcom/mwping/android/plugin/sampleapp/LogTrace;"

class TemplateTrace(private val adviceAdapter: AdviceAdapter) {
    private val classType: Type =
        Type.getObjectType(TRACE_CLASS_NAME)
    private var timerLocalIndex = -1
    fun start(timerName: String) {
        timerLocalIndex = this.adviceAdapter.newLocal(classType)
        this.adviceAdapter.push(timerName)
        this.adviceAdapter.invokeStatic(
            Type.getObjectType(LOG_UTILS_CLASS_NAME),
            Method(LOG_START_METHOD_NAME_START, LOG_START_METHOD_DESC)
        )
        this.adviceAdapter.storeLocal(timerLocalIndex)
    }

    fun stop() {
        check(timerLocalIndex != -1) { "LogTrace.stop called without calling LogTrace.start" }
        this.adviceAdapter.loadLocal(timerLocalIndex)
        this.adviceAdapter.invokeVirtual(classType, Method(LOG_START_METHOD_NAME_STOP, "()V"))
    }

}