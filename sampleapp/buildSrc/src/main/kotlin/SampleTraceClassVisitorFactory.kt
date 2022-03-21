import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters.None
import config.isInstrumentable
import org.objectweb.asm.ClassVisitor
import javax.annotation.Nonnull

/**
 * weiping@atlasv.com
 * 2022/3/20
 */
abstract class SampleTraceClassVisitorFactory : AsmClassVisitorFactory<None> {
    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return SampleInstrumentationVisitor(
            this.instrumentationContext.apiVersion.get(),
            nextClassVisitor
        )
    }

    override fun isInstrumentable(@Nonnull classData: ClassData): Boolean {
        return classData.isInstrumentable()

    }
}