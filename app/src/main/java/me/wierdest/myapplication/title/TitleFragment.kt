package me.wierdest.myapplication.title

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import me.wierdest.myapplication.R
import me.wierdest.myapplication.database.MyDatabase
import me.wierdest.myapplication.database.MyViewModel
import me.wierdest.myapplication.database.MyViewModelFactory
import me.wierdest.myapplication.databinding.FragmentTitleBinding


/**
 * Basic title fragment with databinding, a  basic viewmodel
 * and navigation to the app's home fragment.
 */
class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_title, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val sessionDAO = MyDatabase.getInstance(application).sessionDAO
        val viewModelFactory = MyViewModelFactory(
            sessionDAO,
            application
        )

        viewModel = ViewModelProvider(this.requireParentFragment(), viewModelFactory)
            .get(MyViewModel::class.java)

        /**
         * UI elements can be dealt with programmatically,
         * through the Observer pattern technique, or
         * in the xml, through the <data> tag.
         *
         * The latter technique is less dynamic, good for
         * a straightforward bridge between database and ui.
         *
         * As an example of it, check HomeFragment; the
         * viewModel's live data SessionId is bound to its
         * TextView, using Transformations and mapping.
         *
         * The newButton's clickListener and the continueButton's
         * enable state are both determined by the
         * observation of a piece of the viewModel's LiveData,
         * lastSession, explicitly below:
         */

        val continueButton = binding.continueSessionButton
        val newButton = binding.newSessionButton

        /**
         * Setting up the observer
         */
        viewModel.lastSession.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                continueButton.isEnabled = false
                /**
                 * If there isn't a last session, simply navigate to
                 * HomeFragment
                 */
                newButton.setOnClickListener { view ->
                    view.findNavController().navigate(TitleFragmentDirections
                        .actionTitleFragmentToHomeFragment())
                }

            }
            else {

                /**
                 * If there's a session, alert about erasing it with the newButton,
                 * set continueButton's clickListener to navigate
                 */
                continueButton.isEnabled = true
                continueButton.setOnClickListener { view ->
                    view.findNavController().navigate(TitleFragmentDirections
                        .actionTitleFragmentToHomeFragment())
                }

                newButton.setOnClickListener { _ ->
                    alertOnNewSession(it.sessionId)
                }
            }
        })

        /**
         * Set up an optionsMenu on the top bar
         */

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.title_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /**
         * Sets up  navigation
         */
        return NavigationUI.onNavDestinationSelected(item,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun alertOnNewSession(id: Long) {
        /**
         * Pulls up an alertDialog, erasing the current -- last --
         * session if that's the case, by its id.
         */
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNegativeButton("No") {_, _ ->
                }
                setPositiveButton("Yes") {_, _ ->
                    viewModel.removeSession(id)
                }
            }
            builder.setTitle("This erases the current unsaved session.")
            builder.setMessage("Are you sure?")
            builder.create()
        }
        alertDialog?.show()
    }


}