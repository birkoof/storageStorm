package com.basic.storagestorm.dialogs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.basic.storagestorm.R

open class BaseDialogActivity : AppCompatActivity() {

    private lateinit var tvCancel: TextView
    private lateinit var tvSave: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvFirstText: TextView
    private lateinit var tvSecondText: TextView
    private lateinit var firstInput: TextInputEditText
    private lateinit var secondInput: TextInputEditText
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_dialog)

        tvCancel = findViewById(R.id.tvCancel)
        tvSave = findViewById(R.id.tvSave)
        tvTitle = findViewById(R.id.tvTitle)
        tvFirstText = findViewById(R.id.tvFirstText)
        tvSecondText = findViewById(R.id.tvSecondText)
        firstInput = findViewById(R.id.firstInput)
        secondInput = findViewById(R.id.secondInput)

        sharedPref = getPreferences(Context.MODE_PRIVATE)
    }
}
