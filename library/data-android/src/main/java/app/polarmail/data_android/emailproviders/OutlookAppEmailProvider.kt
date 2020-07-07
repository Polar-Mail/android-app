package app.polarmail.data_android.emailproviders

import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.model.EmailProvider
import app.polarmail.domain.model.HostInfo
import app.polarmail.domain.model.OAuth2Info

class OutlookAppEmailProvider : AppEmailProvider {
    override val name: String = "Outlook"
    override val icon: Int = app.polarmail.data_android.R.drawable.ic_email_provider_outlook

    override val emailProvider: EmailProvider = object : EmailProvider {
        override val id: String = "outlook"
        override val name: String = "Outlook"
        override val imap: HostInfo = HostInfo("", 0)
        override val smtp: HostInfo = HostInfo("", 0)
        override val oauth2: OAuth2Info? = OAuth2Info(
            "",
            "",
            "",
            "",
            emptyList(),
            ""
        )
    }
}