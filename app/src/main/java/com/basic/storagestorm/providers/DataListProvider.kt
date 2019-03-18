package com.basic.storagestorm.providers
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.basic.storagestorm.Constants
import com.basic.storagestorm.DatabaseFragment
import com.basic.storagestorm.Helper
import com.basic.storagestorm.models.Collection
import com.basic.storagestorm.models.Documents
import com.basic.storagestorm.models.Field
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.InputStream
import java.lang.Exception
import kotlin.coroutines.Continuation


class DataListProvider(val fragment: DatabaseFragment, val activity: FragmentActivity?) {

    fun getData(pair: Pair<String, String>) : MutableList<Pair<String, Any>>? {
        return when (pair.first) {
            Constants.HOME -> getHomeData()
            Constants.COLLECTION -> getCollection(pair.second).documents
            Constants.DOCUMENT -> getDocument(pair.second).fields
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
            list.add(Pair(Constants.COLLECTION, getCollection(it)))
        }

        return list
    }

    private fun getCollection(ID: String) : Collection {
        Log.d("DDDD", "INSIDE getCol")
        val documentsList = mutableListOf<Pair<String, Any>>()
        documentsList.add(Pair(Constants.CATEGORY, "Documents"))

        val jsonFile = StringBuilder(parseJson(ID))
        val parser: Parser = Parser.default()
        val jsonObject: JsonObject = parser.parse(jsonFile) as JsonObject
        val documents = jsonObject.array<String>("documents")

        documents?.forEach {
            val jsonFile2 = StringBuilder(parseJson(it))
            val jsonObject2: JsonObject = parser.parse(jsonFile2) as JsonObject
            val document = getDocument(jsonObject2.string("id") as String)
            documentsList.add(Pair(Constants.DOCUMENT, document))
        }

        val title = jsonObject.string("name") as String

        return Collection(title, ID, documentsList) {
            fragment.updateContent(title, ID, documentsList)
        }
    }

    private fun getDocument(ID: String) : Documents {
        val jsonFile = StringBuilder(parseJson(ID))
        val parser: Parser = Parser.default()
        val jsonObject = parser.parse(jsonFile) as JsonObject
        val title = jsonObject.string("name")
        val collections: JsonArray<String>? = jsonObject.array("collections")
        val list = mutableListOf<Pair<String, Any>>()

        if (collections != null) {
            list.add(Pair(Constants.CATEGORY, "Collections"))
            collections.forEach {
                val jsonFile2 = StringBuilder(parseJson(it))
                val jsonObject2: JsonObject = parser.parse(jsonFile2) as JsonObject
                val collection = getCollection(jsonObject2.string("id") as String)
                list.add(Pair(Constants.COLLECTION, collection))
            }
        }

        list.add(Pair(Constants.CATEGORY, "Fields"))
        list.add(Pair(Constants.FIELD, Field(ID, jsonObject.toJsonString(true))))

        return Documents(ID, title, list) {
            fragment.updateContent(title, ID, list)
        }
    }

    private fun parseJson(id: String) : String { return Helper.getFileByID(id) }
}

