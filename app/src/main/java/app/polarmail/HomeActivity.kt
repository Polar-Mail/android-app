package app.polarmail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import app.polarmail.auth.AuthActivity
import app.polarmail.databinding.ActivityMainBinding
import app.polarmail.domain.model.AuthState
import app.polarmail.home.HomeAuthState
import app.polarmail.home.HomeState
import app.polarmail.home.HomeViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
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

}
