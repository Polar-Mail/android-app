package app.polarmail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.NavController
import app.polarmail.auth.AuthActivity
import app.polarmail.auth.accountselector.AccountSelectorFragment
import app.polarmail.core_ui.extensions.hideSoftInput
import app.polarmail.core_ui.extensions.setupWithNavController
import app.polarmail.databinding.ActivityMainBinding
import app.polarmail.home.HomeAuthState
import app.polarmail.home.HomeEvents
import app.polarmail.home.HomeState
import app.polarmail.home.HomeViewModel
import app.polarmail.settings.SettingsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by viewModels()

    private var currentNavController: NavController? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setTitle(null)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        bindState()
        bindEvents()

        binding.imageAvatar.setOnClickListener {
            viewModel.openAccountSelector()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() = binding.bottomNavigation.setupWithNavController(
        listOf(
            R.navigation.nav_graph_mail,
            R.navigation.nav_graph_mail,
            R.navigation.nav_graph_mail
        ),
        supportFragmentManager,
        R.id.nav_host_container,
        intent
    ).observe(this) { navController ->
        currentNavController = navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.navSearch) {
                hideSoftInput()
            }
        }
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

    private fun bindEvents() {
        onEvents(viewModel) { event ->
            when (event.take()) {
                is HomeEvents.OpenAccountSelector -> openAccountSelector()
            }
        }
    }

    private fun handleState(homeState: HomeState) {
        when (val state = homeState.authState) {
            is HomeAuthState.LoggedOut -> {
                val intent = Intent(this, AuthActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
            is HomeAuthState.LoggedIn -> {
                Glide.with(this)
                    .asBitmap()
                    .load(state.accountSelected.avatar)
                    .apply(RequestOptions.centerCropTransform().circleCrop())
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onLoadCleared(placeholder: Drawable?) {
                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            updateProfileDrawable(resource)
                        }

                    })
            }
        }
    }

    private fun updateProfileDrawable(bitmap: Bitmap) {
        binding.imageAvatar.setImageBitmap(bitmap)
    }

    private fun openAccountSelector() {
        AccountSelectorFragment.show(supportFragmentManager) {
            openSettings()
        }
    }

    fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

}
