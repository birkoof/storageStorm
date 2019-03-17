package com.basic.storagestorm.models

class Collection(var name: String,
                 val documents: MutableList<Pair<Int, Any>>?,
                 val onPerformAction: () -> Unit) {

    fun performAction() {
        onPerformAction()
    }
}