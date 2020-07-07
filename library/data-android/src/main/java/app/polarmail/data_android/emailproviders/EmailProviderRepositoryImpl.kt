package app.polarmail.data_android.emailproviders

import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.repository.EmailProviderRepository

class EmailProviderRepositoryImpl : EmailProviderRepository {

    private val appProviders: Map<String, AppEmailProvider> by lazy {
        mapOf(
            "gmail" to GmailAppEmailProvider(),
            "yahoo" to YahooAppEmailProvider(),
            "outlook" to OutlookAppEmailProvider(),
            "office-365" to Office365AppEmailProvider(),
            "icloud" to ICloudAppEmailProvider()
        )
    }

    override fun getEmailProviders(): List<AppEmailProvider> = appProviders.values.toList()

    override fun getEmailProviderById(id: String): AppEmailProvider? = appProviders[id]

}