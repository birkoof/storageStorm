package com.basic.storagestorm.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.R
import com.basic.storagestorm.utilities.Constants
import com.basic.storagestorm.utilities.Helper
import kotlinx.android.synthetic.main.activity_create_object.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class CreateObject : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_object)

        progressBar = findViewById(R.id.progressBar)

        btnCreate.setOnClickListener {
            createObject()
        }
    }

    private fun createObject() {
        val json = inputContent.text.toString()
        if (!Helper.isValidJSON(json)) {
            Toast.makeText(this, "Please enter valid JSON!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Helper.hasNetworkConnection(this)) {
            Toast.makeText(this, "Please check your network!", Toast.LENGTH_SHORT).show()
            return
        }
        progressBar.visibility = View.VISIBLE
        doAsync {
            try {
                val id = IkarusApi(Constants.UTILITIES_SERVER_URL).store(json)
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(it, "Object created! ID: $id.", Toast.LENGTH_SHORT).show()
                    inputContent.text?.clear()
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(it, "An error occurred!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
