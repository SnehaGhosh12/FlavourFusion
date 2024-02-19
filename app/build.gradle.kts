plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {


    namespace = "com.example.flavourfusion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flavourfusion"
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    val nav_version = "2.7.6"
    val lifecycle_version = "2.6.2"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation ("com.google.android.material:material:1.11.0-alpha01")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //intuit
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.intuit.ssp:ssp-android:1.1.0")

    //animation
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")

    //retrofit and gson
    implementation ("com.google.code.gson:gson:2.10")
    implementation ("com.squareup.retrofit2:retrofit:2.0.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.0.2")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    //ROOM
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

}