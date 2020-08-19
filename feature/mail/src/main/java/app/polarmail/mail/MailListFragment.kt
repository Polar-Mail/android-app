package app.polarmail.mail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.polarmail.mail.databinding.FragmentMailListBinding

class MailListFragment : Fragment(R.layout.fragment_mail_list) {

    private lateinit var binding: FragmentMailListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMailListBinding.bind(view)
    }

}