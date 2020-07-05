package app.polarmail.auth

import androidx.annotation.DrawableRes
import app.polarmail.auth.databinding.ItemEmailProviderBinding
import app.polarmail.core_ui.util.ViewBindingEpoxyModelWithHolder
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass

@EpoxyModelClass
abstract class EmailProviderModel : ViewBindingEpoxyModelWithHolder<ItemEmailProviderBinding>() {

    @EpoxyAttribute @DrawableRes var icon: Int = 0
    @EpoxyAttribute lateinit var name: String
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash) lateinit var clickListener: () -> Unit

    override fun getDefaultLayout(): Int = R.layout.item_email_provider

    override fun ItemEmailProviderBinding.bind() {
        imageEmailProviderIcon.setImageResource(icon)
        textEmailProviderName.text = name
        root.setOnClickListener { clickListener.invoke() }
    }

}