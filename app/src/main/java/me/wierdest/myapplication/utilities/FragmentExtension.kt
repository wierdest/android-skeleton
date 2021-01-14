package me.wierdest.myapplication.utilities

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import me.wierdest.myapplication.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


/**
 * Collection of Fragment extension functions
 */

const val OPEN_DOC_REQUEST_CODE = 1
const val FILE_SIZE_REQUEST_CODE = 1
const val FILE_NAME_REQUEST_CODE = 0

val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()

/**
 * Hides activity's bars
 */
fun Fragment.hideSupportBar(h: Boolean)  {
    val castActivity = this.activity as AppCompatActivity
    val bar = castActivity.supportActionBar
    if (h) bar?.hide() else bar?.show()
}


fun Fragment.hideStatusBar(h: Boolean) {
    requireActivity().window.apply {
        if (h) {
            addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}


/**
 * Opens a text (pdf, docx, odt, epub, or text/all ), using an intent
 * Receives the results on its onActivityResult
 */
fun Fragment.openDocument() {
    /**
     * We use GET_CONTENT here because we're not interested in a persistable permission access
     * (across device reboots). We're mainly interested in the current content of the document,
     * now, in the future we may add an option suitable for that need (maybe to deal with ongoing
     * updating or incomplete source documents, without the need of a rescan)
     * This is work for a later time, but let its need be recorded here
     */
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "text/*"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            putExtra(
                Intent.EXTRA_MIME_TYPES, arrayOf("text/*", "application/pdf", "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.oasis.opendocument.text", "application/epub+zip")
            )
        }
        addCategory(Intent.CATEGORY_OPENABLE)
    }
    if (intent.resolveActivity(this.requireActivity().packageManager) != null) {
        startActivityForResult(intent, OPEN_DOC_REQUEST_CODE)
    }
}

/**
 * Opens a document externally, and if the intent can't be resolved shows
 * an alert dialog that pops the back stack.
 */

fun Fragment.openDocumentExternally(typeToOpen: String?, uri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        val path = uri.path!! // there must be a path!
        if (path.contains(".doc") || path.contains(".docx") ||
            path.contains(".odt")) {
            setDataAndType(uri, "application/msword")
        } else {
            setDataAndType(uri, typeToOpen)
        }
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    if (intent.resolveActivity(this.requireActivity().packageManager) != null) {
        startActivity(intent)
        requireArguments().putBoolean("openDocExt", true)

    } else {
        requireArguments().putBoolean("openDocExt", false)
        // intent can't be resolved, so we pop the back stack
        val alert: AlertDialog? = activity?.let { val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(getString(R.string.alert_on_open_doc_ext_unsuccessful))
                setPositiveButton(getString(R.string.alert_ok)) { _, _ -> findNavController().popBackStack() }
            }.create()
        }
        alert?.show()
    }
}

/**
 * Reads and returns a text from input URI
 */
@Throws(IOException::class)
fun Fragment.readTextFromUri(uri: Uri) : String {
    val stringBuilder = StringBuilder()
    requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                stringBuilder.append("\n")
                line = reader.readLine()
            }
        }
    }
    return stringBuilder.toString()
}

/**
 * Extracts doc metadata according to the request code constant
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
fun Fragment.extractDocMetaData(uri: Uri, requestCode: Int) : String {
    /**
     * Query returns only one row
     */
    val data = mutableListOf("")
    val cursor: Cursor? = requireContext().contentResolver.query(uri, null, null, null,
        null, null)

    cursor?.use {
        if (it.moveToFirst()) {
            data.apply {
                add(FILE_NAME_REQUEST_CODE, it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME)))

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                add(FILE_SIZE_REQUEST_CODE, if (!it.isNull(sizeIndex)) it.getString(sizeIndex) else "Unknown")
            }
        }
    }
    return data[requestCode]
}