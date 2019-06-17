package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_base_dialog.*

class AddObjectToCollectionDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTitle.text = "Add Object to Collection"
        tvFirstText.text = "Collection ID"
        tvSecondText.text = "Object ID"

        tvSave.text = "ADD"
        tvSave.setOnClickListener {
            Toast.makeText(this, "TODO save", Toast.LENGTH_SHORT).show()
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }
}
