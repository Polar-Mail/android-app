package app.polarmail.domain.model

data class HostInfo(
    val host: String,
    val port: Int,
    val startTls: Boolean = false
)