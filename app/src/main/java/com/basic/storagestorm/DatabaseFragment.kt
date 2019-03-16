package com.basic.storagestorm

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class DatabaseFragment : Fragment() {

    private val pathList = mutableListOf<String>()
    private val collectionList = mutableListOf<Pair<Int, Any>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_database, container, false)

        // TODO feed data legally
        pathList.add("Home")
        val recyclerPath = view.findViewById<RecyclerView>(R.id.recyclerPath)
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)

        collectionList.add(Pair(Constants.CATEGORY, "collections"))
        collectionList.add(Pair(Constants.COLLECTION, Collection("Customers")))
        collectionList.add(Pair(Constants.COLLECTION, Collection("Cities")))
        collectionList.add(Pair(Constants.COLLECTION, Collection("Destinations")))
        collectionList.add(Pair(Constants.CATEGORY, "testing"))
        collectionList.add(Pair(Constants.COLLECTION, Collection("Attractions")))
        collectionList.add(Pair(Constants.COLLECTION, Collection("Hotels")))

        val recyclerContent = view.findViewById<RecyclerView>(R.id.recyclerContent)
        recyclerContent.adapter = DatabaseContentAdapter(collectionList, activity)

        return view
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = DatabaseFragment()
    }
}
