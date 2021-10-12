plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("org.jetbrains.compose") version "1.0.0-alpha3"
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }

        commonMain {
            dependencies {
                implementation(project(":config"))
                val decomposeVersion = "0.3.1"
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
                implementation("net.accelf.accompanist:accompanist-swiperefresh:0.19.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
                api(compose.foundation)
                api(compose.material)
                api(compose.runtime)
                api(compose.ui)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val androidMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.0.0-alpha.9")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val desktopMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.0.0-alpha.9")
                implementation("net.harawata:appdirs:1.2.1")
            }
        }
    }
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}
