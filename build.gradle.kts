import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        maven("https://dl.bintray.com/jetbrains/intellij-plugin-service")
    }
}

plugins {
    java
    id("org.jetbrains.intellij") version "0.7.3"
    kotlin("jvm") version "1.5.0"
    idea
}

group = "com.github.gindex"
version = "1.1.4"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val ideaVersion: String by project
val ideaType: String by project
val downloadIdeaSources: String by project

intellij {
    version = ideaVersion
    type = ideaType
    pluginName = "JavaDoc HTML Cleaner"
    setPlugins("java")
    downloadSources = downloadIdeaSources.toBoolean()
    sandboxDirectory = project.rootDir.canonicalPath + "/.sandbox"
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs.plus("-progressive")
    }
}

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = true

        excludeDirs.add(file(intellij.sandboxDirectory))
        excludeDirs.add(file("testData"))
    }
}

defaultTasks("clean", "buildPlugin")
