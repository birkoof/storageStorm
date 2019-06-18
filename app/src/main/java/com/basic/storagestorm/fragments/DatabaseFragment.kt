package com.basic.storagestorm.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.R
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.adapters.DatabasePathAdapter
import com.basic.storagestorm.interfaces.BackpressHandler
import com.basic.storagestorm.models.DataObject
import com.basic.storagestorm.models.Field
import com.basic.storagestorm.models.Path
import com.basic.storagestorm.utilities.Constants
import com.basic.storagestorm.utilities.Helper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException


class DatabaseFragment : Fragment(), BackpressHandler {

    private var pathList = mutableListOf<Path>()
    private lateinit var recyclerContent : RecyclerView
    private lateinit var recyclerPath : RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvNoConnection: TextView
    private lateinit var btnRetry: Button
    private lateinit var lastUsed: Pair<String, String>
    private lateinit var refreshContentReceiver: RefreshContentReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_database, container, false)

        pathList.add(
            Path(
                Constants.HOME,
                Constants.HOME,
                Constants.HOME
            ) {
                updateContent(
                    Constants.HOME,
                    Constants.HOME,
                    Constants.HOME
                )
                removeAllPathsAfter(Path(
                    Constants.HOME,
                    Constants.HOME,
                    Constants.HOME
                ) {})
        })

        tvNoConnection = view.findViewById(R.id.tvNoConnection)
        btnRetry = view.findViewById(R.id.btnRetry)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerContent = view.findViewById(R.id.recyclerContent)
        recyclerPath = view.findViewById(R.id.recyclerPath)
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)
        lastUsed = Pair(
            Constants.HOME,
            Constants.HOME
        )
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        refreshContentReceiver = RefreshContentReceiver()
        activity?.registerReceiver(refreshContentReceiver, IntentFilter(Constants.REFRESH_DATA))
    }

    override fun onStart() {
        super.onStart()
        getAllCollections()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(refreshContentReceiver)
    }

    private fun getAllCollections() {
        progressBar.visibility = View.VISIBLE
        recyclerContent.visibility = View.GONE
        if (!Helper.hasNetworkConnection(activity?.applicationContext)) {
            progressBar.visibility = View.GONE
            tvNoConnection.visibility = View.VISIBLE
            tvNoConnection.text = "No network connection."
            btnRetry.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                getAllCollections()
            }
            return
        } else {
            tvNoConnection.visibility = View.GONE
            btnRetry.visibility = View.GONE
        }

        doAsync {
            try {
                // TODO replace with getAll
                val list = IkarusApi(Constants.UTILITIES_SERVER_URL).getCollBySid("s-000008").toMutableList()
                uiThread {
                    progressBar.visibility = View.GONE
                    val resultData = mutableListOf<Pair<String, Any>>()
                    // TODO change this
                    resultData.add(
                        Pair(
                            Constants.CATEGORY,
                            Constants.DATA_OBJECT
                        )
                    )
                    if (list.isNullOrEmpty()) {
                        Toast.makeText(activity, "No data.", Toast.LENGTH_LONG).show()
                        return@uiThread
                    }
                    list.forEach {
                        // TODO add first all collection and then all objects - waiting for API update
                        resultData.add(Pair(Constants.DATA_OBJECT, DataObject(it) {
                            this@DatabaseFragment.updateContent(
                                null, it,
                                Constants.DATA_OBJECT
                            )
                        }))
                    }
                    recyclerContent.visibility = View.VISIBLE
                    recyclerContent.adapter = DatabaseContentAdapter(resultData, activity)
                    lastUsed = Pair(
                        Constants.HOME,
                        Constants.HOME
                    )
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "An error occurred", Toast.LENGTH_LONG).show()
                    tvNoConnection.visibility = View.VISIBLE
                    tvNoConnection.text = "An error occurred."
                    btnRetry.visibility = View.VISIBLE
                    btnRetry.setOnClickListener {
                        getAllCollections()
                    }
                    return@uiThread
                }
            } catch (exception: IllegalStateException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "An error occurred", Toast.LENGTH_LONG).show()
                    tvNoConnection.visibility = View.VISIBLE
                    tvNoConnection.text = "An error occurred."
                    btnRetry.visibility = View.VISIBLE
                    btnRetry.setOnClickListener {
                        getAllCollections()
                    }
                    return@uiThread
                }
            }
        }
    }

    private fun getObjectData(objectID: String) {
        progressBar.visibility = View.VISIBLE
        recyclerContent.visibility = View.GONE
        if (!Helper.hasNetworkConnection(activity?.applicationContext)) {
            progressBar.visibility = View.GONE
            tvNoConnection.visibility = View.VISIBLE
            btnRetry.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                getObjectData(objectID)
            }
            return
        } else {
            tvNoConnection.visibility = View.GONE
            btnRetry.visibility = View.GONE
        }
        doAsync {
            val objectJSON: String?
            val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
            try {
                objectJSON = ikarus.get(objectID)
                uiThread {
                    progressBar.visibility = View.GONE
                    if (objectJSON.isNullOrEmpty()) {
                        Toast.makeText(activity, "No data.", Toast.LENGTH_LONG).show()
                        return@uiThread
                    }
                    val resultData = mutableListOf<Pair<String, Any>>()
                    resultData.add(
                        Pair(
                            Constants.CATEGORY,
                            Constants.FIELD
                        )
                    )
                    resultData.add(Pair(Constants.FIELD, Field(objectJSON)))
                    recyclerContent.visibility = View.VISIBLE
                    recyclerContent.adapter = DatabaseContentAdapter(resultData, activity)
                    lastUsed = Pair(Constants.DATA_OBJECT, objectID)
                }
            } catch (exception: IOException) {
                progressBar.visibility = View.GONE
                uiThread {
                    Toast.makeText(activity, "An error occurred", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateView()
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
        when (type) {
            Constants.HOME -> getAllCollections()
//            Constants.COLLECTION -> getCollectionData(id)
            Constants.COLLECTION -> {
                Toast.makeText(activity, "TODO COLLECTION", Toast.LENGTH_LONG).show()
            }
            Constants.DATA_OBJECT -> getObjectData(id)
        }

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

    override fun onBackButtonPressed(): Boolean {
        if (pathList.size == 1)
            return false

        val path = pathList[pathList.size - 2]
        updateContent(path.name, path.ID, path.type)
        removeAllPathsAfter(path)

        return true
    }

    private fun updateView() {
        when (lastUsed.first) {
            Constants.HOME -> getAllCollections()
            Constants.DATA_OBJECT -> getObjectData(lastUsed.second)
            // TODO collection
        }
    }

    inner class RefreshContentReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateView()
        }
    }
}
