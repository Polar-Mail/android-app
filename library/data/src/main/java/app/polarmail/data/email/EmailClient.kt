package app.polarmail.data.email

import javax.mail.Folder


internal interface EmailClient {

    fun isConnected(): Boolean

    fun connect(authenticationType: AuthenticationType, host: String, port: Int)

    fun disconnect()

    fun getFolders(): List<Folder>

}