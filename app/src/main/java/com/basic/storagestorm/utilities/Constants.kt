package com.basic.storagestorm.utilities

class Constants {
    companion object {
        const val CATEGORY = "Category"
        const val COLLECTION = "Collections"
        const val DATA_OBJECT = "Data Objects"
        const val FIELD = "Fields"
        const val HOME = "Home"

        // utilites server
        const val UTILITIES_SERVER_URL = "http://muffin-ti.me:8084"

        // intent extra
        const val INTENT_EXTRA_SEARCH_WORD = "intent_extra_search_word"
        const val INTENT_EXTRA_OBJECT_ID = "intent_extra_object_id"
        const val INTENT_EXTRA_COLLECTION_ID = "intent_extra_collection_id"
        const val INTENT_EXTRA_OBJECT_JSON = "intent_extra_object_json"

        // broadcast
        const val REFRESH_DATA = "refresh_data"

        // preferences tags
        const val PREF_CREATE_COLL_HEAD_ID = "create_coll_head_id"
        const val PREF_GET_COLL_COLL_ID = "pref_get_coll_coll_id"
        const val PREF_DELETE_COLL_COLL_ID = "pref_delete_coll_coll_id"
        const val PREF_ADD_COLL_TO_COLL_ID_A = "add_coll_to_coll_id_a"
        const val PREF_ADD_COLL_TO_COLL_ID_B = "add_coll_to_coll_id_b"
        const val PREF_REMOVE_COLL_FROM_COLL_ID_A = "remove_coll_from_coll_id_a"
        const val PREF_REMOVE_COLL_FROM_COLL_ID_B = "remove_coll_from_coll_id_b"

        const val PREF_EDIT_OBJ_OBJ_ID = "pref_edit_obj_obj_id"
        const val PREF_DELETE_OBJ_OBJ_ID = "pref_delete_obj_obj_id"
        const val PREF_ADD_OBJ_TO_COLL_OBJ_ID = "add_obj_to_coll_obj_id"
        const val PREF_ADD_OBJ_TO_COLL_COLL_ID = "add_obj_to_coll_coll_id"
        const val PREF_REMOVE_OBJ_FROM_COLL_OBJ_ID = "remove_obj_from_coll_obj_id"
        const val PREF_REMOVE_OBJ_FROM_COLL_COLL_ID = "remove_obj_from_coll_coll_id"
    }
}