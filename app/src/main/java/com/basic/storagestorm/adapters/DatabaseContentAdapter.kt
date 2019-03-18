package com.basic.storagestorm.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.basic.storagestorm.Constants
import com.basic.storagestorm.MainActivity
import com.basic.storagestorm.R
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.Documents

/*
* Adapter class for the horizontal recycler view that behaves as a database path tracker
*
* @param list - a list of items TODO provide a real list and think about the data type
* @param context - context of the activity
* */
class DatabaseContentAdapter(private val list: MutableList<Pair<String, Any>>?, private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            1 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row, parent, false))
            2 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.collection_row, parent, false))
            3 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.document_row, parent, false))
            else -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.collection_row, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list!![position].first) {
            Constants .CATEGORY -> 1
            Constants.COLLECTION -> 2
            Constants.DOCUMENT -> 3
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
           1 -> (holder as ViewHolder).bindCategory(list!![position].second as String)
           2 -> (holder as ViewHolder).bindCollection(list!![position].second as Collection)
           3 -> (holder as ViewHolder).bindDocument(list!![position].second as Documents)
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

            val tvCollectionID = itemView.findViewById<TextView>(R.id.tvCollectionID)
            tvCollectionID?.text = collection.id

            val textWrapper = itemView.findViewById<RelativeLayout>(R.id.textWrapper)
            textWrapper.setOnClickListener {
                collection.performAction()
            }
        }

        fun bindDocument(document: Documents) {
            val tvID = itemView.findViewById<TextView>(R.id.tvID)
            val tvDocumentTitle = itemView.findViewById<TextView>(R.id.tvDocumentTitle)
            tvID.text = document.ID

            if (document.title.isNullOrBlank())
                tvDocumentTitle.visibility = View.GONE
            else {
                tvDocumentTitle.visibility = View.VISIBLE
                tvDocumentTitle.text = document.title
            }
        }
    }
}