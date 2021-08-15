plugins {
    kotlin("multiplatform")
    id("com.android.application")
}

kotlin {
    android()
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                compileOnly(project(":core"))
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
