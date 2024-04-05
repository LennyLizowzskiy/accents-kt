plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
//    targetHierarchy.default()

    jvm()

    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

//    linuxX64()

//    macosX64()
//    macosArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.apache.commonLang)
            }
        }

        val androidMain by getting {

        }
    }
}

android {
    namespace = "com.lizowzskiy.accents"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
