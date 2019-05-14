package com.basic.storagestorm.models

class DataObject(
    var ID: String,
    val onPerformAction: () -> Unit
) {

    fun performAction() {
        onPerformAction()
    }
}