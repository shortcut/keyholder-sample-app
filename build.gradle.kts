import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.diffplug.spotless") version "5.6.1"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

buildscript {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("gradle.plugin.me.tadej:versioning:0.2.0")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:perf-plugin:1.4.0")
    }
}

subprojects {
    apply{ plugin("com.diffplug.spotless")}
    configure<com.diffplug.gradle.spotless.SpotlessExtension>  {
        kotlin {
            target("**/*.kt")
            ktlint("0.39.0").userData(mapOf(
                "disabled_rules" to "no-wildcard-imports")
            )
        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.allWarningsAsErrors = true
    }
}