package app.polarmail.settings.accounts

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.polarmail.core.util.AccountId
import app.polarmail.core_ui.util.NavigationLink
import app.polarmail.core_ui.util.NavigationLink.authIntent
import app.polarmail.settings.R
import app.polarmail.settings.databinding.FragmentSettingsAccountsBinding
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class AccountSettingsFragment : Fragment(R.layout.fragment_settings_accounts) {

    val viewModel: AccountSettingsViewModel by viewModels()

    private lateinit var binding: FragmentSettingsAccountsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsAccountsBinding.bind(view)
        bindState()
        bindEvents()
    }

    private fun bindState() {
        onStates(viewModel) { state ->
            when (state) {
                is AccountSettingsState -> render(state)
            }
        }
    }

    private fun bindEvents() {
        onEvents(viewModel) {
            when (val event = it.take()) {
                is AccountSettingsEvents.OpenAddAccount -> onAddAccount()
                is AccountSettingsEvents.OpenAccount -> onNavigateToAccount(event.id)
            }
        }
    }

    private fun onAddAccount() {
        val intent = authIntent()
        startActivity(intent)
    }

    private fun onNavigateToAccount(id: AccountId) {
        val directions =
            AccountSettingsFragmentDirections.actionAccountSettingsFragmentToAccountDetailSettingsFragment(
                id.id
            )
        findNavController().navigate(directions)
    }

    private fun render(state: AccountSettingsState) {
        binding.epoxySettingAccounts.withModels {
            state.accounts.forEach { item ->
                when (item) {
                    is AccountSettingsItem.AccountItem -> {
                        settingsAccount {
                            id(item.account.id.toString())
                            name(item.account.username)
                            avatarUrl(item.account.avatar)
                            clickListener {
                                viewModel.goToAccount(item.account.id)
                            }
                        }
                    }
                    is AccountSettingsItem.AddAccount -> {
                        addAccountButton {
                            id("account-settings-add")
                            clickListener {
                                viewModel.goToAddAccount()
                            }
                        }
                    }
                }

            }
        }
    }

}