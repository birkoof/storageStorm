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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_database, container, false)

        // TODO feed path legally
        pathList.add("Just")
        pathList.add("Testing")
        pathList.add("Path")
        pathList.add("Recycler")
        pathList.add("View")
        pathList.add("Just2")
        pathList.add("Testing2")
        pathList.add("Path")
        pathList.add("Path")
        pathList.add("Recycler2")
        pathList.add("View2")

        val recyclerPath = view.findViewById<RecyclerView>(R.id.recyclerPath)
        recyclerPath.adapter = DatabasePathAdapter(pathList, activity)
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
