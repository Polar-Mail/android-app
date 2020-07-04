package app.polarmail.data_android.emailproviders

import app.polarmail.data.providers.AppEmailProvider
import app.polarmail.data.providers.EmailProvider
import app.polarmail.core_ui.R

class GmailAppEmailProvider(override val emailProvider: EmailProvider) : AppEmailProvider {

    override val name: String = "Gmail"
    override val icon: Int = R.drawable.ic_email_provider_gmail

}