package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class DeleteObjectDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text = "Delete an Object"
        tvFirstText.text = "Object ID"
        tvSecondText.visibility = View.GONE
        secondInput.visibility = View.GONE
        tvSave.text = "DELETE"
        tvSave.setOnClickListener {
            executeDelete(firstInput.text.toString())
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun executeDelete(objectID: String) {
        doAsync {
            try {
                IkarusApi(Constants.UTILITIES_SERVER_URL).delete(objectID)
                uiThread {
                    Toast.makeText(it, "$objectID deleted!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (exception: IOException) {
                uiThread {
                    Toast.makeText(it, "Network error!", Toast.LENGTH_SHORT).show()
                }
            } catch (exception: IllegalStateException) {
                uiThread {
                    Toast.makeText(it, "Please enter valid Object ID", Toast.LENGTH_SHORT).show()
                }
            } catch (exception: NullPointerException) {
                uiThread {
                    Toast.makeText(it, "Please enter valid Object ID", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
