import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(GradlePlugins.kotlin)
}

dependencies {
    api(Deps.kotlin)
    api(Deps.coroutines)
    api(Deps.threeTen)
    api(Deps.hilt)
    implementation(Deps.coroutinesTest)
    implementation(Deps.truth)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}