import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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

        manifestPlaceholders["appAuthRedirectScheme"] = "app.polarmail"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias(gradleLocalProperties(rootDir).getProperty("KEY_ALIAS"))
            keyPassword(gradleLocalProperties(rootDir).getProperty("KEY_PASSWORD"))
            storeFile(file("/Users/diego/AndroidStudioProjects/polarmail.keystore"))
            storePassword(gradleLocalProperties(rootDir).getProperty("STORE_PASSWORD"))
        }
        create("release") {
            keyAlias(gradleLocalProperties(rootDir).getProperty("KEY_ALIAS"))
            keyPassword(gradleLocalProperties(rootDir).getProperty("KEY_PASSWORD"))
            storeFile(file("/Users/diego/AndroidStudioProjects/polarmail.keystore"))
            storePassword(gradleLocalProperties(rootDir).getProperty("STORE_PASSWORD"))
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
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.domain))
    implementation(project(Modules.dataAndroid))
    implementation(project(Modules.auth))
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
    implementation(Deps.hiltViewModel)
    kapt(Deps.hiltJetpackCompiler)
    implementation(Deps.roomRuntinme)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)
    implementation(Deps.glide)
    kapt(Deps.glideCompiler)

    testImplementation(Deps.uniflowTest)
    testImplementation(Deps.uniflowAndroidTest)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.navigationTest)
    testImplementation(Deps.flowTest)
    testImplementation(Deps.truth)
    testImplementation(Deps.test)
    testImplementation(Deps.mockk)
}
