package com.basic.storagestorm

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.collection_row.view.*
import kotlinx.android.synthetic.main.database_path_row.view.*

/*
* Adapter class for the horizontal recycler view that behaves as a database path tracker
*
* @param list - a list of items TODO provide a real list and think about the data type
* @param context - context of the activity
* */
class DatabaseContentAdapter(private val list: MutableList<Pair<Int, Any>>, private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabaseContentAdapter.ViewHolder {
        return when(viewType) {
            Constants.COLLECTION -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.collection_row, parent, false))
            Constants.CATEGORY -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row, parent, false))
            else -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.collection_row, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
           Constants.CATEGORY -> (holder as DatabaseContentAdapter.ViewHolder).bindCategory(list[position].second as String)
           Constants.COLLECTION -> (holder as DatabaseContentAdapter.ViewHolder).bindCollection(list[position].second as Collection)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCategory(category: String) {
            val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
            tvCategory?.text = category
        }

        fun bindCollection(collection: Collection) {
            val tvCollectionTitle = itemView.findViewById<TextView>(R.id.tvCollectionTitle)
            tvCollectionTitle?.text = collection.name

            // TODO onClickListener ???
        }
    }
}