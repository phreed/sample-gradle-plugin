/*
 * For more details take a look at the Writing Custom Plugins chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.0.2/userguide/custom_plugins.html
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the Kotlin Gradle plugin development plugin to add support for developing Gradle plugins
    `kotlin-dsl`
    `maven-publish`
}

group = "edu.vu"
version = "2024.7.1"

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
//        vendor = JvmVendorSpec.AZUL
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    testImplementation(gradleTestKit())
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencies.constraints {
    api( "org.apache.maven:maven-plugin-api:2.0")
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin test framework
            useKotlinTest("1.8.10")
        }

        // Create a new test suite
        val functionalTest by registering(JvmTestSuite::class) {
            // Use Kotlin test framework
            useKotlinTest("1.8.10")

            dependencies {
                // functionalTest test suite depends on the production code in tests
                implementation(project())
            }

            targets {
                all {
                    // This test suite should run after the built-in test suite has run its tests
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}

gradlePlugin {
    plugins {
        create("sample") {
            id = "edu.vu.sample-plugin"
            implementationClass = "edu.vu.SamplePlugin"
        }
    }
    testSourceSets(sourceSets["functionalTest"])
}


tasks {
    named<Task>("check") {
        // Include functionalTest as part of the check lifecycle
        dependsOn(testing.suites.named("functionalTest"))
    }
}

publishing {
}
