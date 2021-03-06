pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

rootProject.name = "contral"

include(":android")
include(":config")
include(":core")
include(":desktop")
include(":mastodon")
