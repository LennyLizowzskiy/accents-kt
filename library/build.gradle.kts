plugins {
    id("maven-publish")
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

val javaVer = JavaVersion.VERSION_17

android {
    compileSdkVersion = "android-31"
    namespace = "com.lizowzskiy.accents"
    defaultConfig {
        minSdk = 23
    }
    compileOptions {
        sourceCompatibility = javaVer
        targetCompatibility = javaVer
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvmToolchain("$javaVer".toInt())

    jvm()

    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "$javaVer"
            }
        }
    }

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.apache.commonLang)
            }
        }
    }
}