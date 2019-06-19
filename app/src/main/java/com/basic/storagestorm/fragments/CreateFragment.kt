package com.basic.storagestorm.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.basic.storagestorm.R
import com.basic.storagestorm.activities.CreateObject
import com.basic.storagestorm.activities.MainActivity
import com.basic.storagestorm.dialogs.*
import com.basic.storagestorm.interfaces.BackpressHandler
import com.basic.storagestorm.utilities.Constants

class CreateFragment : Fragment(), BackpressHandler {

    private lateinit var btnCreate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnGedit: Button
    private lateinit var btnAddToColl: Button
    private lateinit var btnRemoveFromColl: Button
    private lateinit var btnResetDatabase: Button

    private lateinit var rbCollections: RadioButton
    private lateinit var rbObjects: RadioButton

    private val COLL = 0
    private val OBJ = 1

    private var currentlyChecked = COLL

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        rbCollections = view.findViewById(R.id.rbCollections)
        rbObjects = view.findViewById(R.id.rbObjects)

        rbCollections.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btnGedit.text = "GET"
                btnAddToColl.text = "Add coll to coll"
                btnRemoveFromColl.text = "Remove coll from coll"
                currentlyChecked = COLL
            }
        }

        rbObjects.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                btnGedit.text = "EDIT"
                btnAddToColl.text = "Add object to coll"
                btnRemoveFromColl.text = "Remove object from coll"
                currentlyChecked = OBJ
            }
        }

        btnGedit = view.findViewById(R.id.btnEditObject)
        btnGedit.setOnClickListener {
            if (currentlyChecked == OBJ) {
                startActivity(Intent(activity, EditObjectDialog::class.java))
            } else {
                startActivity(Intent(activity, GetCollectionDialog::class.java))
            }
        }

        btnCreate = view.findViewById(R.id.btnCreateObject)
        btnCreate.setOnClickListener {
            if (currentlyChecked == OBJ) {
                startActivity(Intent(activity, CreateObject::class.java))
            } else {
                startActivity(Intent(activity, CreateCollectionDialog::class.java))
            }
        }

        btnDelete = view.findViewById(R.id.btnDeleteObject)
        btnDelete.setOnClickListener {
            if (currentlyChecked == OBJ) {
                startActivity(Intent(activity, DeleteObjectDialog::class.java))
            } else {
                startActivity(Intent(activity, DeleteCollectionDialog::class.java))
            }
        }

        btnAddToColl = view.findViewById(R.id.btnAddObjectToColl)
        btnAddToColl.setOnClickListener {
            if (currentlyChecked == OBJ) {
                startActivity(Intent(activity, AddObjectToCollectionDialog::class.java))
            } else {
                startActivity(Intent(activity, AddCollectionToCollectionDialog::class.java))
            }
        }

        btnRemoveFromColl = view.findViewById(R.id.btnRemoveObjectFromColl)
        btnRemoveFromColl.setOnClickListener {
            if (currentlyChecked == OBJ) {
                startActivity(Intent(activity, RemoveObjectFromCollectionDialog::class.java))
            } else {
                startActivity(Intent(activity, RemoveCollectionFromCollectionDialog::class.java))
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_create_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Toast.makeText(activity, "Syncing...", Toast.LENGTH_SHORT).show()
        activity?.sendBroadcast(Intent(Constants.REFRESH_DATA))
        return true
    }

    companion object {
        fun newInstance() = CreateFragment()
    }

    override fun onBackButtonPressed(): Boolean {
        (activity as MainActivity).toDatabaseFragment()
        return true
    }
}
