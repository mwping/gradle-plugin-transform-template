package com.mwping.android.plugin

import com.mwping.android.plugin.ext.androidApp
import com.mwping.android.plugin.task.GreetingTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidTemplatePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.warn("-------------AndroidTemplatePlugin applied-------------")
        val android = project.androidApp
        project.afterEvaluate {
            project.logger.warn("${android.defaultConfig}")
        }
        project.tasks.register("greet", GreetingTask::class.java)
    }
}