import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose") version "1.0.0-alpha3"
}

kotlin {
    android()
    jvm {
        compilations {
            val main by getting

            tasks.named<Jar>("jvmJar").configure {
                from(main.compileDependencyFiles.map { if (it.isDirectory) it else zipTree(it) })
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            }
        }
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }

        commonMain {
            dependencies {
                compileOnly(project(":core"))
                compileOnly("com.arkivanov.decompose:decompose:0.3.1")
                val retrofitVersion = "2.9.0"
                implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
                implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
                implementation("org.jsoup:jsoup:1.14.2")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val androidMain by getting {
            dependencies {
                implementation("androidx.appcompat:appcompat:1.3.1")
            }
        }
    }
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "net.accelf.contral.mastodon"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "0.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            resources.srcDir("src/commonMain/resources")
        }
    }
}
