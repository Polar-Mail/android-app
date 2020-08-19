package app.polarmail.auth.accountselector

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import app.polarmail.auth.AuthActivity
import app.polarmail.auth.databinding.FragmentAccountSelectorBinding
import app.polarmail.core.util.AccountId
import app.polarmail.core_ui.util.adapter.dividerModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class AccountSelectorFragment : BottomSheetDialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager, callback: () -> Unit) {
            AccountSelectorFragment().apply {
                listener = callback
            }.show(fragmentManager, null)
        }
    }

    var listener: (() -> Unit)? = null

    val viewModel: AccountSelectorViewModel by viewModels()

    private lateinit var binding: FragmentAccountSelectorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindState()
        bindEvents()
    }

    private fun bindState() {
        onStates(viewModel) { state ->
            when (state) {
                is AccountSelectorViewState -> render(state)
            }
        }
    }

    private fun bindEvents() {
        onEvents(viewModel) {
            when (val event = it.take()) {
                is AccountSelectorEvents.OpenAddAccount -> onOpenAddAccount()
            }
        }
    }

    private fun render(state: AccountSelectorViewState) {
        binding.epoxyAccounts.withModels {
            state.items.forEach { item ->
                when (item) {
                    is AccountSelectorItem.AddAccount -> {
                        accountSelectorAdd {
                            id("account_selector_add")
                            clickListener {
                                viewModel.openAddAccount()
                            }
                        }
                    }
                    is AccountSelectorItem.Account -> {
                        accountSelector {
                            id(item.id)
                            avatarUrl(item.avatar)
                            name(item.name)
                            isAccountSelected(item.isSelected)
                            clickListener {
                                viewModel.selectAccount(item.id)
                            }
                        }
                    }
                    is AccountSelectorItem.Settings -> {
                        dividerModel {
                            id("account-selector-divider1")
                        }
                        accountSelectorSettings {
                            id("account-selector-settings")
                            clickListener {
                                openSettings()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onOpenAddAccount() {
        val intent = Intent(requireActivity(), AuthActivity::class.java).apply {
            putExtra(AuthActivity.ARG_IS_FRESH, false)
        }
        startActivity(intent)
    }

    private fun openSettings() {
        listener?.invoke()
    }

}