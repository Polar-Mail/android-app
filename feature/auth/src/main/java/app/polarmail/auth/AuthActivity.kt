package app.polarmail.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import app.polarmail.core_ui.util.NavigationLink.PARAM_AUTH_IS_FRESH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(R.layout.activity_auth) {

    companion object {
        const val ARG_IS_FRESH = PARAM_AUTH_IS_FRESH
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFresh = intent.getBooleanExtra(ARG_IS_FRESH, true)

        if (savedInstanceState == null) {
            val fragment = AuthFragment().apply {
                arguments = bundleOf(ARG_IS_FRESH to isFresh)
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
    }

}