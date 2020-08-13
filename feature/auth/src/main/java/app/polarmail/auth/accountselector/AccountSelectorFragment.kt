package app.polarmail.auth.accountselector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import app.polarmail.auth.databinding.FragmentAccountSelectorBinding
import app.polarmail.core_ui.util.DividerModel
import app.polarmail.core_ui.util.RoundedBottomSheetDialog
import app.polarmail.core_ui.util.dividerModel
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class AccountSelectorFragment : RoundedBottomSheetDialog() {

    companion object {
        fun show(fragmentManager: FragmentManager) {
            AccountSelectorFragment().show(fragmentManager, null)
        }
    }

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
        onStates(viewModel) { state ->
            when (state) {
                is AccountSelectorViewState -> render(state)
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
                                // TODO
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
                                // TODO
                            }
                        }
                    }
                    is AccountSelectorItem.Settings -> {
                        dividerModel {
                            id("divider1")
                        }
                        accountSelectorSettings {
                            id("account_selector_settings")
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