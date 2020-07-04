package app.polarmail.data.providers

import app.polarmail.data.providers.EmailProvider

interface AppEmailProvider {

    val name: String
    val icon: Int
    val emailProvider: EmailProvider

}