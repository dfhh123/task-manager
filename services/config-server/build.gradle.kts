plugins {
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.spring.cloud.config.server)
    testImplementation(libs.spring.boot.starter.test)
}