package app.polarmail.data_android.emailproviders

import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.model.EmailProvider
import app.polarmail.core_ui.R
import app.polarmail.data_android.BuildConfig
import app.polarmail.domain.model.HostInfo
import app.polarmail.domain.model.OAuth2Info

class GmailAppEmailProvider :
    AppEmailProvider {

    override val name: String = "Gmail"
    override val icon: Int = R.drawable.ic_email_provider_gmail
    override val emailProvider: EmailProvider = object : EmailProvider {
        override val id: String = "gmail"
        override val name: String = "Gmail"

        override val imap: HostInfo =
            HostInfo("imap.gmail.com", 993, false)

        override val smtp: HostInfo =
            HostInfo("smtp.gmail.com", 465, false)

        override val oauth2: OAuth2Info =
            OAuth2Info(
                "https://accounts.google.com/o/oauth2/v2/auth",
                BuildConfig.GOOGLE_CLIENT_ID,
                "",
                AuthConstant.REDIRECT_URI,
                listOf(
                    "https://mail.google.com/"
                ),
                "https://www.googleapis.com/oauth2/v4/token"
            )
    }

}