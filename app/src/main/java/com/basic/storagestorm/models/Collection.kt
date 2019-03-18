package com.basic.storagestorm.models

class Collection(var name: String,
                 val id: String,
                 val documents: MutableList<Pair<String, Any>>?,
                 val onPerformAction: () -> Unit) {

    fun performAction() {
        onPerformAction()
    }
}