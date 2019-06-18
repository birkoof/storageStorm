package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class AddObjectToCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTitle.text = "Add Object to Collection"
        tvFirstText.text = "Collection ID"
        tvSecondText.text = "Object ID"

        tvSave.text = "ADD"
        tvSave.setOnClickListener {
            executeInsert(firstInput.text.toString(), secondInput.text.toString())
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun executeInsert(collID: String, objID: String) {
        doAsync {
            try {
                val success = IkarusApi(Constants.UTILITIES_SERVER_URL).insertColl(collID, objID)
                uiThread {
                    if (success) {
                        Toast.makeText(it, "$objID inserted into $collID!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(it, "Please enter valid Collection/Object ID", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (exception: IOException) {
                uiThread {
                    Toast.makeText(it, "Network error!", Toast.LENGTH_SHORT).show()
                }
            } catch (exception: NullPointerException) {
                uiThread {
                    Toast.makeText(it, "Please enter valid Collection/Object ID", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
