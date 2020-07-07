package app.polarmail.data_android.emailproviders

import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.model.EmailProvider
import app.polarmail.domain.model.HostInfo
import app.polarmail.domain.model.OAuth2Info

class Office365AppEmailProvider : AppEmailProvider {
    override val name: String = "Office 365"
    override val icon: Int = app.polarmail.data_android.R.drawable.ic_email_provider_office_365

    override val emailProvider: EmailProvider = object : EmailProvider {
        override val id: String = "office-365"
        override val name: String = "Office 365"
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