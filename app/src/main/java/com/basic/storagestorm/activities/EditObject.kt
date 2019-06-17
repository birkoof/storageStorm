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
import kotlinx.android.synthetic.main.activity_edit_object.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class EditObject : AppCompatActivity() {

    private lateinit var jsonString: String
    private lateinit var objectID: String
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_object)

        progressBar = findViewById(R.id.progressBar)

        jsonString = intent.getStringExtra(Constants.INTENT_EXTRA_OBJECT_JSON)
        editText.setText(jsonString)

        objectID = intent.getStringExtra(Constants.INTENT_EXTRA_OBJECT_ID)
        tvObjectID.text = "Object ID: $objectID"

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val editedJson = editText.text.toString()
            if (Helper.isValidJSON(editedJson)) {
                Toast.makeText(this, "TODO update API!", Toast.LENGTH_LONG).show()
//                executeUpdate(editedJson)
            } else {
                Toast.makeText(this, "Json is not valid!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun executeUpdate(editedJson: String) {
        progressBar.visibility = View.VISIBLE
        if (!Helper.hasNetworkConnection(this)) {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Please check your network connection.", Toast.LENGTH_LONG).show()
            return
        }
        doAsync {
            val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
            try {
                // TODO update API
//                ikarus.update(objectID, editedJson)
                uiThread {
                    progressBar.visibility = View.VISIBLE
                    Toast.makeText(it, "Object updated.", Toast.LENGTH_LONG).show()
                    finish()
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@EditObject, "Something went wrong.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
