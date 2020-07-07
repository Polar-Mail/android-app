package app.polarmail.data.maper

import app.polarmail.core.util.AccountId
import app.polarmail.data.entitiy.AccountEntity
import app.polarmail.domain.model.Account

fun Account.toAccountEntity(): AccountEntity =
    AccountEntity(
        id.id,
        lastConnected,
        username,
        password,
        host,
        port,
        avatar,
        isSelected
    )

fun AccountEntity.toAccount(): Account =
    Account(
        AccountId(id),
        lastConnected,
        username,
        password,
        host,
        port,
        avatar,
        isSelected
    )