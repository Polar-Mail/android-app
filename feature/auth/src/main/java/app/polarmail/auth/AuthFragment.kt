package app.polarmail.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.polarmail.auth.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentAuthBinding.bind(view)
    }



}