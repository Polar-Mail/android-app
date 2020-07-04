package app.polarmail.data.providers

data class HostInfo(
    val host: String,
    val port: Int,
    val startTls: Boolean = false
)