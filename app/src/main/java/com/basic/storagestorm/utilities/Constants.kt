package com.basic.storagestorm.utilities

class Constants {
    companion object {
        val CATEGORY = "Category"
        val COLLECTION = "Collections"
        val DATA_OBJECT = "Data Objects"
        val FIELD = "Fields"
        val HOME = "Home"

        // utilites server
        val UTILITIES_SERVER_URL = "http://muffin-ti.me:8084"

        // intent extra
        val INTENT_EXTRA_SEARCH_WORD = "intent_extra_search_word"
        val INTENT_EXTRA_OBJECT_ID = "intent_extra_object_id"
        val INTENT_EXTRA_COLLECTION_ID = "intent_extra_collection_id"
        val INTENT_EXTRA_COLLECTION_NAME = "intent_extra_collection_name"
        val INTENT_EXTRA_OBJECT_JSON = "intent_extra_object_json"

        // broadcast
        val REFRESH_DATA = "refresh_data"

        // preferences tag
        val SHARED_PREFERENCES = "storage_storm_preference"
    }
}