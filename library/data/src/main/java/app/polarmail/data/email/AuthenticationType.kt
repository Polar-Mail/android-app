package app.polarmail.data.email

sealed class AuthenticationType {

    data class CRAMMD5(
        val username: String,
        val password: String
    ) : AuthenticationType()

    data class OAuth2(
        val username: String,
        val accessToken: String
    ) : AuthenticationType()

}