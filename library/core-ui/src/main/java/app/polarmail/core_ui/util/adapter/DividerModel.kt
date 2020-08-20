package app.polarmail.core_ui.util.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import app.polarmail.core_ui.R
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class DividerModel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.item_divider, this)
    }

}