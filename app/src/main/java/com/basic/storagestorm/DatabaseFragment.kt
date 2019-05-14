package com.basic.storagestorm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.adapters.DatabasePathAdapter
import com.basic.storagestorm.models.Path
import com.basic.storagestorm.providers.DataListProvider


class DatabaseFragment : Fragment(), BackpressHandler {

    private var pathList = mutableListOf<Path>()
    private var dataList : MutableList<Pair<String, Any>>? = null
    private lateinit var recyclerContent : RecyclerView
    private lateinit var recyclerPath : RecyclerView
    private val dataListProvider = DataListProvider(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_database, container, false)

        pathList.add(Path(Constants.HOME, Constants.HOME, Constants.HOME) {
            updateContent(Constants.HOME, Constants.HOME, Constants.HOME)
            removeAllPathsAfter(Path(Constants.HOME, Constants.HOME, Constants.HOME) {})
        })

        recyclerPath = view.findViewById(R.id.recyclerPath)
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)

        dataList = dataListProvider.getAllCollections()

        recyclerContent = view.findViewById(R.id.recyclerContent)
        recyclerContent.adapter = DatabaseContentAdapter(dataList, activity)

        return view
    }

    /**
     * This method will be called when a path is clicked in the path recycler view
     * It loads all data that belong to the collection/object that is selected
     *
     * @param title: The name of the collection/object that is displayed // TODO redundant?
     * @param id: the ID of the collection/object
     * @param type: one of the constants that determines the type (collection, object, home)
     * */
    fun updateContent(title: String?, id: String, type: String) {
        val content: MutableList<Pair<String, Any>> = when (type) {
            Constants.HOME -> dataListProvider.getAllCollections()
            Constants.COLLECTION -> dataListProvider.getCollectionData(title, id)
            else -> dataListProvider.getObjectData(id)
        }

        recyclerContent.adapter = DatabaseContentAdapter(content, activity)

        pathList.add(Path(title, id, type) {
            updateContent(title, id, type)
            removeAllPathsAfter(Path(title, id, type) {})
        })
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)
        recyclerPath.smoothScrollToPosition(pathList.size - 1)
    }

    /**
     * This method removes all path entries in the path recycler that are
     * position after the path that is selected
     *
     * @param path: the selected path
     */
    private fun removeAllPathsAfter(path: Path) {
        // TODO find a cleaner kotlin solution
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

    companion object {
        fun newInstance() = DatabaseFragment()
    }

    private fun parseJson(id: String) : String { return Helper.getFileByID(id) }

    override fun onBackButtonPressed(): Boolean {
        if (pathList.size == 1)
            return false

        val path = pathList[pathList.size - 2]
        updateContent(path.name, path.ID, path.type)
        removeAllPathsAfter(path)

        return true
    }

}
