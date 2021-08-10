import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose") version "0.5.0-build227"
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("reflect"))
                val decomposeVersion = "0.3.1"
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
            }
        }
    }
}

android {
    compileSdk = 30

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}
