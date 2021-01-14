package me.wierdest.myapplication.utilities

import android.content.ClipData
import android.content.ClipboardManager

/**
 * Collection of ClipboardManager's extension functions
 */

/**
 * Checks if there's a primary clip on the clipboard that holds plain text
 * and returns its content string
 */
fun ClipboardManager.getClipText() : String? {
    primaryClip?.also {
        if (it.description.hasMimeType("text/*")) {
            val clipText = it.getItemAt(0).text
            // At some point we would need to pre process the text, maybe. For content expansion.
            // for now we simply return the text
            return clipText.toString()
        }
    }
    return null
}
/**
 * Empties the clipboard by adding an empty clip
 */

fun ClipboardManager.empty() {

    val emptySequence = "" as CharSequence
    val mimeTypes = arrayOf("text/*")
    val item = ClipData.Item("")
    val emptyClip = ClipData(emptySequence, mimeTypes, item)
    setPrimaryClip(emptyClip)
}

/**
 * Inserts contents of a plain text file in the clipboard
 */

fun ClipboardManager.insert(label: CharSequence, source: CharSequence) {
    val item = ClipData.Item(source)
    val clip = ClipData(label, arrayOf("text/*"), item)
    setPrimaryClip(clip)
}

/**
 * Checks if there's a primary clip on the clipboard that holds plain text
 * and returns its content string
 */
fun ClipboardManager.getClipName() : String? {
    primaryClip?.also {
        if (it.description.hasMimeType("text/*")) {
            val clipName = it.description.label
            // At some point we would need to pre process the text, maybe. For content expansion.
            // for now we simply return the text
            if (clipName.isNotEmpty()) {
                return clipName.toString()
            }
        }
    }
    return null
}