plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10"
}

android {
    namespace = "com.ams"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ams"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        setProperty("archivesBaseName", "Ams")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.animation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //sdp
    implementation(libs.sdp.android)
    //Country code picker
    implementation(libs.ccp)
    // AWS database storage
    implementation(libs.aws.android.sdk.s3)
    // Animation
    implementation(libs.android.animations.kotlin)
    //json converter
    implementation(libs.converter.gson)
    //retrofit
    implementation(libs.retrofit)
    //interceptor
    implementation(libs.logging.interceptor)
    //Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // ✅ AndroidX Hilt Compiler (used for @HiltViewModel processing)
    kapt(libs.androidx.hilt.compiler)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.photoview)


    //ExoPlayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.flexbox)
    implementation(libs.animatablecompose)
    implementation(libs.lottie)

    // Jetpack Compose BOM
    implementation(platform(libs.androidx.compose.bom))

// Core Compose UI
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

// Material 3 (if you want Material styling)
    implementation(libs.androidx.material3)

// Animation
    implementation(libs.animation)

// ComposeView support in Fragments
    implementation(libs.androidx.activity.compose)

    // Accompanist Pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

// Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

// Lifecycle (ViewModel + livedata/StateFlow)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.libphonenumber)

}