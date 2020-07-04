package app.polarmail.data.email

class LocalEmailManager(private val isDebug: Boolean) : EmailManager {

    private val accounts: MutableMap<Long, EmailClient> = mutableMapOf()

    override suspend fun connect(
        accountId: Long,
        host: String,
        port: Int,
        protocol: EmailProtocol,
        authenticationType: AuthenticationType
    ) {
        if (accounts.containsKey(accountId) && accounts[accountId]?.isConnected() == true) {
            return
        }

        val emailClient = EmailClientFactory.makeEmailService(isDebug, protocol)
        emailClient.connect(authenticationType, host, port)
        accounts[accountId] = emailClient
    }


    override suspend fun disconnect(accountId: Long) {
        accounts[accountId]?.disconnect()
    }

}