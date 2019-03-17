package com.basic.storagestorm.providers
import com.basic.storagestorm.Constants
import com.basic.storagestorm.DatabaseFragment
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.Documents


class DataListProvider(val fragment: DatabaseFragment) {

    fun getData(id: String) : MutableList<Pair<Int, Any>> {
        return when (id) {
            Constants.HOME -> getHomeData()
            else -> feedDummyData(id)
        }
    }

    private fun getHomeData() : MutableList<Pair<Int, Any>> {

        val list = mutableListOf<Pair<Int, Any>>()

        list.add(Pair(Constants.CATEGORY, "collections"))
        list.add(Pair(Constants.COLLECTION, Collection("Customers", getData("Customers")) {
            fragment.updateContent("Customers", "Customers", getData("Customers"))
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Cities", getData("Cities")) {
            fragment.updateContent("Cities", "Cities", getData("Cities"))
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Destinations", getData("Destinations")) {
            fragment.updateContent("Destinations", "Destinations", getData("Cities"))
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Something", getData("Something")) {
            fragment.updateContent("Something", "Something", getData("Something"))
        }))
        return list
    }


    private fun feedDummyData(id: String) : MutableList<Pair<Int, Any>> {
        val newList = mutableListOf<Pair<Int, Any>>()

        // TODO feed from server
        return when (id) {
            "Customers" -> {
                newList.add(Pair(Constants.CATEGORY, "documents"))
                newList.add(Pair(Constants.COLLECTION, Collection("Cities", getData("Cities")) {
                   fragment.updateContent("Cities", "Cities", getData("Cities"))
                }))
                newList.add(Pair(Constants.CATEGORY, "documents"))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Eman Basic")))
                newList.add(Pair(Constants.DOCUMENT, Documents("LVc28e3gaca4Sg5fdgsIp", null)))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Batman")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Oriflame")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Smoki")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Tops")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Jadro")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "KFC")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Subway")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Swing Kitchen")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Tribeka")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Enough")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "of Dummy")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Names")))
                newList
            }
            "Cities" -> {
                newList.add(Pair(Constants.CATEGORY, "documents"))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "New York")))
                newList.add(Pair(Constants.DOCUMENT, Documents("LVc28e3gaca4Sg5fdgsIp", null)))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Chicago")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "New Orleans")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Berlin")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Vienna")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Graz")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Sarajevo")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Mrkonjic Grad")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Velika Kladusa")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Aspartan")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Ubija")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Nemojte piti")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Coca Colu")))
                newList
            }
            "Destinations" -> {
                newList.add(Pair(Constants.CATEGORY, "documents"))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "North")))
                newList.add(Pair(Constants.DOCUMENT, Documents("LVc28e3gaca4Sg5fdgsIp", null)))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "East")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "West")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "South")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Nord")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "West")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Ost")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Sued")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Sjever")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Istok")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Zapad")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Jug")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "PFF")))
                newList
            }
            else -> {
                newList.add(Pair(Constants.CATEGORY, "documents"))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("LVc28e3gaca4Sg5fdgsIp", null)))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList.add(Pair(Constants.DOCUMENT, Documents("zxc2se3saca43g56tgsdr", "Has Title")))
                newList
            }
        }
    }
}

