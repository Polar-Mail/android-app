package app.polarmail.settings.accounts.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.polarmail.settings.R
import app.polarmail.settings.databinding.FragmentSettingsAccountDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class AccountDetailSettingsFragment : Fragment(R.layout.fragment_settings_account_detail) {

    private lateinit var binding: FragmentSettingsAccountDetailBinding

    private val viewModel: AccountDetailSettingsViewModel by viewModels()
    private val args: AccountDetailSettingsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsAccountDetailBinding.bind(view)
        bindState()
        bindActions()
        bindEvents()
        val id = args.accountId
        viewModel.load(id)
    }

    private fun bindState() {
        onStates(viewModel) { state ->
            when (state) {
                is AccountDetailViewState -> render(state)
            }
        }
    }

    private fun bindActions() {
        binding.buttonRemoveAccount.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun bindEvents() {
        onEvents(viewModel) {
            when (val event = it.take()) {
                is AccountDetailSettingsEvents.Logout -> onLogout()
            }
        }
    }

    private fun render(state: AccountDetailViewState) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = state.account?.username
    }

    private fun onLogout() {
        findNavController().navigateUp()
    }

}