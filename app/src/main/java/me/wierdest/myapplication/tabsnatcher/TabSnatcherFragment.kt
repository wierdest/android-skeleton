package me.wierdest.myapplication.tabsnatcher

import android.app.Activity
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.wierdest.myapplication.R
import me.wierdest.myapplication.database.MyViewModel
import me.wierdest.myapplication.databinding.FragmentTabSnatcherBinding
import me.wierdest.myapplication.utilities.*

class TabSnatcherFragment : Fragment() {

    val TAG = javaClass.simpleName
    private lateinit var b: FragmentTabSnatcherBinding
    private lateinit var viewModel: MyViewModel

    /**
     * We need the content resolver to check if the file is the right type
     * We need the clipboard to insert the contents of a smaller than ideal file and communicate it to LibraryFragment
     */

    private lateinit var contentResolver: ContentResolver
    private lateinit var clipboard: ClipboardManager

    /**
     * Timer:
     */
    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /**
         * Binding and viewModel
         */
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_snatcher, container,false)
        b.lifecycleOwner = this
        viewModel =
            ViewModelProvider(this.requireParentFragment()).get(MyViewModel::class.java)

        contentResolver = requireContext().contentResolver
        clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        openDocument()

        return b.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPEN_DOC_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also {
                Log.i(TAG, "On Activity result!")
                val type = contentResolver.getType(it)
                when  {
                    type != "text/plain" -> {
                        openDocumentExternally(type, it)
                        Log.i(TAG, "called opened doc externally type: $type uri: $it")

                    }
                    type == "text/plain" -> {
                        sendTabToClipboardAndGo(type, it)
                    }
                    else -> {
                        Log.i(TAG, "On Activity result else!")
                    }

                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "On Resume!")
        if (requireArguments().getBoolean("openDocExt")) {
            // we've opened a document externally, the user probably, maybe was successful in copying some text.
            // we have no way to know that here.
            // Yes, we do. We put an argument in the fragment if the intent was correctly resolved
            // We could change the visuals here, and instead of using an alert dialog on the extension openDocumentExternally
            // we could show a snackbar, etc... todo
            countDown()
        }

    }

    private fun sendTabToClipboardAndGo(type: String, u: Uri) {
        val source = readTextFromUri(u)
        val label = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            extractDocMetaData(u, FILE_NAME_REQUEST_CODE)
        } else {
            "Filename Unknown"
        }
        clipboard.insert(label, source)
        countDown()

    }
    private fun countDown() {
        countDownTimer = object : CountDownTimer(400, 100) {
            override fun onFinish() {
                // navigate to the snippets fragment
                findNavController().popBackStack()
            }
            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }




}