
plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
    id(GradlePlugins.kotlinAndroidExtensions)
    kotlin("kapt")
    id(GradlePlugins.hilt)
    id("kotlin-android")
}

android {
    compileSdkVersion(Config.compileVersion)
    buildToolsVersion(Config.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = 1
        manifestPlaceholders["appAuthRedirectScheme"] = "app.polarmail"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (this as ExtensionAware).configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions> {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(Modules.coreUi))
    implementation(project(Modules.dataAndroid))
    implementation(Deps.constraintLayout)
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
    implementation(Deps.hiltViewModel)
    kapt(Deps.hiltJetpackCompiler)
    kapt(Deps.epoxyCompiler)
    implementation(Deps.glide)
    kapt(Deps.glideCompiler)

    testImplementation(Deps.junit)
    testImplementation(Deps.truth)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.uniflowTest)
    testImplementation(Deps.uniflowAndroidTest)
    testImplementation(Deps.mockk)
    testImplementation(Deps.test)
    testImplementation(Deps.testRunner)
}
