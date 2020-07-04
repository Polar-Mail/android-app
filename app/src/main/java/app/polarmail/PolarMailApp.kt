package app.polarmail

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PolarMailApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }

}