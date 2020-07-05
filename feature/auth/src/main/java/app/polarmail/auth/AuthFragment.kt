package app.polarmail.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.polarmail.auth.databinding.FragmentAuthBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val viewModel: AuthViewModel by viewModels()

    lateinit var binding: FragmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        initRecycler()
        listenState()
    }

    private fun initRecycler() {
        binding.epoxyProviders.layoutManager =
            GridLayoutManager(
                requireActivity(),
                resources.getInteger(R.integer.grid_span),
                RecyclerView.VERTICAL,
                false
            )
    }

    private fun listenState() {
        onStates(viewModel) { state ->
            when (state) {
                is AuthViewState -> handleState(state)
            }
        }
    }

    private fun handleState(state: AuthViewState) {
        binding.epoxyProviders.withModels {
            state.providers.forEach { provider ->
                emailProvider {
                    id(provider.emailProvider.id)
                    icon(provider.icon)
                    name(provider.name)
                    clickListener {
                        // TODO
                    }
                }
            }
        }
    }

}