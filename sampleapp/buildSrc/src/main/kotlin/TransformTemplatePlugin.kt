import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * weiping@atlasv.com
 * 2022/3/20
 */
class TransformTemplatePlugin:Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.warn("Apply TransformTemplatePlugin----------")
    }
}