package app.polarmail.settings.accounts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.polarmail.settings.R
import app.polarmail.settings.databinding.FragmentSettingsAccountsBinding
import dagger.hilt.android.AndroidEntryPoint
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
                                val directions =
                                    AccountSettingsFragmentDirections.actionAccountSettingsFragmentToAccountDetailSettingsFragment(
                                        item.account.id.id
                                    )
                                findNavController().navigate(directions)
                            }
                        }
                    }
                    is AccountSettingsItem.AddAccount -> {
                        addAccountButton {
                            id("account-settings-add")
                            clickListener {
                                // TODO
                            }
                        }
                    }
                }

            }
        }
    }

}