plugins {
    id("spring-boot-conventions")
    id("kotlin-conventions")
}

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.cloud.eureka)
    implementation(libs.spring.boot.starter.data.mongodb)
    implementation(libs.spring.boot.starter.validation)
    implementation(project(":shared:schemas"))
    testImplementation(libs.spring.boot.starter.test)
    compileOnly(libs.mapstruct)
    kapt(libs.mapstruct.processor)
}