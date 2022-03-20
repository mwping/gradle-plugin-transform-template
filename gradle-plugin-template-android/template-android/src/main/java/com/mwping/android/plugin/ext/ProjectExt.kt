package com.mwping.android.plugin.ext

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

/**
 * weiping@atlasv.com
 * 2021/12/3
 */

val Project.androidApp: AppExtension get() = extensions["android"] as AppExtension
