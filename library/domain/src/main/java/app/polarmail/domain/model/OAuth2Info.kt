package app.polarmail.domain.model

data class OAuth2Info(
    val authorizationUrl: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val scopes: List<String>,
    val tokenEndpoint: String
)