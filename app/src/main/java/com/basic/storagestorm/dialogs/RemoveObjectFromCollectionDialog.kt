package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class RemoveObjectFromCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTitle.text = "Remove Object from Collection"
        tvFirstText.text = "Collection ID"
        tvSecondText.text = "Object ID"

        tvSave.text = "REMOVE"
        tvSave.setOnClickListener {
            val coll = firstInput.text.toString()
            val obj = secondInput.text.toString()
            when {
                coll.isEmpty() -> Toast.makeText(
                    this,
                    "Please enter Collection ID.",
                    Toast.LENGTH_SHORT
                ).show()
                obj.isEmpty() -> Toast.makeText(
                    this,
                    "Please enter Object ID.",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    executeRemove(coll, obj)
                }
            }
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun executeRemove(collID: String, objID: String) {
        if (objID.contains("s-")) {
            Toast.makeText(this, "Please enter valid Object ID", Toast.LENGTH_SHORT).show()
            return
        }
        doAsync {
            try {
                val success = IkarusApi(Constants.UTILITIES_SERVER_URL).removeColl(collID, objID)
                uiThread {
                    if (success) {
                        Toast.makeText(it, "$objID deleted from $collID!", Toast.LENGTH_SHORT).show()
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
