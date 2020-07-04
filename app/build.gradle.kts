plugins {
    id(GradlePlugins.android)
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id(GradlePlugins.hilt)
}

android {
    compileSdkVersion(Config.compileVersion)
    buildToolsVersion(Config.buildToolsVersion)
    defaultConfig {
        applicationId = "app.polarmail"
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.domain))
    implementation(project(Modules.dataAndroid))
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
    implementation(Deps.hiltViewModel)
    kapt(Deps.hiltJetpackCompiler)

    testImplementation(Deps.uniflowTest)
    testImplementation(Deps.uniflowAndroidTest)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.navigationTest)
    testImplementation(Deps.flowTest)
    testImplementation(Deps.truth)
    testImplementation(Deps.test)
    testImplementation(Deps.mockk)
}
