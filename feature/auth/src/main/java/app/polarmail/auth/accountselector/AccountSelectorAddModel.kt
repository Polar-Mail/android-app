package app.polarmail.auth.accountselector

import app.polarmail.auth.R
import app.polarmail.auth.databinding.ItemAccountSelectorAddBinding
import app.polarmail.core_ui.util.ViewBindingEpoxyModelWithHolder
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass

@EpoxyModelClass
abstract class AccountSelectorAddModel
    : ViewBindingEpoxyModelWithHolder<ItemAccountSelectorAddBinding>() {

    override fun getDefaultLayout(): Int = R.layout.item_account_selector_add

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) lateinit var clickListener: () -> Unit

    override fun ItemAccountSelectorAddBinding.bind() {
        root.setOnClickListener { clickListener.invoke() }
    }

}