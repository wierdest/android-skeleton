package me.wierdest.mindsetquest.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wierdest.mindsetquest.R
import me.wierdest.mindsetquest.database.MyViewModel
import me.wierdest.mindsetquest.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_settings, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this.requireParentFragment()).get(MyViewModel::class.java)


        val clearAllButton = binding.clearAllButton

        viewModel.lastSession.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                clearAllButton.isEnabled = false
                clearAllButton.setText(R.string.settings_no_sessions_to_clear_button)
            }
            else {
                clearAllButton.isEnabled = true
                clearAllButton.setOnClickListener {
                    viewModel.clearAll()
                }
            }
        })

        return binding.root
    }
}