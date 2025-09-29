pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "super-pet"

include(
    ":apps:api-gateway",
    ":services:config-server",
    ":services:discovery-server",
    ":services:notification-service",
    ":services:task-service",
    ":services:user-service",
    ":shared:schemas"
)
