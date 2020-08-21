package app.polarmail.data.email

import app.polarmail.core.util.AccountId
import app.polarmail.domain.repository.AccountRepository
import app.polarmail.domain.repository.FolderRepository

class LocalEmailManager constructor(
    private val isDebug: Boolean,
    private val accountRepository: AccountRepository,
    private val folderRepository: FolderRepository
) : EmailManager {

    private val accounts: MutableMap<AccountId, EmailClient> = mutableMapOf()

    override suspend fun connect(accountId: AccountId) {
        if (accounts.containsKey(accountId) && accounts[accountId]?.isConnected() == true) {
            return
        }

        val account = accountRepository.getById(accountId)
        requireNotNull(account)

        val authType = AuthenticationType.OAuth2(account.username, account.password)
        val emailClient = EmailClientFactory.makeEmailService(
            isDebug,
            EmailProtocol.IMAP // TODO forced to use imap for now
        )

        emailClient.connect(authType, account.host, account.port)
        accounts[accountId] = emailClient
    }

    override suspend fun disconnect(accountId: AccountId) {
        accounts[accountId]?.disconnect()
    }

    override suspend fun sync(accountId: AccountId) {
        accounts[accountId]?.getFolders()?.forEach { folder ->
            val folderId = folderRepository.addFolder(
                folder.name,
                accountId,
                folder.unreadMessageCount
            )
        }
    }

}