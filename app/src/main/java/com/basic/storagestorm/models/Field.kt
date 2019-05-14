package com.basic.storagestorm.models

import com.basic.storagestorm.providers.Server

class Field(val ID: String) {

    fun getJSON(): String {
        return Server.getObjectJSON(ID)
    }
}