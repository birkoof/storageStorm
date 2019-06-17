package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_base_dialog.*

class RemoveObjectFromCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTitle.text = "Remove Object from Collection"
        tvFirstText.text = "Collection ID"
        tvSecondText.text = "Object ID"

        tvSave.text = "REMOVE"
        tvSave.setOnClickListener {
            Toast.makeText(this, "TODO remove", Toast.LENGTH_SHORT).show()
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }
}
