plugins {
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.spring.cloud.gateway)
    implementation(libs.spring.cloud.eureka)
    testImplementation(libs.spring.boot.starter.test)
}

