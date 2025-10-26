plugins {
    `java-library`
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

repositories {
    mavenCentral()
    maven { url = uri("https://packages.confluent.io/maven/") }
}

dependencies {
    api(libs.avro)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

avro {
    isCreateSetters.set(false)
    fieldVisibility.set("PRIVATE")
    outputCharacterEncoding.set("UTF-8")
}


