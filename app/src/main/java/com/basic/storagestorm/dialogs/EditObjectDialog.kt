package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_base_dialog.*

class EditObjectDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text = "Edit an Object"
        tvFirstText.text = "Object ID"
        tvSecondText.visibility = View.GONE
        secondInput.visibility = View.GONE
        tvSave.text = "EDIT"
        tvSave.setOnClickListener {
            Toast.makeText(this, "TODO edit", Toast.LENGTH_SHORT).show()
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }
}
