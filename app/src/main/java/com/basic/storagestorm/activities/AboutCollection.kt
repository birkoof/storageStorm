package com.basic.storagestorm.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.R
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.DataObject
import com.basic.storagestorm.utilities.Constants
import com.basic.storagestorm.utilities.Helper
import kotlinx.android.synthetic.main.activity_about_collection.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class AboutCollection : AppCompatActivity() {

    private lateinit var displayString: String
    private lateinit var collectionID: String
    private lateinit var collectionName: String
    private lateinit var refreshContentReceiver: RefreshContentReceiver
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_collection)

        progressBar = findViewById(R.id.progressBar)
        collectionID = intent.getStringExtra(Constants.INTENT_EXTRA_COLLECTION_ID)

        displayString = "Displaying collection $collectionID"
        tvResultFor.text = displayString
    }

    override fun onResume() {
        super.onResume()
        refreshContentReceiver = RefreshContentReceiver()
        registerReceiver(refreshContentReceiver, IntentFilter(Constants.REFRESH_DATA))
        executeGet(collectionID, collectionName)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(refreshContentReceiver)
    }

    private fun executeGet(collID: String, name: String) {
        progressBar.visibility = View.VISIBLE
        if (!Helper.hasNetworkConnection(this)) {
            tvResultFor.text = "No network connection."
            btnRetry.visibility = View.VISIBLE
            recyclerResult.visibility = View.GONE
            btnRetry.setOnClickListener {
                executeGet(collID, name)
            }
            progressBar.visibility = View.GONE
            return
        }
        btnRetry.visibility = View.GONE
        doAsync {
            try {
                val list = IkarusApi(Constants.UTILITIES_SERVER_URL).getCollBySid(collID)?.toMutableList()
                uiThread {
                    progressBar.visibility = View.GONE
                    tvResultFor.text = displayString

                    if (list.isNullOrEmpty()) {
                        tvResultFor.text = "No results for $collID."
                        return@uiThread
                    }

                    val resultData = mutableListOf<Pair<String, Any>>()
                    val collectionList = mutableListOf<String>()
                    val objectList = mutableListOf<String>()

                    list.forEach {
                        if (it.contains("s-")) collectionList.add(it)
                        else objectList.add(it)
                    }

                    if (collectionList.isNotEmpty()) {
                        resultData.add(Pair(Constants.CATEGORY, Constants.COLLECTION))
                        collectionList.forEach {
                            val split = it.split("(")
                            val collName = split[0]
                            val id = split[1].removeSuffix(")")

                            resultData.add(Pair(Constants.COLLECTION, Collection(collName, id) {
                                val intent = Intent(this@AboutCollection, AboutCollection::class.java)
                                intent.putExtra(Constants.INTENT_EXTRA_COLLECTION_ID, id)
                                intent.putExtra(Constants.INTENT_EXTRA_COLLECTION_NAME, collName)
                                startActivity(intent)
                            }))
                        }
                    }

                    if (objectList.isNotEmpty()) {
                        resultData.add(Pair(Constants.CATEGORY, Constants.DATA_OBJECT))
                        objectList.forEach {
                            resultData.add(Pair(Constants.DATA_OBJECT, DataObject(it) {
                                val intent = Intent(this@AboutCollection, AboutDataObject::class.java)
                                intent.putExtra(Constants.INTENT_EXTRA_OBJECT_ID, it)
                                startActivity(intent)
                            }))
                        }
                    }

                    recyclerResult.visibility = View.VISIBLE
                    recyclerResult.adapter = DatabaseContentAdapter(resultData, this@AboutCollection)
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(it, "An error occurred", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class RefreshContentReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            executeGet(collectionID, collectionName)
        }
    }

}
