package com.basic.storagestorm

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.adapters.DatabasePathAdapter
import com.basic.storagestorm.models.Path
import com.basic.storagestorm.providers.DataListProvider
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.InputStream
import java.lang.Exception


class DatabaseFragment : Fragment(), BackpressHandler {

    private var pathList = mutableListOf<Path>()
    private var dataList : MutableList<Pair<String, Any>>? = null
    private lateinit var recyclerContent : RecyclerView
    private lateinit var recyclerPath : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_database, container, false)

        // TODO feed data legally
        val homeData = DataListProvider(this, activity).getData(Pair(Constants.HOME, Constants.HOME))

        pathList.add(Path(Constants.HOME, Constants.HOME, homeData) {
            updateContent(Constants.HOME, Constants.HOME, homeData)

            removeAllPathsAfter(Path(Constants.HOME, Constants.HOME, homeData){})
        })
        recyclerPath = view.findViewById(R.id.recyclerPath)
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)

        dataList = DataListProvider(this, activity).getData(Pair(Constants.HOME, Constants.HOME))

        recyclerContent = view.findViewById(R.id.recyclerContent)
        recyclerContent.adapter = DatabaseContentAdapter(dataList, activity)

        return view
    }

    fun updateContent(title: String?, id: String, newData: MutableList<Pair<String, Any>>?) {
        val jsonFile = StringBuilder(parseJson(id))
        val parser: Parser = Parser.default()
        val jsonObject: JsonObject = parser.parse(jsonFile) as JsonObject

        val type = when(jsonObject.string("type") as String) {
            "Document" -> Constants.DOCUMENT
            "Collection" -> Constants.COLLECTION
            "Home" -> Constants.HOME
            else -> Constants.FIELD
        }

        recyclerContent.adapter = DatabaseContentAdapter(newData, activity)

        // BEAUTIFUL KOTLIN -> for beginners: if (title != null) name = title else name = id
        val name = title ?: id
        val data = DataListProvider(this, activity).getData(Pair(type, id))
        pathList.add(Path(name, id, data) {
            updateContent(name, id, data)
            removeAllPathsAfter(Path(name, id, data){})
        })
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)
        recyclerPath.smoothScrollToPosition(pathList.size - 1)
    }

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

    private fun parseJson(id: String) : String { return Helper.getFileByID(id) }

    override fun onBackButtonPressed(): Boolean {
        if (pathList.size == 1)
            return false

        val path = pathList[pathList.size - 2]
        updateContent(path.name, path.ID, path.content)
        removeAllPathsAfter(path)

        return true
    }

}
