plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(platform(libs.spring.cloud.bom))

    implementation(libs.bundles.api.gateway.common)

    compileOnly(libs.lombok)
}

tasks.bootJar {
    archiveFileName.set("app.jar")

    layered {
        enabled = true
    }
}