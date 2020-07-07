plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Config.compileVersion)
    buildToolsVersion(Config.buildToolsVersion)
    defaultConfig {
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "GOOGLE_CLIENT_ID",
            com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("GOOGLE_CLIENT_ID")
        )
    }
}

dependencies {
    api(project(Modules.data))
    implementation(project(Modules.coreUi))
    implementation(Deps.roomRuntinme)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)
}
