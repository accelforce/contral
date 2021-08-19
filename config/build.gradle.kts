plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("reflect"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.6.0")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val desktopMain by getting {
            dependencies {
                implementation("net.harawata:appdirs:1.2.1")
            }
        }
    }
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 23
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}
