package app.polarmail.auth.accountselector

import app.polarmail.auth.R
import app.polarmail.auth.databinding.ItemAccountSelectorSettingsBinding
import app.polarmail.core_ui.util.ViewBindingEpoxyModelWithHolder
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass

@EpoxyModelClass
abstract class AccountSelectorSettingsModel
    : ViewBindingEpoxyModelWithHolder<ItemAccountSelectorSettingsBinding>() {

    override fun getDefaultLayout(): Int = R.layout.item_account_selector_settings

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) lateinit var clickListener: () -> Unit

    override fun ItemAccountSelectorSettingsBinding.bind() {
        root.setOnClickListener { clickListener.invoke() }
    }

}