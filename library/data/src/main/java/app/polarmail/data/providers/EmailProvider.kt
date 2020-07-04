package app.polarmail.data.providers

interface EmailProvider {

    val id: String
    val name: String
    val imap: HostInfo
    val smtp: HostInfo
    val oauth2: OAuth2Info?

}