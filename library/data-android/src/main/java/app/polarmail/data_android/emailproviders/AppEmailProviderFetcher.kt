package app.polarmail.data_android.emailproviders

import app.polarmail.data.providers.AppEmailProvider
import app.polarmail.data.providers.GmailEmailProvider
import java.lang.IllegalStateException

object AppEmailProviderFetcher {

    private val gmailProvider by lazy { GmailEmailProvider() }

    private val appProviders: Map<String, AppEmailProvider> by lazy {
        mapOf("gmail" to GmailAppEmailProvider(gmailProvider))
    }

    fun fetchAll(): List<AppEmailProvider> = appProviders.values.toList()

    fun fetch(id: String): AppEmailProvider =
        appProviders[id] ?: throw IllegalStateException("Email provider not found")

}