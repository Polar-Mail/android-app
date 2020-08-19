package app.polarmail.settings.accounts

import app.polarmail.core_ui.util.ViewBindingEpoxyModelWithHolder
import app.polarmail.settings.R
import app.polarmail.settings.databinding.ItemSettingAccountAddBinding
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass

@EpoxyModelClass
abstract class AddAccountButtonModel :
    ViewBindingEpoxyModelWithHolder<ItemSettingAccountAddBinding>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: () -> Unit

    override fun getDefaultLayout(): Int = R.layout.item_setting_account_add

    override fun ItemSettingAccountAddBinding.bind() {
        root.setOnClickListener { clickListener.invoke() }
    }

}