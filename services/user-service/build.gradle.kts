plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(platform(libs.spring.cloud.bom))

    implementation(libs.bundles.user.service.common)
    implementation(project(":shared:schemas"))
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    compileOnly(libs.lombok)


    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)


    testImplementation(libs.bundles.user.service.testing)

    testCompileOnly(libs.lombok)
    testImplementation("org.hamcrest:hamcrest:3.0")
}