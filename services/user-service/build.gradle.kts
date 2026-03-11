plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(platform(libs.spring.cloud.bom))

    implementation(libs.bundles.user.service.common)
    implementation(project(":shared:schemas"))

    compileOnly(libs.lombok)


    annotationProcessor(libs.lombok)
    annotationProcessor(libs.mapstruct.processor)


    testImplementation(libs.bundles.user.service.testing)

    testCompileOnly(libs.lombok)
    testImplementation("org.hamcrest:hamcrest:3.0")
}