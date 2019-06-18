package com.basic.storagestorm.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.activities.AboutCollection
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class GetCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTitle.text = "Get Collection"
        tvFirstText.text = "Collection ID:"
        tvSecondText.visibility = View.GONE
        secondInput.visibility = View.GONE
        tvSave.text = "GET"
        tvSave.setOnClickListener {
            val collID = firstInput.text.toString()
            if (collID.isEmpty()) {
                Toast.makeText(this, "Please enter an collection ID.", Toast.LENGTH_SHORT).show()
            } else {
                getCollection(collID)
            }
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun getCollection(collID: String) {
        doAsync {
            try {
                val result = IkarusApi(Constants.UTILITIES_SERVER_URL).getCollBySid(collID)
                uiThread {
                    if (result.isNullOrEmpty()) {
                        Toast.makeText(it, "Please enter a valid collection ID.", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(it, AboutCollection::class.java)
                        intent.putExtra(Constants.INTENT_EXTRA_COLLECTION_ID, collID)
                        intent.putExtra(Constants.INTENT_EXTRA_COLLECTION_NAME, "TODO handle name")
                        startActivity(intent)
                        finish()
                    }
                }
            } catch (exception: IOException) {
                uiThread {
                    Toast.makeText(it, "An error occurred.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
