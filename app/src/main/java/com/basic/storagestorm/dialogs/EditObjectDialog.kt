package com.basic.storagestorm.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.activities.EditObject
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class EditObjectDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text = "Edit an Object"
        tvFirstText.text = "Object ID"
        tvSecondText.visibility = View.GONE
        secondInput.visibility = View.GONE
        firstInput.setText(sharedPref.getString(Constants.PREF_EDIT_OBJ_OBJ_ID, "000001"))
        tvSave.text = "EDIT"
        tvSave.setOnClickListener {
            if (firstInput.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter an Object ID", Toast.LENGTH_SHORT).show()
            } else {
                validateObjectID(firstInput.text.toString())
            }
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun validateObjectID(objectID: String) {
        doAsync {
            try {
                val json = IkarusApi(Constants.UTILITIES_SERVER_URL).get(objectID)
                uiThread {
                    if (json.isNullOrEmpty()) {
                        Toast.makeText(it, "Please enter valid Object ID", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(it, EditObject::class.java)
                        intent.putExtra(Constants.INTENT_EXTRA_OBJECT_ID, objectID)
                        intent.putExtra(Constants.INTENT_EXTRA_OBJECT_JSON, json)
                        startActivity(intent)
                        sharedPref.edit()?.run {
                            putString(Constants.PREF_EDIT_OBJ_OBJ_ID, objectID)
                            apply()
                        }
                        finish()
                    }
                }
            } catch (exception: IllegalStateException) {
                uiThread {
                    Toast.makeText(it, "Please enter valid Object ID", Toast.LENGTH_SHORT).show()
                }
            } catch (exception: IOException) {
                uiThread {
                    Toast.makeText(it, "Network error.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
