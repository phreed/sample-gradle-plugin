
pluginManagement {
    plugins {
//        id("org.jetbrains.kotlin.jvm").version("2.0.0")
    }
    resolutionStrategy {
    }
    repositories {
        // Get community plugins from the Gradle Plugin Portal
        gradlePluginPortal()
        // mavenLocal()
    }
}

rootProject.name = "sample"

dependencyResolutionManagement {
    repositories {
        // Get components from Maven Central
        mavenCentral()
    }
}

include("sample-plugin")
