package app.polarmail.domain.manager

import app.polarmail.core.util.AccountId

sealed class AddAccountResult {

        data class Success(val id: AccountId) : AddAccountResult()

        object Failure : AddAccountResult()

    }
