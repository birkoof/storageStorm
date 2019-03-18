package com.basic.storagestorm.models

class Documents(var ID: String,
                var title: String?,
                val fields: MutableList<Pair<String, Any>>?,
                val onPerformAction: () -> Unit) {

    fun performAction() {
        onPerformAction()
    }
}