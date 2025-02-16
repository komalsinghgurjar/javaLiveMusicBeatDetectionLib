/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.12/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

// publishing 
group = "io.github.komalsinghgurjar"  // Replace with your package group
val artifactIdVal: String? = "java-live-music-beat-detection-lib"
val versionVal: String? = "1.0.0"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // Publishes the Java component
            groupId = group.toString() // Replace with your package group
            artifactId = artifactIdVal.toString() // Replace with your library name
            version = versionVal.toString() // Update with your actual version
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/komalsinghgurjar/javaLiveMusicBeatDetectionLib")
            credentials {
    username = (project.findProperty("gpr.user") as? String) ?: System.getenv("USERNAME")
    password = (project.findProperty("gpr.token") as? String) ?: System.getenv("TOKEN")
}

        }
   }
}
