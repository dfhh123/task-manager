plugins {
    id("java-conventions")
    id("spring-boot-conventions")
}

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.flyway)
    implementation(libs.spring.kafka)
    implementation(project(":shared:schemas"))
    implementation(libs.spring.boot.starter.quartz)
    implementation(libs.spring.cloud.eureka)
    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.spring.cloud.starter.config)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.spring.boot.starter.validation)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.kafka.test)
    runtimeOnly(libs.postgres)
}