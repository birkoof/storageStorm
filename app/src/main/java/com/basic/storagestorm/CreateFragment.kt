package com.basic.storagestorm

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import at.tugraz.ikarus.api.IkarusApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class CreateFragment : Fragment(), BackpressHandler {

    private lateinit var rbObject: RadioButton
    private lateinit var rbCollection: RadioButton
    private lateinit var rbAddToColl: RadioButton

    private lateinit var clAddObject: ConstraintLayout
    private lateinit var tvObjectContent: TextView
    private lateinit var inputContent: TextInputEditText
    private lateinit var tvCreateObject: TextView

    private lateinit var clAddToCollection: ConstraintLayout
    private lateinit var inputCollectionID: TextInputEditText
    private lateinit var insertID: TextInputEditText
    private lateinit var btnAdd: Button

    private lateinit var clNoNetwork: ConstraintLayout
    private lateinit var btnRetry: Button

    private lateinit var clAddCollection: ConstraintLayout
    private lateinit var inputCollName: TextInputEditText
    private lateinit var inputHeadID: TextInputEditText
    private lateinit var btnCreateColl: Button

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        rbObject = view.findViewById(R.id.rbObject)
        rbCollection = view.findViewById(R.id.rbCollection)
        rbAddToColl = view.findViewById(R.id.rbAddToColl)

        clAddCollection = view.findViewById(R.id.clAddCollection)
        inputCollName = view.findViewById(R.id.inputCollName)
        inputHeadID = view.findViewById(R.id.inputHeadID)
        btnCreateColl = view.findViewById(R.id.btnCreateColl)

        clNoNetwork = view.findViewById(R.id.clNoNetwork)
        btnRetry = view.findViewById(R.id.btnRetry)

        clAddObject = view.findViewById(R.id.clAddObject)
        tvObjectContent = view.findViewById(R.id.tvObjectContent)
        inputContent = view.findViewById(R.id.inputContent)
        tvCreateObject = view.findViewById(R.id.tvCreateObject)

        clAddToCollection = view.findViewById(R.id.clAddToCollection)
        inputCollectionID = view.findViewById(R.id.inputID)
        insertID = view.findViewById(R.id.insertID)
        btnAdd = view.findViewById(R.id.btnAddToCollection)

        progressBar = view.findViewById(R.id.progressBar)

        return view
    }

    override fun onStart() {
        super.onStart()
        rbCollection.setOnClickListener {
            handleSelection()
        }
        rbObject.setOnClickListener {
            handleSelection()
        }
        rbAddToColl.setOnClickListener {
            handleSelection()
        }
        handleSelection()
    }

    private fun handleSelection() {
        if (!Helper.hasNetworkConnection(activity)) {
            clAddObject.visibility = View.GONE
            clAddCollection.visibility = View.GONE
            clNoNetwork.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                retry()
            }
        } else {
            clNoNetwork.visibility = View.GONE
            when {
                rbCollection.isChecked -> {
                    clAddObject.visibility = View.GONE
                    clAddToCollection.visibility = View.GONE
                    clAddCollection.visibility = View.VISIBLE
                    btnCreateColl.setOnClickListener {
                        createCollection()
                    }
                }
                rbObject.isChecked -> {
                    clAddCollection.visibility = View.GONE
                    clAddToCollection.visibility = View.GONE
                    clAddObject.visibility = View.VISIBLE
                    tvCreateObject.setOnClickListener {
                        createObject()
                    }
                }
                else -> {
                    clAddCollection.visibility = View.GONE
                    clAddObject.visibility = View.GONE
                    clAddToCollection.visibility = View.VISIBLE
                    btnAdd.setOnClickListener {
                        addToCollection()
                    }
                }
            }
        }
    }

    private fun retry() {
        handleSelection()
    }

    private fun createCollection() {
        progressBar.visibility = View.VISIBLE
        val collName = inputCollName.text.toString()
        val headID = inputHeadID.text.toString()
        var collID = ""
        doAsync {
            try {
                val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
                collID = ikarus.makeColl(headID, collName)
                uiThread {
                    progressBar.visibility = View.GONE
                    if (collID.isEmpty()) {
                        Toast.makeText(activity, "Something went wrong.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Success! Collection ID: $collID", Toast.LENGTH_SHORT).show()
                        inputCollName.text?.clear()
                        inputHeadID.text?.clear()
                    }
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "Error! Please try again.", Toast.LENGTH_SHORT).show()
                    return@uiThread
                }
            } catch (exception: IllegalStateException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "Error! Check ID format", Toast.LENGTH_SHORT).show()
                    return@uiThread
                }
            }
        }
    }

    private fun addToCollection() {
        progressBar.visibility = View.VISIBLE
        val collID = inputCollectionID.text.toString()
        val ID = insertID.text.toString()
        doAsync {
            try {
                val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
                ikarus.insertColl(collID, ID)
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "Success! $ID inserted to $collID.", Toast.LENGTH_SHORT).show()
                    inputCollectionID.text?.clear()
                    insertID.text?.clear()
                }
            } catch (exception: IOException) {
                uiThread {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, "Error! Check ID format", Toast.LENGTH_SHORT).show()
                    return@uiThread
                }
            } // TODo ilegal state exception = API Update
        }
    }

    private fun createObject() {
        val json = inputContent.text.toString()
        if (!Helper.isValidJSON(json)) {
            Toast.makeText(activity, "JSON is not valid!", Toast.LENGTH_SHORT).show()
        } else {
            progressBar.visibility = View.VISIBLE
            doAsync {
                val ikarus = IkarusApi(Constants.UTILITIES_SERVER_URL)
                try {
                    val objectID = ikarus.store(json)
                    uiThread {
                        Toast.makeText(activity, "Object created. ID: $objectID", Toast.LENGTH_LONG).show()
                        inputContent.text!!.clear()
                        progressBar.visibility = View.GONE
                    }
                } catch (exception: IOException) {
                    uiThread {
                        Toast.makeText(activity, "An error occurred", Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = CreateFragment()
    }

    override fun onBackButtonPressed(): Boolean {
        (activity as MainActivity).toDatabaseFragment()
        return true
    }
}
