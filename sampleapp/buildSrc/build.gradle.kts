plugins {
    `kotlin-dsl`
}

dependencies {
    // Open this line to learn plugin develop
    // implementation("com.google.firebase:perf-plugin:1.4.1")

    val asmVersion = "9.1"
    implementation("org.ow2.asm:asm:$asmVersion")
    implementation("org.ow2.asm:asm-commons:${asmVersion}")

    implementation("com.android.tools.build:gradle:7.1.2")
    implementation("com.android.tools:sdk-common:30.1.2")
    implementation("com.google.code.gson:gson:2.9.0")
}
repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}