plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.compose.compiler.get().pluginId)
}

android {
    namespace = "com.lukelorusso.zoomableimagebox.example"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    buildFeatures {
        buildConfig = true
        compose = true
    }

    defaultConfig {
        applicationId = "com.lukelorusso.zoomableimagebox.example"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.compileSdk.get().toInt()
        versionCode = 1
        versionName = libs.versions.android.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val enableAnalyticsTag = "ENABLE_ANALYTICS"

        debug {
            versionNameSuffix = ".debug"
            isMinifyEnabled = false
            buildConfigField("Boolean", enableAnalyticsTag, "false")
        }

        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
            buildConfigField("Boolean", enableAnalyticsTag, "true")
            signingConfig = signingConfigs.getByName("debug")
        }

        testBuildType = "debug"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
}

dependencies {
    implementation(project(":zoomableimagebox"))
    implementation(libs.bundles.example)
}
