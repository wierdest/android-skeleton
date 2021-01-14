package me.wierdest.myapplication.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import me.wierdest.myapplication.R
import me.wierdest.myapplication.database.MyViewModel
import me.wierdest.myapplication.databinding.FragmentSettingsBinding

const val DEFAULT_SCROLL_SPEED = 50
class SettingsFragment : PreferenceFragmentCompat() {
    val TAG = javaClass.name
    val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "speed") {
                val seekBarPreference = findPreference<SeekBarPreference>("speed")
                seekBarPreference?.apply {
                    value = sharedPreferences.getInt("speed", DEFAULT_SCROLL_SPEED)
                    Log.i(TAG, "reset scroll speed to: $value")
                }

            }

        }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}

//class SettingsFragment : Fragment() {
//
//    private lateinit var binding: FragmentSettingsBinding
//    private lateinit var viewModel: MyViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = DataBindingUtil.inflate(inflater,
//            R.layout.fragment_settings, container, false)
//        binding.lifecycleOwner = this
//
//        viewModel = ViewModelProvider(this.requireParentFragment()).get(MyViewModel::class.java)
//
//
//        val clearAllButton = binding.clearAllButton
//
//        viewModel.lastTab.observe(viewLifecycleOwner, Observer {
//            if (it == null) {
//                clearAllButton.isEnabled = false
//                clearAllButton.setText(R.string.settings_no_tabs_to_clear_button)
//            }
//            else {
//                clearAllButton.isEnabled = true
//                clearAllButton.setOnClickListener {
//                    viewModel.clearAll()
//                }
//            }
//        })
//
//        return binding.root
//    }
//}