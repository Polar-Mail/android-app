package app.polarmail.domain.model

interface AppEmailProvider {

    val name: String
    val icon: Int
    val emailProvider: EmailProvider

}