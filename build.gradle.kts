// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories{
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
    dependencies{
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath(libs.firebase.crashlytics.gradle)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.57.1" apply false
}