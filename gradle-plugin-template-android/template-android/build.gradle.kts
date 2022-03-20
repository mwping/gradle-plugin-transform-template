plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    // Use JUnit test framework for unit tests
    testImplementation("junit:junit:4.13")
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation(gradleApi())
}

group = "com.mwping.android.plugin"
version = "1.0.0"

gradlePlugin {
    // Define the plugin
    val greeting by plugins.creating {
        id = "$group.template-android"
        implementationClass = "com.mwping.android.plugin.AndroidTemplatePlugin"
    }
}

publishing {
    repositories {
        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/mwping/android-developer")
            credentials {
                username = System.getenv("GPR_USR") ?: project.findProperty("GPR_USR").toString()
                password = System.getenv("GPR_KEY") ?: project.findProperty("GPR_KEY").toString()
            }
        }
    }
}

// Add a source set and a task for a functional test suite
val functionalTest by sourceSets.creating
gradlePlugin.testSourceSets(functionalTest)

configurations[functionalTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())

val functionalTestTask = tasks.register<Test>("functionalTest") {
    testClassesDirs = functionalTest.output.classesDirs
    classpath = configurations[functionalTest.runtimeClasspathConfigurationName] + functionalTest.output
}

tasks.check {
    // Run the functional tests as part of `check`
    dependsOn(functionalTestTask)
}
