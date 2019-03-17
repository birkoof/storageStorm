package com.basic.storagestorm.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.basic.storagestorm.R
import com.basic.storagestorm.models.Path
import kotlinx.android.synthetic.main.database_path_row.view.*

/*
* Adapter class for the horizontal recycler view that behaves as a database path tracker
*
* @param pathList - a list of paths
* @param context - context of the activity
* */
class DatabasePathAdapter(private val pathList: MutableList<Path>, private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.database_path_row, parent, false))
    }

    override fun getItemCount(): Int {
        return pathList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindPath(pathList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pathText = itemView.pathText as TextView

        fun bindPath(path: Path) {
            pathText.text = path.name
            pathText.setOnClickListener {
                path.performAction()
            }
        }
    }
}