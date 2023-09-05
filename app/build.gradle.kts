plugins {
    id ("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace  = "com.example.screen_lake"
    compileSdk = 34

    defaultConfig {
        applicationId ="com.example.screen_lake"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.screen_lake.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled =  false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packagingOptions {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {


    coreLibraryDesugaring (libs.desugar.jdk.libs)

    implementation (libs.core.ktx)
    implementation (libs.lifecycle.runtime.ktx)
    implementation (libs.activity.compose)
    implementation (libs.ui)
    implementation (libs.ui.tooling.preview)
    implementation (libs.foundation)
    implementation(libs.material3)
    implementation("androidx.compose.material3:material3-window-size-class:1.2.0-alpha06")
    implementation(libs.androidx.material)

    // testing
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
    androidTestImplementation (libs.androidx.ui.test.junit4)
    debugImplementation (libs.androidx.ui.tooling)
    debugImplementation (libs.androidx.ui.test.manifest)
    testImplementation (libs.truth)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.androidx.core.testing)

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    // Compose dependencies
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.navigation.compose)
    implementation ("androidx.compose.material:material-icons-extended")



    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.hilt.navigation.compose)

    // For Robolectric tests.
    testImplementation (libs.hilt.android.testing)
    // ...with Kotlin.
    kaptTest (libs.hilt.android.compiler)
    // ...with Java.
    testAnnotationProcessor (libs.hilt.android.compiler)


    // For instrumented tests.
    androidTestImplementation (libs.hilt.android.testing)
    // ...with Kotlin.
    kaptAndroidTest (libs.hilt.android.compiler)
    // ...with Java.
    androidTestAnnotationProcessor (libs.hilt.android.compiler)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.runtime.livedata)
    kapt(libs.androidx.room.compiler)
    annotationProcessor( libs.androidx.room.compiler)

    // constraint layout
    implementation (libs.androidx.constraintlayout.compose)

    // Glide
    implementation (libs.landscapist.glide)

    // GSON
    api(libs.gson)

//     The compose calendar library
    implementation (libs.compose)

// number picker
    implementation(libs.numberpicker)
}