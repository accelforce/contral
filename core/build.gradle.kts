import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
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
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
            }
        }
    }
}
