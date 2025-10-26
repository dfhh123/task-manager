plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}
