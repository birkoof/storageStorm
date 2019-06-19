package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class CreateCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTitle.text = "Create Collection"
        tvFirstText.text = "Collection name"
        tvSecondText.text = "Head ID"

        tvSave.text = "CREATE"
        tvSave.setOnClickListener {
            when {
                firstInput.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Please enter collection name!", Toast.LENGTH_SHORT).show()
                }
                secondInput.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Please enter a head ID!", Toast.LENGTH_SHORT).show()
                }
                else -> executeCreate(firstInput.text.toString(), secondInput.text.toString())
            }
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun executeCreate(name: String, headID: String) {
        doAsync {
            try {
                val ret = IkarusApi(Constants.UTILITIES_SERVER_URL).makeColl(headID, name)
                uiThread {
                    if (ret.isNullOrEmpty()) {
                        Toast.makeText(it, "Please enter valid Head ID", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(it, "Collection \"$name\" created. ID: $ret!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } catch (exception: IOException) {
                uiThread {
                    Toast.makeText(it, "Network error!", Toast.LENGTH_SHORT).show()
                }
            } catch (exception: NullPointerException) {
                uiThread {
                    Toast.makeText(it, "Please enter valid Head ID", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
