package com.mwping.android.plugin.transform

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.io.File

/**
 * weiping@atlasv.com
 * 2022/3/20
 */
class SampleTransformPlugin : Plugin<Project> {
    private var foundApplicationPlugin = false

    override fun apply(project: Project) {
        project.pluginManager.withPlugin("com.android.application") {
            foundApplicationPlugin = true
            val logTraceConfigFilePath = project.property("logTraceConfigFile")?.toString()
                ?: error("Please add:logTraceConfigFilePath=your-config-file-path in file gradle.properties")
            configFile = File(logTraceConfigFilePath)
            this@SampleTransformPlugin.perform(project)
        }

        project.afterEvaluate {
            check(foundApplicationPlugin) { "${this@SampleTransformPlugin.javaClass.simpleName} must only be used with Android application projects. Need to apply the 'com.android.application' plugin with this plugin." }
        }
    }

    private fun perform(project: Project) {
        registerForProject(project)
    }

    private fun registerForProject(project: Project) {
        project.extensions.getByType(
            AndroidComponentsExtension::class
        ).onVariants { variant ->
            registerForVariant(variant as ApplicationVariant)
        }
    }

    private fun registerForVariant(appVariant: ApplicationVariant) {
        appVariant.transformClassesWith(
            SampleTraceClassVisitorFactory::class.java,
            InstrumentationScope.ALL
        ) { }
        appVariant.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_CLASSES)
    }

    companion object {
        lateinit var configFile: File
    }
}