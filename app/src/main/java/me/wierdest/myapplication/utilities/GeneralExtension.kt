package me.wierdest.myapplication.utilities

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


/**
 * Simple linear conversion: converts a value within an old range
 * to a new value within a new range, and returns it
 */
fun Int.linearConversion(oldMax: Int, oldMin: Int, newMax: Int, newMin: Int) : Int {
    val oldRange = oldMax - oldMin
    return if (oldRange == 0) {
        newMin
    } else {
        val newRange = newMax - newMin
        (((this - oldMin) * newRange) / oldRange) + newMin
    }
}


/**
 * Takes the Long for the time in milliseconds and
 * converts it to a nicely formatted string for display.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the month
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */
@SuppressLint("SimpleDateFormat")
fun Long.convertLongToDateString(): String {
    return SimpleDateFormat("EEEE MMM-dd-yy' at: 'HH:mm")
        .format(this).toString()
}
