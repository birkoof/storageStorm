package com.basic.storagestorm.models

class Collection(
    var name: String?,
    val id: String,
    val onPerformAction: () -> Unit) {

    fun performAction() {
        onPerformAction()
    }
}