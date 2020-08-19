package app.polarmail.core_ui.util.adapter

import app.polarmail.core_ui.R
import app.polarmail.core_ui.databinding.ItemButtonModelBinding
import app.polarmail.core_ui.util.ViewBindingEpoxyModelWithHolder
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass

@EpoxyModelClass
abstract class ButtonModel : ViewBindingEpoxyModelWithHolder<ItemButtonModelBinding>() {

    @EpoxyAttribute
    lateinit var text: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: () -> Unit

    override fun getDefaultLayout(): Int = R.layout.item_button_model

    override fun ItemButtonModelBinding.bind() {
        buttonModel.text = text
        root.setOnClickListener { clickListener.invoke() }
    }

}