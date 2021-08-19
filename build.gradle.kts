import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        val ktVersion = "1.5.21"
        classpath(kotlin("gradle-plugin", ktVersion))
        classpath(kotlin("serialization", ktVersion))
        classpath("com.android.tools.build:gradle:7.0.0")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.17.1")
    }
}

group = "net.accelf.contral"
version = "0.0.0"

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        google()
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")

    dependencies {
        add("detektPlugins", "io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
    }

    afterEvaluate {
        project.extensions.findByType<KotlinMultiplatformExtension>()?.let {
            it.sourceSets.removeAll { sourceSet -> sourceSet.name == "androidAndroidTestRelease" }
        }
    }

    extensions.configure(DetektExtension::class.java) {
        config = files("${rootProject.rootDir}/detekt.yml")
        buildUponDefaultConfig = true

        input = files(input, "src/commonMain/kotlin", "src/commonTest/kotlin")

        reports {
            sarif {
                destination = File("${rootProject.buildDir}/reports/detekt/$name.sarif")
            }
        }
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "1.8"
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
