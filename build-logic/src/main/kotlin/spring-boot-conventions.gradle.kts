plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    java
}

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.5")
    }
}

