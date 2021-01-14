package me.wierdest.myapplication.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import me.wierdest.myapplication.database.Tab
import me.wierdest.myapplication.databinding.ListItemTabBinding


class TabListAdapter(val listener: TabItemListener) : ListAdapter<Tab, TabListAdapter.ViewHolder>(TabItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(val b: ListItemTabBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Tab) {

            b.item = item
            b.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTabBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }


}

class TabItemDiffCallback : DiffUtil.ItemCallback<Tab>() {
    override fun areItemsTheSame(oldItem: Tab, newItem: Tab): Boolean {
        return oldItem.tabId == newItem.tabId
    }

    override fun areContentsTheSame(oldItem: Tab, newItem: Tab): Boolean {
       return oldItem == newItem
    }

}

class TabItemListener(val onSwipeLeft: (tabId:Long) -> Unit, val onSwipeRight: (tabId:Long) -> Unit)

class TabItemTouchCallback(private val adapter: TabListAdapter)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val item = adapter.currentList[viewHolder.adapterPosition]
        when (direction) {
            ItemTouchHelper.LEFT -> {
                adapter.listener.onSwipeLeft(item.tabId)
            }
            // diffUtil Callback takes the job of actually removing from the list, as long as we removed from database!
            ItemTouchHelper.RIGHT -> {
                adapter.listener.onSwipeRight(item.tabId)
                adapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }

    }

}