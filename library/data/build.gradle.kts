plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
}

dependencies {
    implementation(project(Modules.core))
    api(project(Modules.domain))
    implementation(Deps.kotlin)
    implementation(Deps.coroutines)
    implementation(Deps.roomRuntinme)
    implementation(Deps.roomRuntinme)
    implementation(Deps.jakarta)
    kapt(Deps.roomCompiler)

    testImplementation(Deps.junit)
    testImplementation(Deps.mockk)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.truth)
    testImplementation(Deps.flowTest)
}

