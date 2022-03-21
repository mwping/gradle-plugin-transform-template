import com.google.firebase.perf.plugin.instrumentation.model.ClassInfo
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
            this.classInfo,
            this.api,
            rootMethodVisitor,
            access,
            methodName,
            methodDesc
        )
    }

    class TemplatePerfMethodVisitor(
        private val classInfo: ClassInfo,
        api: Int,
        methodVisitor: MethodVisitor?,
        access: Int,
        private val perfMethodName: String,
        perfMethodDesc: String?
    ) : AdviceAdapter(api, methodVisitor, access, perfMethodName, perfMethodDesc) {
        private val templateTrace = TemplateTrace(this)
        private var traceAdded = false
        override fun onMethodEnter() {
            super.onMethodEnter()
            templateTrace.start(this.classInfo.type.className, this.perfMethodName)
            traceAdded = true
        }

        override fun onMethodExit(opcode: Int) {
            super.onMethodExit(opcode)
            if (traceAdded) {
                templateTrace.stop()
            }
        }

        override fun visitMethodInsn(
            opcodeAndSource: Int,
            owner: String?,
            name: String?,
            descriptor: String?,
            isInterface: Boolean
        ) {
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }
}