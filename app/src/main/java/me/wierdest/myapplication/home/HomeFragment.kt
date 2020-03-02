package me.wierdest.myapplication.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.wierdest.myapplication.R
import me.wierdest.myapplication.database.MyViewModel
import me.wierdest.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        binding.lifecycleOwner = this

        /**
         * Now, instead of having a viewModel created from scratch,
         * the same old instance created by TitleFragment is requested
         */
        viewModel =
            ViewModelProvider(this.requireParentFragment()).get(MyViewModel::class.java)

        /**
         * Set the binding's viewModel to the one we requested so we can use
         * databinding in the xml layout directly. Check the fragment_home layout
         * for the attribute being converted there from Long to String.
         */

        binding.myViewModel = viewModel

        /**
         * Also set up an observer for lastSession.
         * If there isn't a session, create one.
         */

        viewModel.lastSession.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                viewModel.addNewSession()
            }
            else {
                Log.i("HomeFragment", "LastSession is ${it.sessionId}")
            }
        })

        return binding.root


    }

}