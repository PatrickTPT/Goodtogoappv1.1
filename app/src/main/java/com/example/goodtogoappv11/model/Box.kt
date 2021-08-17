package com.example.goodtogoappv11.model

data class Box(
    var storeId: Int?,
    var storeName: String?,
    val boxid : Int?,
    val packer : String?,
    val date: Long,
    var status: Int?,
    val childList: MutableList<Boxes.BoxChild>?,
    val cupType: String?,
    val cupNumber: Int?,
    val viewType: Int,
    var expandable: Boolean = false
)
