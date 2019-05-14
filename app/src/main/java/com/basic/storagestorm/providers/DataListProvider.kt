package com.basic.storagestorm.providers
import com.basic.storagestorm.Constants
import com.basic.storagestorm.DatabaseFragment
import com.basic.storagestorm.Helper
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.DataObject
import com.basic.storagestorm.models.Field


class DataListProvider(val fragment: DatabaseFragment) {

    fun getAllCollections(): MutableList<Pair<String, Any>> {
        // TODO get data from server
        val list = mutableListOf<Pair<String, Any>>()
        list.add(Pair(Constants.CATEGORY, Constants.COLLECTION))
        list.add(Pair(Constants.COLLECTION, Collection("Cars", "0000001") {
            fragment.updateContent("Cars", "0000001", Constants.COLLECTION)
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Cities", "0000002") {
            fragment.updateContent("Cities", "0000002", Constants.COLLECTION)
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Users", "0000003") {
            fragment.updateContent("Users", "0000003", Constants.COLLECTION)
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Animals", "0000004") {
            fragment.updateContent("Animals", "0000004", Constants.COLLECTION)
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Companies", "0000005") {
            fragment.updateContent("Companies", "0000005", Constants.COLLECTION)
        }))

        return list
    }

    fun getCollectionData(name: String?, headerID: String): MutableList<Pair<String, Any>> {
        val list = mutableListOf<Pair<String, Any>>()
        // TODO add string resources maybe
        list.add(Pair(Constants.CATEGORY, Constants.COLLECTION))
        list.add(Pair(Constants.COLLECTION, Collection("Trees", "0000006") {
            fragment.updateContent("Trees", "0000006", Constants.COLLECTION)
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Plants", "0000007") {
            fragment.updateContent("Plants", "0000007", Constants.COLLECTION)
        }))
        list.add(Pair(Constants.COLLECTION, Collection("Rivers", "0000008") {
            fragment.updateContent("Rivers", "0000008", Constants.COLLECTION)
        }))
        list.add(Pair(Constants.CATEGORY, Constants.DATA_OBJECT))
        list.add(Pair(Constants.DATA_OBJECT, DataObject("0000009") {
            fragment.updateContent(null, "0000009", Constants.DATA_OBJECT)
        }))
        list.add(Pair(Constants.DATA_OBJECT, DataObject("0000010") {
            fragment.updateContent(null, "0000011", Constants.DATA_OBJECT)
        }))

        return list
    }

    fun getObjectData(id: String): MutableList<Pair<String, Any>> {
        // TODO use id to get data from server
        val list = mutableListOf<Pair<String, Any>>()
        // TODO add string resources maybe
        list.add(Pair(Constants.CATEGORY, Constants.FIELD))
        list.add(Pair(Constants.FIELD, Field(id)))
        return list
    }

    private fun parseJson(id: String) : String { return Helper.getFileByID(id) }
}

