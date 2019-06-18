package com.basic.storagestorm.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.basic.storagestorm.R
import com.basic.storagestorm.activities.CreateObject
import com.basic.storagestorm.activities.MainActivity
import com.basic.storagestorm.dialogs.AddObjectToCollectionDialog
import com.basic.storagestorm.dialogs.DeleteObjectDialog
import com.basic.storagestorm.dialogs.EditObjectDialog
import com.basic.storagestorm.dialogs.RemoveObjectFromCollectionDialog
import com.basic.storagestorm.interfaces.BackpressHandler

class CreateFragment : Fragment(), BackpressHandler {

    private lateinit var progressBar: ProgressBar
    private lateinit var btnCreateObject: Button
    private lateinit var btnDeleteObject: Button
    private lateinit var btnEditObject: Button
    private lateinit var btnAddObjectToColl: Button
    private lateinit var btnRemoveObjectFromColl: Button

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

        return view
    }

//    private fun createCollection() {
//        progressBar.visibility = View.VISIBLE
//        val collName = inputCollName.text.toString()
//        val headID = inputHeadID.text.toString()
//        var collID = ""
//        doAsync {
//            try {
//                val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
//                collID = ikarus.makeColl(headID, collName)
//                uiThread {
//                    progressBar.visibility = View.GONE
//                    if (collID.isEmpty()) {
//                        Toast.makeText(activity, "Something went wrong.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(activity, "Success! Collection ID: $collID", Toast.LENGTH_SHORT).show()
//                        inputCollName.text?.clear()
//                        inputHeadID.text?.clear()
//                    }
//                }
//            } catch (exception: IOException) {
//                uiThread {
//                    progressBar.visibility = View.GONE
//                    Toast.makeText(activity, "Error! Please try again.", Toast.LENGTH_SHORT).show()
//                    return@uiThread
//                }
//            } catch (exception: IllegalStateException) {
//                uiThread {
//                    progressBar.visibility = View.GONE
//                    Toast.makeText(activity, "Error! Check ID format", Toast.LENGTH_SHORT).show()
//                    return@uiThread
//                }
//            }
//        }
//    }
//
//    private fun addToCollection() {
//        progressBar.visibility = View.VISIBLE
//        val collID = inputCollectionID.text.toString()
//        val ID = insertID.text.toString()
//        doAsync {
//            try {
//                val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
//                ikarus.insertColl(collID, ID)
//                uiThread {
//                    progressBar.visibility = View.GONE
//                    Toast.makeText(activity, "Success! $ID inserted to $collID.", Toast.LENGTH_SHORT).show()
//                    inputCollectionID.text?.clear()
//                    insertID.text?.clear()
//                }
//            } catch (exception: IOException) {
//                uiThread {
//                    progressBar.visibility = View.GONE
//                    Toast.makeText(activity, "Error! Check ID format", Toast.LENGTH_SHORT).show()
//                    return@uiThread
//                }
//            } // TODo ilegal state exception = API Update
//        }
//    }
//
//    private fun createObject() {
//        val json = inputContent.text.toString()
//        if (!Helper.isValidJSON(json)) {
//            Toast.makeText(activity, "JSON is not valid!", Toast.LENGTH_SHORT).show()
//        } else {
//            progressBar.visibility = View.VISIBLE
//            doAsync {
//                val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
//                try {
//                    val objectID = ikarus.store(json)
//                    uiThread {
//                        Toast.makeText(activity, "Object created. ID: $objectID", Toast.LENGTH_LONG).show()
//                        inputContent.text!!.clear()
//                        progressBar.visibility = View.GONE
//                    }
//                } catch (exception: IOException) {
//                    uiThread {
//                        Toast.makeText(activity, "An error occurred", Toast.LENGTH_LONG).show()
//                        progressBar.visibility = View.GONE
//                    }
//                }
//            }
//        }
//    }

    companion object {
        fun newInstance() = CreateFragment()
    }

    override fun onBackButtonPressed(): Boolean {
        (activity as MainActivity).toDatabaseFragment()
        return true
    }
}
