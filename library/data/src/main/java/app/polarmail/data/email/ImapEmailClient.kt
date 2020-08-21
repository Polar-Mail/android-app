package app.polarmail.data.email

import com.sun.mail.imap.IMAPStore
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Folder
import javax.mail.PasswordAuthentication
import javax.mail.Session

internal class ImapEmailClient(
    private val debug: Boolean
) : EmailClient {

    private var session: Session? = null
    private var store: IMAPStore? = null

    override fun isConnected(): Boolean = store?.isConnected ?: false

    override fun connect(
        authenticationType: AuthenticationType,
        host: String,
        port: Int
    ) {
        val properties = Properties().apply {
            put("mail.imap.host", host)
            put("mail.imap.port", port)
            put("mail.iamp.starttls.enable", "true") // TODO could be not secure
            put("mail.imap.ssl.enable", "true") // TODO could be not secure
            put("mail.imap.auth", "true")
            put("mail.iamp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            put("mail.iamp.socketFactory.fallback", "false")

            if (debug) {
                put("mail.debug.auth", "true")
            }

            when (authenticationType) {
                is AuthenticationType.OAuth2 -> {
                    put("mail.imap.auth.mechanisms", "XOAUTH2")
                }
                is AuthenticationType.CRAMMD5 -> {
                    put("mail.imap.sasl.mechanisms", "CRAM-MD5")
                    put("mail.smtp.sasl.enable", "true")
                    put("mail.imap.auth.mechanisms", "CRAM-MD5")
                }
            }
        }

        val mailAuth = getAuth(authenticationType)
        session = Session.getInstance(properties, mailAuth)?.also {
            it.debug = debug
            store = it.getStore("imaps") as IMAPStore // TODO could be not secure
        }
    }

    private fun getAuth(authenticationType: AuthenticationType): Authenticator =
        object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return when (authenticationType) {
                    is AuthenticationType.CRAMMD5 -> {
                        PasswordAuthentication(
                            authenticationType.username,
                            authenticationType.password
                        )
                    }
                    is AuthenticationType.OAuth2 -> {
                        PasswordAuthentication(
                            authenticationType.username,
                            authenticationType.accessToken
                        )
                    }
                }
            }
        }


    override fun disconnect() {
        store?.close()
        store = null
        session = null
    }

    override fun getFolders(): List<Folder> {
        return store?.defaultFolder?.list()?.toList() ?: emptyList()
    }

}