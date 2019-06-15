package com.basic.storagestorm.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.basic.storagestorm.R
import com.basic.storagestorm.models.HistoryEntry

class SearchHistoryAdapter(
    private val list: List<HistoryEntry>,
    private val context: Context?,
    private val onClick: (entry: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_row, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindItem(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: HistoryEntry) {
            val tvItem = itemView.findViewById<TextView>(R.id.tvItem)
            val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
            tvItem.text = item.entry
            tvDate.text = item.getDateAsString()
            itemView.setOnClickListener { onClick(item.entry) }
        }
    }
}