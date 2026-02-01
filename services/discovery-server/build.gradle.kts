plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(platform(libs.spring.cloud.bom))

    implementation(libs.bundles.discovery.service.common)
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}