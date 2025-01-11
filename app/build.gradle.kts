plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.nextone.datandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nextone.datandroid"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.gridlayout)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    compileOnly (libs.lombok);
    annotationProcessor (libs.lombok);
    implementation ("com.github.mik3y:usb-serial-for-android:3.8.1");
    implementation (libs.jackson.databind)
    implementation (libs.modelmapper)
    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.camera.extensions)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.gson)
    implementation ("com.google.android.material:material:1.9.0")
}