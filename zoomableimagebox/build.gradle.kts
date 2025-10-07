plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.compose.compiler.get().pluginId)
    id("maven-publish")
}

android {
    namespace = "com.lukelorusso.zoomableimagebox"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }

        testBuildType = "debug"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }

    buildFeatures {
        compose = true
    }

    publishing {
        singleVariant("release") {
            //withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.bundles.lib)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.lukelorusso"
            artifactId = "ZoomableImageBox"
            version = libs.versions.android.versionName.get()

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
