package app.polarmail.data.email


internal interface EmailClient {

    fun isConnected(): Boolean

    fun connect(authenticationType: AuthenticationType, host: String, port: Int)

    fun disconnect()

}