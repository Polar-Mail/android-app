package app.polarmail.data.email


class Pop3EmailClient(private val isDebug: Boolean) : EmailClient {

    override fun isConnected(): Boolean {
        TODO("Not yet implemented")
    }

    override fun connect(authenticationType: AuthenticationType, host: String, port: Int) {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

}