package com.example.goodtogoappv11.model

import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE

class Boxes {
    data class BoxLong (
        var store: String?,
        val boxid : Int?,
        val packer : String?,
        val date: Long?,
        val status: Int?,
        val childList: MutableList<BoxChild>?,
        val viewType: Int,
        var expandable: Boolean = false)

    data class BoxChild (
        val cupType: String,
        val cupId: Int,
        var cupStatus: Int,
        val viewType: Int
    )


    //child data generator


    var boxA = mutableListOf<BoxLong>(
        BoxLong("好基地",
            4567001,
            "0963328359",
            2021080600,
            null,null,
            VIEW_TYPE_ONE)
    )


}