package com.basic.storagestorm.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_base_dialog.*

class DeleteObjectDialog : BaseDialogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvTitle.text = "Delete an Object"
        tvFirstText.text = "Object ID"
        tvSecondText.visibility = View.GONE
        secondInput.visibility = View.GONE
        tvSave.text = "DELETE"
        tvSave.setOnClickListener {
            Toast.makeText(this, "TODO delete", Toast.LENGTH_SHORT).show()
        }
        tvCancel.setOnClickListener {
            finish()
        }
    }
}
