pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.0.21"
        id("org.jetbrains.kotlin.kapt") version "2.0.21"
        id("org.jetbrains.kotlin.plugin.spring") version "2.0.21"
        id("org.springframework.boot") version "3.5.6"
        id("io.spring.dependency-management") version "1.1.6"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://packages.confluent.io/maven")
    }
}


