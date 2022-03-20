plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.google.firebase:perf-plugin:1.4.1")
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation("com.android.tools:sdk-common:30.1.2")
}
repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}