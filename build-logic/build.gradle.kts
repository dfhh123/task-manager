plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.5.6")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.6")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
}
