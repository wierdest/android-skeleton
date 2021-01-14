package me.wierdest.myapplication.title

import android.app.AlertDialog
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import me.wierdest.myapplication.R
import me.wierdest.myapplication.database.MyDatabase
import me.wierdest.myapplication.database.MyViewModel
import me.wierdest.myapplication.database.MyViewModelFactory
import me.wierdest.myapplication.databinding.FragmentTitleBinding
import me.wierdest.myapplication.utilities.empty
import me.wierdest.myapplication.utilities.getClipText
import me.wierdest.myapplication.utilities.hideSupportBar


/**
 * Basic title fragment with databinding, a  basic viewmodel
 * and navigation to the app's home fragment.
 */
class TitleFragment : Fragment() {

    private lateinit var b: FragmentTitleBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var clipboard: ClipboardManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        b = DataBindingUtil.inflate(inflater,
            R.layout.fragment_title, container, false)

        b.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val sessionDAO = MyDatabase.getInstance(application).tabDAO
        val viewModelFactory = MyViewModelFactory(
            sessionDAO,
            application
        )

        hideSupportBar(false)

        viewModel = ViewModelProvider(this.requireParentFragment(), viewModelFactory)
            .get(MyViewModel::class.java)

        /**
         * INITIALIZE CLIPBOARD
         */

        clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        /**
         * ADD BUTTON LISTENER
         */

        b.addButton.setOnClickListener { alertOnNewTab(clipboard.getClipText()) }


        /**
         * RECYCLER VIEW STUFF
         */
        val tabItemListener = TabItemListener(

            // onSwipeLeft:
            { tabId -> alertOnRemove(tabId) },

            // onSwipeRight:
            { tabId -> pickTabAndNavigate(tabId) }

        )
        val adapter = TabListAdapter(tabItemListener)
        b.tabList.adapter = adapter
        val tabItemTouchCallback = TabItemTouchCallback(adapter)
        val tabItemTouchHelper = ItemTouchHelper(tabItemTouchCallback)
        tabItemTouchHelper.attachToRecyclerView(b.tabList)

        /**
         * Model Observers
         */
        /**
         * ALL TABS TO FILL UP THE RECYCLER VIEW'S LIST
         */
        viewModel.allTabs.observe(viewLifecycleOwner, Observer {
            it?.also { adapter.submitList(it) }
        })

        /**
         * IS SOURCE UNIQUE TO PREVENT ADDING DUPLICATES
         */
        viewModel.isSourceUnique.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!it) {
                    alertOnNotUniqueSource()
                } else {
                    alertOnUniqueSource()
                }
                viewModel.resetIsSourceUnique()
            }
        })

        /**
         * Set up an optionsMenu on the top bar
         */
        setHasOptionsMenu(true)

        return b.root
    }

    private fun alertOnUniqueSource() {
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        val alert: AlertDialog? = activity?.let { val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Please add a NAME to the Tab!")
                setView(input)
                setNegativeButton(getString(R.string.alert_no)) { _, _ -> }
                setPositiveButton(getString(R.string.alert_ok)) { _, _ -> viewModel.addNameToLastTab(input.text.toString()); b.tabList.adapter?.notifyDataSetChanged() }
            }.create()
        }
        alert?.show()

    }
    override fun onResume() {
        super.onResume()
        alertOnNewTab(clipboard.getClipText())
    }

    private fun pickTabAndNavigate(tabId: Long) {
        viewModel.pickTabToRead(tabId)
        findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToHomeFragment())
    }

    private fun alertOnNotUniqueSource() {
        val alert: AlertDialog? = activity?.let { val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("You've already got that one!")
                setMessage("Find another tab?")
                setNegativeButton(getString(R.string.alert_no)) { _, _ -> }
                setPositiveButton(getString(R.string.alert_sure)) { _, _ -> goFetchTab() }
            }.create()
        }
        alert?.show()

    }
    private fun alertOnRemove(tabId: Long) {
        val alert: AlertDialog? = activity?.let { val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Are you sure you would like to delete this tab?")
                setMessage("There's no undoing for that right now!")
                setNegativeButton(getString(R.string.alert_no)) { _, _ -> b.tabList.adapter?.notifyDataSetChanged() }
                setPositiveButton(getString(R.string.alert_sure)) { _, _ -> viewModel.removeTab(tabId) }
            }.create()
        }
        alert?.show()

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
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun alertOnNewTab(source: String?) {
        val alert: AlertDialog? = activity?.let { val builder = AlertDialog.Builder(it)
            builder.apply {
                if (source.isNullOrEmpty()) {
                    setMessage("No tab on clipboard! Find in file?")
                    setNegativeButton("NO") { _, _ -> } // do nothing
                    setPositiveButton("SURE") { _, _ -> goFetchTab() }
                } else {
                    setTitle(getString(R.string.alert_clipboard_is_ok))
                    setMessage(source)
                    setNegativeButton(getString(R.string.alert_no)) { _, _ ->
                        goFetchTab()
                        clipboard.empty() // dismiss the source
                    }
                    setPositiveButton(getString(R.string.alert_sure)) { _, _ ->
                        viewModel.addNewTab(source)
                        clipboard.empty()
                    }
                }
            }.create()
        }
        alert?.show()
    }
    private fun goFetchTab() {
        findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToTabSnatcherFragment())
    }


}