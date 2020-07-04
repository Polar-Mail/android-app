package app.polarmail.data.providers

class GmailEmailProvider : EmailProvider {

    override val id: String = "gmail"
    override val name: String = "Gmail"

    override val imap: HostInfo = HostInfo("imap.gmail.com", 993, false)

    override val smtp: HostInfo = HostInfo("smtp.gmail.com", 465, false)

    override val oauth2: OAuth2Info = OAuth2Info(
        "",
        System.getenv("POLAR_MAIL_GOOGLE_CLIENT_ID"),
        "",
        "",
        emptyList(),
        ""
    )

}