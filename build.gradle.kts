import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        maven("https://dl.bintray.com/jetbrains/intellij-plugin-service")
    }
}

plugins {
    java
    id("org.jetbrains.intellij") version "0.4.13"
    kotlin("jvm") version "1.3.60"
    idea
}

group = "com.github.gindex"
version = "1.0.1"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


intellij {
    version = "2019.2"
    pluginName = "JavaDoc HTML Cleaner"
    setPlugins("java")

    downloadSources = true

    sandboxDirectory = project.rootDir.canonicalPath + "/.sandbox"
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = true

        excludeDirs.add(file(intellij.sandboxDirectory))
    }
}

defaultTasks("clean", "buildPlugin")