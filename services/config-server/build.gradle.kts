plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(platform(libs.spring.cloud.bom))
    implementation(platform (libs.spring.boot.bom))

    implementation(libs.bundles.config.service.common)
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
}