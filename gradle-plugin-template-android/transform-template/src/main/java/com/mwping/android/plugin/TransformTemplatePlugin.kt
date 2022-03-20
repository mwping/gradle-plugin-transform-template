package com.mwping.android.plugin

import com.mwping.android.plugin.ext.androidApp
import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformTemplatePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.warn("-------------TransformTemplatePlugin applied-------------")
        val android = project.androidApp
        project.afterEvaluate {
            project.logger.warn("${android.defaultConfig}")
        }
    }
}