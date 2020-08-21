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

        val googleKey = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("GOOGLE_CLIENT_ID")

        buildConfigField(
            "String",
            "GOOGLE_CLIENT_ID",
            "\"$googleKey\""
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (this as ExtensionAware).configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions> {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(Modules.data))
    implementation(project(Modules.coreUi))
    implementation(Deps.roomRuntinme)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)
    implementation(Deps.hilt)
    implementation(Deps.hiltWorker)
    implementation(Deps.workManager)
    kapt(Deps.hiltCompiler)

    androidTestImplementation(Deps.test)
    androidTestImplementation(Deps.coroutinesTest)
    androidTestImplementation(Deps.testRunner)
    androidTestImplementation(Deps.testCore)
    androidTestImplementation(Deps.truth)
}
