plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version "0.5.0-build227"
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "net.accelf.contral.android"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "0.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-rc01"
    }
}

dependencies {
    implementation(project(":core"))
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("com.arkivanov.decompose:decompose:0.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
