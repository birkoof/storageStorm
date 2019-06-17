package com.basic.storagestorm.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.R
import com.basic.storagestorm.activities.EditObject
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.DataObject
import com.basic.storagestorm.models.Field
import com.basic.storagestorm.utilities.Constants
import com.basic.storagestorm.utilities.Helper
import com.dandan.jsonhandleview.library.JsonViewLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

/*
* Adapter class for a recycler view that displays the data
* is flexible and can be reused in different activities/fragments
*
* @param list - list of items
* @param context - context of the activity
* */
class DatabaseContentAdapter(
    private val list: MutableList<Pair<String,
            Any>>?, private val context: Context?
) :
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

        fun bindDataObject(dataObject: DataObject) {
            val tvID = itemView.findViewById<TextView>(R.id.tvID)
            val contentView = itemView.findViewById<RelativeLayout>(R.id.contentView)
            val ivEdit = itemView.findViewById<ImageView>(R.id.ivEdit)
            val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete)

            tvID.text = dataObject.ID
            contentView.setOnClickListener {
                dataObject.performAction()
            }

            ivEdit.setOnClickListener {
                doAsync {
                    try {
                        val json = IkarusApi(Constants.UTILITIES_SERVER_URL).get(dataObject.ID)
                        uiThread {
                            val intent = Intent(contentView.context, EditObject::class.java)
                            intent.putExtra(Constants.INTENT_EXTRA_OBJECT_ID, dataObject.ID)
                            intent.putExtra(Constants.INTENT_EXTRA_OBJECT_JSON, json)
                            contentView.context.startActivity(intent)
                        }
                    } catch (exception: IOException) {
                        uiThread {
                            Toast.makeText(contentView.context, "Network error.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            ivDelete.setOnClickListener {
                val builder = AlertDialog.Builder(contentView.context)
                builder.setTitle("Delete object")
                builder.setMessage("Are you sure you want to delete object ${dataObject.ID}?")
                builder.setPositiveButton("Yes") { _, _ ->
                    if (!Helper.hasNetworkConnection(contentView.context)) {
                        Toast.makeText(contentView.context, "Please check your network.", Toast.LENGTH_LONG).show()
                    } else {
                        doAsync {
                            try {
                                val success = IkarusApi(Constants.UTILITIES_SERVER_URL).delete(dataObject.ID)
                                Log.d("IKARUS", "deleting object ${dataObject.ID} - success: $success")
                                uiThread {
                                    Toast.makeText(
                                        contentView.context,
                                        "Object ${dataObject.ID} deleted.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    contentView.context.sendBroadcast(Intent(Constants.REFRESH_DATA))
                                }
                            } catch (exception: IOException) {
                                uiThread {
                                    Toast.makeText(
                                        contentView.context,
                                        "An error occurred, please try again.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@uiThread
                                }
                            }
                        }
                    }
                }
                builder.setNegativeButton("No") { _, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        fun bindField(field: Field) {
            val jsonView = itemView.findViewById<JsonViewLayout>(R.id.jsonView)
            jsonView.bindJson(field.json)

            // TODO add makeup
            jsonView.setKeyColor(Color.BLACK)
            jsonView.setValueTextColor(Color.parseColor("#b00020"))
            jsonView.setValueNumberColor(Color.parseColor("#bc2641"))
            jsonView.expandAll()
        }
    }
}