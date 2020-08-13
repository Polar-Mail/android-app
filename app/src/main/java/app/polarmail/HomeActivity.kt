package app.polarmail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import app.polarmail.auth.AuthActivity
import app.polarmail.auth.accountselector.AccountSelectorFragment
import app.polarmail.core_ui.extensions.hideSoftInput
import app.polarmail.core_ui.extensions.setupWithNavController
import app.polarmail.databinding.ActivityMainBinding
import app.polarmail.domain.model.AuthState
import app.polarmail.home.HomeAuthState
import app.polarmail.home.HomeEvents
import app.polarmail.home.HomeState
import app.polarmail.home.HomeViewModel
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

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        bindState()
        bindEvents()

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menu ->
            if (menu.itemId == R.id.navProfile) {
                viewModel.openAccountSelector()
                return@setOnNavigationItemSelectedListener true
            }
            false
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        /*binding.bottomNavigation.setupWithNavController(
            listOf(),
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
         */
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
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
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
        val drawable = BitmapDrawable(resources, bitmap)
        binding.bottomNavigation.setItemIconTintList(null)
        binding.bottomNavigation.menu.findItem(R.id.navProfile).run {
            icon = drawable
        }
    }

    private fun openAccountSelector() {
        AccountSelectorFragment.show(supportFragmentManager)
    }

}
