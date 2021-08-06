import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose") version "0.5.0-build227"
}

group = "net.accelf.contral.core"
version = "0.0.0"

kotlin {
    jvm("common") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("reflect"))
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
