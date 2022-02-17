import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("me.tadej.versioning")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "no.shortcut.androidtemplate"
        minSdk = 23
        targetSdk = 31
        versionCode = versioning.versionCode()
        versionName = versioning.versionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val hasReleaseSigningConfig = rootProject.file("keys/release/signing.properties").isFile

    signingConfigs {
        if (hasReleaseSigningConfig) {
            val propsFile = rootProject.file("keys/release/signing.properties")
            val props = Properties().apply {
                load(propsFile.reader())
            }
            create("release") {
                storeFile = rootProject.file(props.getProperty("storeFile"))
                storePassword = props.getProperty("storePassword")
                keyPassword = props.getProperty("keyPassword")
                keyAlias = props.getProperty("keyAlias")
            }
        }
    }

    buildTypes {
        if (hasReleaseSigningConfig) {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    lint {
        textReport = true
        lintConfig = rootProject.file("lint.xml")
        isCheckDependencies = true
        isCheckTestSources = true
        isExplainIssues = true
        isCheckReleaseBuilds = true
        isAbortOnError = true
        disable(
            "UnusedIds", // Can give false positives
            "StaticFieldLeak", //  So you can inject app context in viewModels without a warning
            "UnusedResources", // Can give false positives as it does not always recognize resources used in databindings
            "ObsoleteLintCustomCheck" // false positives
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}



dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.31")

    // AndroidX
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.core:core-splashscreen:1.0.0-alpha02")

    // Material Design
    implementation("com.google.android.material:material:1.4.0")

    // ConstraintLayout / MotionLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")

    // Datastore Preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ViewModel
    val lifecycleVersion = "2.4.0-rc01"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    // Navigation
    val navigationVersion = "2.3.5"
    implementation("androidx.navigation:navigation-common-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-runtime-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Coroutines
    val coroutinesVersion = "1.5.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:28.4.0"))
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-crashlytics")

    // Data binding for ViewPager and RecyclerView
    implementation("me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:4.0.0")
    implementation("me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-viewpager2:4.0.0")

    // Shortcut made data-binding adapters
    implementation("com.github.shortcut:data-binding:1.0.0")

    // Koin
    val koinVersion = "3.1.2"
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")

    // Unit Testing
    testImplementation("junit:junit:4.13.2")

    // UI testing
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")


    implementation ("com.google.android.play:core-ktx:1.8.1")
    // Added to fix silent exception thrown ('Exception thrown while unbinding: Service not registered: com.google.android.gms.measurement...')
    // Update: June 2021, seems this also fixes extreme logging spam.
    // https://stackoverflow.com/a/64248713
    implementation("com.google.android.gms:play-services-basement:17.6.0")
}
