package com.example.goodtogoappv11.model

import com.example.goodtogoappv11.model.Constants.BOX_STATUS_BOXED

data class Box(
    var storeId: Int?,
    var storeName: String?,
    val boxid : Int?,
    val packer : String?,
    val date: Long,
    var status: Int = BOX_STATUS_BOXED,
    val childList: MutableList<Boxes.BoxChild>?,
    val cupType: String?,
    val cupNumber: Int?,
    val viewType: Int,
    var expandable: Boolean = false
)
