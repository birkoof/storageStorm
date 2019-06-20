package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.widget.Toast
import at.tugraz.ikarus.api.IkarusApi
import com.basic.storagestorm.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class AddCollectionToCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text = "Insert Collection A to Collection B"
        tvFirstText.text = "ID of Collection A"
        tvSecondText.text = "ID of Collection B"
        firstInput.setText(sharedPref.getString(Constants.PREF_ADD_COLL_TO_COLL_ID_A, "s-000001"))
        secondInput.setText(sharedPref.getString(Constants.PREF_ADD_COLL_TO_COLL_ID_B, "s-000002"))
        tvSave.text = "ADD"
        tvSave.setOnClickListener {
            val sourceCollID = firstInput.text.toString()
            val targetCollID = secondInput.text.toString()
            when {
                sourceCollID.isEmpty() -> Toast.makeText(
                    this,
                    "Please enter ID for Collection A.",
                    Toast.LENGTH_SHORT
                ).show()
                targetCollID.isEmpty() -> Toast.makeText(
                    this,
                    "Please enter ID for Collection B.",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    executeInsert(sourceCollID, targetCollID)
                }
            }
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }

    private fun executeInsert(sourceCollID: String, targetCollID: String) {
        if (sourceCollID.contains("s-") && targetCollID.contains("s-")) {
            doAsync {
                try {
                    val success = IkarusApi(Constants.UTILITIES_SERVER_URL).insertColl(targetCollID, sourceCollID)
                    uiThread {
                        if (success) {
                            Toast.makeText(it, "$sourceCollID inserted into $targetCollID!", Toast.LENGTH_SHORT).show()
                            sharedPref.edit()?.run {
                                putString(Constants.PREF_ADD_COLL_TO_COLL_ID_A, sourceCollID)
                                putString(Constants.PREF_ADD_COLL_TO_COLL_ID_B, targetCollID)
                                apply()
                            }
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
