package app.polarmail.data.email

object EmailClientFactory {

    internal fun makeEmailService(isDebug: Boolean, emailProtocol: EmailProtocol): EmailClient =
        when (emailProtocol) {
            EmailProtocol.IMAP -> ImapEmailClient(isDebug)
            EmailProtocol.POP3 -> Pop3EmailClient(isDebug)
        }

}