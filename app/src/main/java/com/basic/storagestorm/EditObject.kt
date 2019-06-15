package com.basic.storagestorm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import kotlinx.android.synthetic.main.activity_edit_object.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class EditObject : AppCompatActivity() {

    private lateinit var jsonString: String
    private lateinit var objectID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_object)

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
            } catch (exception: IOException) {
                Toast.makeText(this@EditObject, "Something went wrong.", Toast.LENGTH_LONG).show()
                return@doAsync
            }
            uiThread {
                progressBar.visibility = View.VISIBLE
                Toast.makeText(it, "Object updated.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
