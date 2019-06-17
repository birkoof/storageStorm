package com.basic.storagestorm.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.R
import com.basic.storagestorm.adapters.DatabaseContentAdapter
import com.basic.storagestorm.models.Field
import com.basic.storagestorm.utilities.Constants
import com.basic.storagestorm.utilities.Helper
import kotlinx.android.synthetic.main.activity_about_data_object.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class AboutDataObject : AppCompatActivity() {

    private lateinit var objectID: String
    private lateinit var objectJSON: String
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_data_object)

        progressBar = findViewById(R.id.progressBar)
        objectID = intent.getStringExtra(Constants.INTENT_EXTRA_OBJECT_ID)
        executeGet(objectID)
    }

    override fun onResume() {
        super.onResume()
        executeGet(objectID)
    }

    private fun executeDelete() {
        progressBar.visibility = View.VISIBLE
        if (!Helper.hasNetworkConnection(this)) {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Please check your network.", Toast.LENGTH_LONG).show()
            return
        }
        doAsync {
            val ikarusApi = IkarusApi(Constants.UTILITIES_SERVER_URL)
            try {
                val success = ikarusApi.delete(objectID)
                Log.d("IKARUS", "deleting object $objectID - success: $success")
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(it, "Object $objectID deleted.", Toast.LENGTH_LONG).show()
                    Handler().postDelayed({
                        finish()
                    }, 1000)
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(it, "An error occurred, please try again.", Toast.LENGTH_LONG).show()
                    return@uiThread
                }
            }
        }
        finish()
    }

    private fun executeGet(objectID: String) {
        progressBar.visibility = View.VISIBLE
        if (!Helper.hasNetworkConnection(this@AboutDataObject)) {
            tvObjectID.text = "No network connection."
            btnRetry.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                executeGet(objectID)
            }
            progressBar.visibility = View.GONE
            return
        }
        btnRetry.visibility = View.GONE
        doAsync {
            val ikarusApi = IkarusApi(Constants.UTILITIES_SERVER_URL)
            try {
                objectJSON = ikarusApi.get(objectID)
                uiThread {
                    progressBar.visibility = View.GONE
                    if (objectJSON.isEmpty()) {
                        tvObjectID.text = "No results for $objectID."
                        return@uiThread
                    }

                    tvObjectID.text = "Object ID: $objectID"
                    val resultData = mutableListOf<Pair<String, Any>>()
                    resultData.add(
                        Pair(
                            Constants.CATEGORY,
                            Constants.FIELD
                        )
                    )
                    resultData.add(Pair(Constants.FIELD, Field(objectJSON)))
                    recyclerResult.adapter = DatabaseContentAdapter(resultData, it)
                }
            } catch (exception: IOException) {
                uiThread {
                    Toast.makeText(this@AboutDataObject, "An error occurred", Toast.LENGTH_LONG).show()
                    return@uiThread
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about_data_object, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuDeleteObject -> {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete object")
            builder.setMessage("Are you sure you want to delete object $objectID?")
            builder.setPositiveButton("Yes") { _, _ ->
                executeDelete()
            }
            builder.setNegativeButton("No") { _, _ -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
            true
        }
        R.id.menuEditObject -> {
            val intent = Intent(this, EditObject::class.java)
            intent.putExtra(Constants.INTENT_EXTRA_OBJECT_JSON, objectJSON)
            intent.putExtra(Constants.INTENT_EXTRA_OBJECT_ID, objectID)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
