package app.polarmail.auth.accountselector

import android.view.View
import app.polarmail.auth.R
import app.polarmail.auth.databinding.ItemAccountBinding
import app.polarmail.core_ui.util.ViewBindingEpoxyModelWithHolder
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.bumptech.glide.Glide

@EpoxyModelClass
abstract class AccountSelectorModel : ViewBindingEpoxyModelWithHolder<ItemAccountBinding>() {

    @EpoxyAttribute
    lateinit var avatarUrl: String

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    @JvmField
    var isAccountSelected: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: () -> Unit

    override fun getDefaultLayout(): Int = R.layout.item_account

    override fun ItemAccountBinding.bind() {
        Glide.with(root)
            .load(avatarUrl)
            .circleCrop()
            .into(imageAvatar)

        textAccountName.text = name
        imageAccountSelected.visibility = if (isAccountSelected) View.VISIBLE else View.GONE
        root.setOnClickListener { clickListener.invoke() }
    }

}