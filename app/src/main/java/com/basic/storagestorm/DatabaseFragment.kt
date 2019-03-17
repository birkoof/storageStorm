package com.basic.storagestorm

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DialogTitle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.adapters.DatabasePathAdapter
import com.basic.storagestorm.models.Path
import com.basic.storagestorm.providers.DataListProvider


class DatabaseFragment : Fragment() {

    private var pathList = mutableListOf<Path>()
    private var dataList = mutableListOf<Pair<Int, Any>>()
    private lateinit var recyclerContent : RecyclerView
    private lateinit var recyclerPath : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_database, container, false)

        Log.d("TEST", "Adding home - Pathlist size before: ${pathList.size}")
        // TODO feed data legally
        pathList.add(Path(Constants.HOME, Constants.HOME) {
            updateContent(Constants.HOME, Constants.HOME, DataListProvider(this).getData(Constants.HOME))
            removeAllPathsAfter(Path(Constants.HOME, Constants.HOME){})
        })
        Log.d("TEST", "Adding home - Pathlist size after: ${pathList.size}")
        recyclerPath = view.findViewById(R.id.recyclerPath)
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)

        dataList = DataListProvider(this).getData(Constants.HOME)

        recyclerContent = view.findViewById(R.id.recyclerContent)
        recyclerContent.adapter = DatabaseContentAdapter(dataList, activity)

        return view
    }

    fun updateContent(title: String, id: String, newData: MutableList<Pair<Int, Any>>) {
        recyclerContent.adapter = DatabaseContentAdapter(newData, activity)
        Log.d("TEST", "Adding path - Pathlist size before: ${pathList.size}")
        pathList.add(Path(title, id) {
            updateContent(title, id, DataListProvider(this).getData(id))
            removeAllPathsAfter(Path(title, id){})
        })
        Log.d("TEST", "Adding path - Pathlist size after: ${pathList.size}")
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)
    }

    private fun removeAllPathsAfter(path: Path) {
        // TODO find a cleaner kotlin solution
        Log.d("TEST", "Deleting path - Pathlist size: ${pathList.size}")
        val newList = mutableListOf<Path>()
        for (iterator in pathList) {
            if (iterator.ID != path.ID) {
                newList.add(iterator)
            } else {
                newList.add(iterator)
                break
            }
        }
        pathList = newList
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = DatabaseFragment()
    }
}
