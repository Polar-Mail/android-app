package app.polarmail.data.email

import javax.mail.Folder


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

    override fun getFolders(): List<Folder> {
        TODO("Not yet implemented")
    }

}