package com.basic.storagestorm.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.basic.storagestorm.R
import com.basic.storagestorm.activities.CreateObject
import com.basic.storagestorm.activities.MainActivity
import com.basic.storagestorm.dialogs.*
import com.basic.storagestorm.interfaces.BackpressHandler

class CreateFragment : Fragment(), BackpressHandler {

    private lateinit var btnCreateObject: Button
    private lateinit var btnDeleteObject: Button
    private lateinit var btnEditObject: Button
    private lateinit var btnAddObjectToColl: Button
    private lateinit var btnRemoveObjectFromColl: Button

    private lateinit var btnCreateCollection: Button
    private lateinit var btnDeleteCollection: Button
    private lateinit var btnAddCollToColl: Button
    private lateinit var btnGetCollection: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        btnEditObject = view.findViewById(R.id.btnEditObject)
        btnEditObject.setOnClickListener {
            startActivity(Intent(activity, EditObjectDialog::class.java))
        }

        btnCreateObject = view.findViewById(R.id.btnCreateObject)
        btnCreateObject.setOnClickListener {
            startActivity(Intent(activity, CreateObject::class.java))
        }

        btnDeleteObject = view.findViewById(R.id.btnDeleteObject)
        btnDeleteObject.setOnClickListener {
            startActivity(Intent(activity, DeleteObjectDialog::class.java))
        }

        btnAddObjectToColl = view.findViewById(R.id.btnAddObjectToColl)
        btnAddObjectToColl.setOnClickListener {
            startActivity(Intent(activity, AddObjectToCollectionDialog::class.java))
        }

        btnRemoveObjectFromColl = view.findViewById(R.id.btnRemoveObjectFromColl)
        btnRemoveObjectFromColl.setOnClickListener {
            startActivity(Intent(activity, RemoveObjectFromCollectionDialog::class.java))
        }

        btnCreateCollection = view.findViewById(R.id.btnCreateCollection)
        btnCreateCollection.setOnClickListener {
            startActivity(Intent(activity, CreateCollectionDialog::class.java))
        }

        btnDeleteCollection = view.findViewById(R.id.btnDeleteCollection)
        btnDeleteCollection.setOnClickListener {
            startActivity(Intent(activity, DeleteCollectionDialog::class.java))
        }

        btnAddCollToColl = view.findViewById(R.id.btnAddCollToColl)
        btnAddCollToColl.setOnClickListener {
            Toast.makeText(activity, "TODO", Toast.LENGTH_SHORT).show()
        }

        btnGetCollection = view.findViewById(R.id.btnGetCollection)
        btnGetCollection.setOnClickListener {
            Toast.makeText(activity, "TODO", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    companion object {
        fun newInstance() = CreateFragment()
    }

    override fun onBackButtonPressed(): Boolean {
        (activity as MainActivity).toDatabaseFragment()
        return true
    }
}
