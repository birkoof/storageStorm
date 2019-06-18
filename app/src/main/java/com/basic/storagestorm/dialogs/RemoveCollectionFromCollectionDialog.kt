package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class RemoveCollectionFromCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTitle.text = "Remove Collection A from Collection B"
        tvFirstText.text = "ID of Collection A"
        tvSecondText.text = "ID of Collection B"

        tvSave.text = "REMOVE"
        tvSave.setOnClickListener {
            val collA = firstInput.text.toString()
            val collB = secondInput.text.toString()
            when {
                collA.isEmpty() -> Toast.makeText(
                    this,
                    "Please enter ID for Collection A.",
                    Toast.LENGTH_SHORT
                ).show()
                collB.isEmpty() -> Toast.makeText(
                    this,
                    "Please enter ID for Collection B.",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    executeRemove(collA, collB)
                }
            }
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun executeRemove(collA: String, collB: String) {
        if (collA.contains("s-") && collB.contains("s-")) {
            doAsync {
                try {
                    val success = IkarusApi(Constants.UTILITIES_SERVER_URL).removeColl(collB, collA)
                    uiThread {
                        if (success) {
                            Toast.makeText(it, "$collA removed from $collB!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(it, "Please enter valid Collection ID", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (exception: IOException) {
                    uiThread {
                        Toast.makeText(it, "Network error!", Toast.LENGTH_SHORT).show()
                    }
                } catch (exception: NullPointerException) {
                    uiThread {
                        Toast.makeText(it, "Please enter valid Collection ID", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please enter valid source/target collection ID", Toast.LENGTH_SHORT).show()
        }
    }
}
