package app.polarmail.data.email

interface EmailManager {

    suspend fun connect(
        accountId: Long,
        host: String,
        port: Int,
        protocol: EmailProtocol,
        authenticationType: AuthenticationType
    )

    suspend fun disconnect(accountId: Long)

}