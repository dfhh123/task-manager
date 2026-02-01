plugins {
    alias(libs.plugins.dependency.management)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {

    implementation(platform(libs.spring.boot.bom))
    implementation(platform(libs.spring.cloud.bom))

    implementation(libs.bundles.task.service.common)
    implementation(project(":shared:schemas"))

    testImplementation(libs.bundles.task.service.testing)
}

