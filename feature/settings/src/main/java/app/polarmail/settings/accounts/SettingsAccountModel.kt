package app.polarmail.settings.accounts

import app.polarmail.core_ui.util.ViewBindingEpoxyModelWithHolder
import app.polarmail.settings.R
import app.polarmail.settings.databinding.ItemSettingsAccountItemBinding
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.bumptech.glide.Glide

@EpoxyModelClass
abstract class SettingsAccountModel : ViewBindingEpoxyModelWithHolder<ItemSettingsAccountItemBinding>() {

    @EpoxyAttribute
    lateinit var avatarUrl: String

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: () -> Unit

    override fun getDefaultLayout(): Int = R.layout.item_settings_account_item

    override fun ItemSettingsAccountItemBinding.bind() {
        Glide.with(root)
            .load(avatarUrl)
            .circleCrop()
            .into(imageAvatar)

        textAccountName.text = name
        imageOpenAccount.setOnClickListener { clickListener.invoke() }
        root.setOnClickListener { clickListener.invoke() }
    }

}