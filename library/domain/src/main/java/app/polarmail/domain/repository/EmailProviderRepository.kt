package app.polarmail.domain.repository

import app.polarmail.domain.model.AppEmailProvider

interface EmailProviderRepository {

    fun getEmailProviders(): List<AppEmailProvider>
    fun getEmailProviderById(id: String): AppEmailProvider?

}