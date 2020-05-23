object Versions {
    val kotlin = "1.3.71"
    val coroutines = "1.3.3"
    val koin = "2.1.5"

    val ktx = "1.2.0"
    val material = "1.2.0-alpha05"
    val appCompat = "1.1.0"
    val constraintLayout = "1.1.3"
    val lifecycle = "2.2.0"
    val fragment = "1.3.0-alpha04"
    val navigation = "2.3.0-alpha06"
    val uniflow = "0.11.0"
    val threeTen = "1.2.4"
    val mockk = "1.10.0"
    val permissionsDispatcher = "4.7.0"

    val flowPreferences = "1.1.1"
    val kaspresso = "1.1.0"
}


object Deps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val material = "com.google.android.material:material:${Versions.material}"
    val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    val uniflow = "io.uniflow:uniflow-android:${Versions.uniflow}"
    val threeTen = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTen}"
    val junit = "junit:junit:4.13"
    val koinTest = "org.koin:koin-test:${Versions.koin}"
    val uniflowTest = "io.uniflow:uniflow-android-test:${Versions.uniflow}"
    val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val junitInstrumentation = "androidx.test.ext:junit:1.1.1"
    val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    val kaspresso = "com.kaspersky.android-components:kaspresso:${Versions.kaspresso}"
    val navigationTest = "androidx.navigation:navigation-testing:${Versions.navigation}"
}