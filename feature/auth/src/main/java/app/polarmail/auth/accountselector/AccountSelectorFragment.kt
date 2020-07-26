package app.polarmail.auth.accountselector

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.polarmail.auth.R
import app.polarmail.auth.databinding.FragmentAccountSelectorBinding

class AccountSelectorFragment : Fragment(R.layout.fragment_account_selector) {

    private lateinit var binding: FragmentAccountSelectorBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountSelectorBinding.bind(view)
    }



}