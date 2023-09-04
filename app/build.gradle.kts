plugins {
    id ("com.android.application")
    kotlin("android")
    kotlin("kapt")
//    id("com.google.devtools.ksp")
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


    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.3")

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.2")
    implementation ("androidx.compose.ui:ui:1.6.0-beta04")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.0-beta04")
    implementation ("androidx.compose.foundation:foundation:1.6.0-beta04")
    implementation("androidx.compose.material3:material3:1.2.0-alpha06")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.0-alpha06")
    implementation("androidx.compose.material:material:1.5.0")

    // testing
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.0")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.5.0")
    testImplementation ("com.google.truth:truth:1.0.1")
    androidTestImplementation ("com.google.truth:truth:1.0.1")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    // Compose dependencies
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.navigation:navigation-compose:2.7.1")
    implementation ("androidx.compose.material:material-icons-extended")



    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    // For Robolectric tests.
    testImplementation ("com.google.dagger:hilt-android-testing:2.47")
    // ...with Kotlin.
    kaptTest ("com.google.dagger:hilt-android-compiler:2.47")
    // ...with Java.
    testAnnotationProcessor ("com.google.dagger:hilt-android-compiler:2.47")


    // For instrumented tests.
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.47")
    // ...with Kotlin.
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.47")
    // ...with Java.
    androidTestAnnotationProcessor ("com.google.dagger:hilt-android-compiler:2.47")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha07")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha07")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.5.0-alpha07")

    // Room
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    kapt("androidx.room:room-compiler:2.5.1")
    annotationProcessor( "androidx.room:room-compiler:2.5.1")

    // constraint layout
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Glide
    implementation ("com.github.skydoves:landscapist-glide:1.5.2")

    // GSON
    api("com.google.code.gson:gson:2.10")

//     The compose calendar library
    implementation ("com.kizitonwose.calendar:compose:2.4.0-beta01")

// number picker
    implementation("com.chargemap.compose:numberpicker:1.0.3")
}