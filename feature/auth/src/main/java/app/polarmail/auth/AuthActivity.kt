package app.polarmail.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(R.layout.activity_auth) {

    companion object {
        const val ARG_IS_FRESH = "is_fresh"
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