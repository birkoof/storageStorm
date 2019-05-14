package com.basic.storagestorm.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.basic.storagestorm.Constants
import com.basic.storagestorm.R
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.DataObject
import com.basic.storagestorm.models.Field
import com.dandan.jsonhandleview.library.JsonViewLayout

/*
* Adapter class for the main recycler view that displays the actual data
*
* @param list - list of items
* @param context - context of the activity
* */
class DatabaseContentAdapter(private val list: MutableList<Pair<String, Any>>?, private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            1 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row, parent, false))
            2 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.collection_row, parent, false))
            3 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.data_object_row, parent, false))
            else -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.field_row, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list!![position].first) {
            Constants.CATEGORY -> 1
            Constants.COLLECTION -> 2
            Constants.DATA_OBJECT -> 3
            Constants.FIELD -> 4
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
            3 -> (holder as ViewHolder).bindDataObject(list!![position].second as DataObject)
           4 -> (holder as ViewHolder).bindField(list!![position].second as Field)
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

        fun bindDataObject(document: DataObject) {
            val tvID = itemView.findViewById<TextView>(R.id.tvID)
            val contentView = itemView.findViewById<RelativeLayout>(R.id.contentView)

            tvID.text = document.ID
            contentView.setOnClickListener {
                document.performAction()
            }
        }

        fun bindField(field: Field) {
            val jsonView = itemView.findViewById<JsonViewLayout>(R.id.jsonView)
            jsonView.bindJson(field.getJSON())

            // TODO add makeup
            jsonView.setKeyColor(Color.BLACK)
            jsonView.setValueTextColor(Color.parseColor("#b00020"))
            jsonView.setValueNumberColor(Color.parseColor("#bc2641"))
            jsonView.expandAll()
        }
    }
}