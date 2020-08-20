package app.polarmail.home

import io.uniflow.core.flow.data.UIEvent

sealed class HomeEvents : UIEvent() {

    object OpenAccountSelector : HomeEvents()

}