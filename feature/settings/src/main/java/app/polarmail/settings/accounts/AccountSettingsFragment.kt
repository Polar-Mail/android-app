package app.polarmail.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.polarmail.settings.databinding.FragmentSettingsAccountsBinding

class AccountSettingsFragment : Fragment(R.layout.fragment_settings_accounts) {

    private lateinit var binding: FragmentSettingsAccountsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsAccountsBinding.bind(view)
    }

}