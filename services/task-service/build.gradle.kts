plugins {
    id("spring-boot-conventions")
    id("kotlin-conventions")
    kotlin("kapt") version "2.0.21"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    kapt("org.mapstruct:mapstruct-processor:1.6.0")
    compileOnly("org.mapstruct:mapstruct:1.6.0")
}