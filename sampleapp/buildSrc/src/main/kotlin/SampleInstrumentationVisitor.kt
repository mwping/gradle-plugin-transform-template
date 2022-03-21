import com.google.firebase.perf.plugin.instrumentation.model.ClassInfo
import config.ClassMethodInfo
import config.SampleTraceConfigManager.classMapping
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

/**
 * weiping@atlasv.com
 * 2022/3/20
 */
class SampleInstrumentationVisitor(api: Int, classVisitor: ClassVisitor) :
    ClassVisitor(api, classVisitor) {
    private val rootClassVisitor = classVisitor
    private val classInfo = ClassInfo()
    private val matchClassInfo by lazy {
        classMapping.keys.find { classData ->
            classData.className == classInfo.type.className
        }?.let {
            val info = classMapping[it]
            classMapping.remove(it)
            info
        }
    }

    override fun visit(
        version: Int,
        access: Int,
        className: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, className, signature, superName, interfaces)
        classInfo.type = Type.getObjectType(className)
        classInfo.interfaces = interfaces
    }

    override fun visitMethod(
        access: Int,
        methodName: String,
        methodDesc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val rootMethodVisitor: MethodVisitor =
            rootClassVisitor.visitMethod(access, methodName, methodDesc, signature, exceptions)
        return TemplatePerfMethodVisitor(
            this.matchClassInfo,
            this.classInfo,
            this.api,
            rootMethodVisitor,
            access,
            methodName,
            methodDesc
        )
    }

    class TemplatePerfMethodVisitor(
        private val matchClassInfo: ClassMethodInfo?,
        private val classInfo: ClassInfo,
        api: Int,
        methodVisitor: MethodVisitor?,
        access: Int,
        private val perfMethodName: String,
        perfMethodDesc: String?
    ) : AdviceAdapter(api, methodVisitor, access, perfMethodName, perfMethodDesc) {
        private val templateTrace = SampleTrace(this)
        private var traceAdded = false
        override fun onMethodEnter() {
            super.onMethodEnter()
            matchClassInfo?.includeMethods?.find {
                it == "*" || perfMethodName.startsWith(it)
            }?.also {
                templateTrace.start(this.classInfo.type.className, this.perfMethodName)
                traceAdded = true
            }
        }

        override fun onMethodExit(opcode: Int) {
            super.onMethodExit(opcode)
            if (traceAdded) {
                templateTrace.stop()
            }
        }
    }
}