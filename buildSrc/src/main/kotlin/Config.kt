object Config {
    val compileVersion = 29
    val buildToolsVersion = "29.0.2"
    val minSdk = 21
    val targetSdk = 29
    val versionCode = 1
    val versionName = "0.1"
}

object Modules {
    val core = ":library:core"
    val coreUi = ":library:core-ui"
    val data = ":library:data"
    val dataAndroid = ":library:data-android"
    val domain = ":library:domain"
    val auth = ":feature:auth"
}