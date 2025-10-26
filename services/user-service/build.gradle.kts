plugins {
    id("spring-boot-conventions")
    kotlin("kapt")
}

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.cloud.eureka)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.kafka)

    implementation(libs.flyway)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)
    implementation(project(":shared:schemas"))

    implementation(libs.kafka.avro.serializer)

    runtimeOnly(libs.postgres)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.kafka.test)
    testImplementation(project(":shared:schemas"))
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")

    implementation("org.springframework.boot:spring-boot-starter-aop")

    testImplementation("org.springframework.security:spring-security-test")
    compileOnly("org.mapstruct:mapstruct:1.6.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}
