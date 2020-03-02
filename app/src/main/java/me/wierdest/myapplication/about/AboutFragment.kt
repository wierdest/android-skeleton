package me.wierdest.myapplication.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.wierdest.myapplication.R

class AboutFragment : Fragment() {

    /**
     * Since it's a simple fragment, we  don't need to create
     * a binding object right now. We simply return the inflated layout
     * */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}