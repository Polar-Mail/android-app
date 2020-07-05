package app.polarmail.data_android.emailproviders

import app.polarmail.data.providers.GmailEmailProvider
import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.repository.EmailProviderRepository

class EmailProviderRepositoryImpl : EmailProviderRepository {

    private val gmailProvider by lazy { GmailEmailProvider() }

    private val appProviders: Map<String, AppEmailProvider> by lazy {
        mapOf("gmail" to GmailAppEmailProvider(gmailProvider))
    }

    override fun getEmailProviders(): List<AppEmailProvider> = appProviders.values.toList()

    override fun getEmailProviderById(id: String): AppEmailProvider? = appProviders[id]

}