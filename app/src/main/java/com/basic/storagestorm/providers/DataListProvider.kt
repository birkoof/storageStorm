package com.basic.storagestorm.providers
import android.support.v4.app.FragmentActivity
import com.basic.storagestorm.Constants
import com.basic.storagestorm.DatabaseFragment
import com.basic.storagestorm.Helper
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.Documents
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.InputStream
import java.lang.Exception


class DataListProvider(val fragment: DatabaseFragment, val activity: FragmentActivity?) {

    fun getData(pair: Pair<String, String>) : MutableList<Pair<String, Any>>? {
        return when (pair.first) {
            Constants.HOME -> getHomeData()
            Constants.COLLECTION -> (getCollection(pair.second).second as Collection).documents
            // TODO documents
            Constants.DOCUMENT -> getHomeData()
            else -> getHomeData()
        }
    }

    private fun getHomeData() : MutableList<Pair<String, Any>> {

        val list = mutableListOf<Pair<String, Any>>()
        list.add(Pair(Constants.CATEGORY, "Collections"))

        val jsonFile = StringBuilder(parseJson(Constants.HOME))
        val parser: Parser = Parser.default()
        val homeObject: JsonObject = parser.parse(jsonFile) as JsonObject
        val collectionIDs = homeObject.array<String>("collections")

        collectionIDs?.forEach {
            list.add(getCollection(it))
        }

        return list
    }

    private fun getCollection(ID: String) : Pair<String, Any> {
        val listOfDocuments = mutableListOf<Pair<String, Any>>()

        val jsonFile = StringBuilder(parseJson(ID))
        val parser: Parser = Parser.default()
        val jsonObject: JsonObject = parser.parse(jsonFile) as JsonObject
        val documents = jsonObject.array<String>("documents")

        documents?.forEach {
            val jsonFile2 = StringBuilder(parseJson(it))
            val jsonObject2: JsonObject = parser.parse(jsonFile2) as JsonObject
            val document = getDocument(jsonObject2.string("id") as String)
            listOfDocuments.add(document)
        }

        val title = jsonObject.string("name") as String

        val collection = Collection(title, ID, listOfDocuments) {
            fragment.updateContent(title, ID, listOfDocuments)
        }

        return Pair(Constants.COLLECTION, collection)
    }

    private fun getDocument(ID: String) : Pair<String, Any> {
        val jsonFile = StringBuilder(parseJson(ID))
        val parser: Parser = Parser.default()
        val jsonObject = parser.parse(jsonFile) as JsonObject
        val title = jsonObject.string("name")

        return Pair(Constants.DOCUMENT, Documents(ID, title))
    }

    private fun parseJson(id: String) : String { return Helper.getFileByID(id) }
}

