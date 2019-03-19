package com.basic.storagestorm.models

class Path(val name: String,
           val ID: String,
           val content: MutableList<Pair<String, Any>>?,
           val onPerformAction: () -> Unit) {

    fun performAction() {
        onPerformAction()
    }
}