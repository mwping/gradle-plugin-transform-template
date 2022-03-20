package com.mwping.android.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class GreetingTask : DefaultTask() {

    @TaskAction
    fun greet() {
        project.logger.warn("Greet!")
    }
}