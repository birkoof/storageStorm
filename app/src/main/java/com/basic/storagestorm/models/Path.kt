package com.basic.storagestorm.models

class Path(
    val name: String?,
    val ID: String,
    val type: String,
    val onPerformAction: () -> Unit) {

    fun performAction() {
        onPerformAction()
    }
}