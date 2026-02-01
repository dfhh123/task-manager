plugins {
    alias(libs.plugins.dependency.management) apply false
    alias(libs.plugins.spring.boot) apply false
}

allprojects {
    group = "com.superpet"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    apply(plugin = "java-library")

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    repositories {
        mavenCentral()
        maven { url = uri("https://packages.confluent.io/maven/") }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "FAILED", "SKIPPED")
        }
    }
}