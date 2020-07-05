package app.polarmail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import app.polarmail.auth.AuthActivity
import app.polarmail.domain.model.AuthState
import app.polarmail.home.HomeState
import app.polarmail.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by viewModels()

    private var currentNavController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        bindState()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.navigateUp() ?: super.onSupportNavigateUp()
    }

    private fun bindState() {
        onStates(viewModel) { state ->
            when (state) {
                is HomeState -> handleState(state)
            }
        }
    }

    private fun handleState(homeState: HomeState) {
        if (homeState.authState == AuthState.LOGGED_OUT) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }

}
