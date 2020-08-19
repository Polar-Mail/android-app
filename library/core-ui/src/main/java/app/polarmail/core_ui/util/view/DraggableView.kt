package app.polarmail.core_ui.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import app.polarmail.core_ui.R

class DraggableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attributeSet, defStyleAttr) {

    init {
        background = ContextCompat.getDrawable(context, R.drawable.bg_draggable)
    }

}