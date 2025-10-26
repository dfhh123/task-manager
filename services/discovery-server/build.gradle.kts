plugins {
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.spring.cloud.eureka.server)
    testImplementation(libs.spring.boot.starter.test)
}