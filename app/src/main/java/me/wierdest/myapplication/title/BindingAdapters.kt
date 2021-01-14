package me.wierdest.myapplication.title

import android.widget.TextView
import androidx.databinding.BindingAdapter
import me.wierdest.myapplication.database.Tab


@BindingAdapter("tabItemId")
fun TextView.setTabItemId(item: Tab) {
    text = item.tabId.toString()
}

@BindingAdapter("tabItemName")
fun TextView.setTabItemName(item: Tab) {
    text = item.name
}
