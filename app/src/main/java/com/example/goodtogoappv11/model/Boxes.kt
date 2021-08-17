package com.example.goodtogoappv11.model

import com.example.goodtogoappv11.model.Constants.BOX_STATUS_BOXED
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
        var cupStatus: Int
    )


    //child data generator
    var boxChildList001 = mutableListOf<BoxChild>(
        BoxChild("大器杯", 30001,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30002,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30003,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30004,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30005,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30006,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30007,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30008,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30009,BOX_STATUS_BOXED),
        BoxChild("大器杯", 30010,BOX_STATUS_BOXED),
    )

    var boxChildList002 = mutableListOf<BoxChild>(
        BoxChild("小器杯", 30011,BOX_STATUS_BOXED),
        BoxChild("小器杯", 30012,BOX_STATUS_BOXED),
        BoxChild("小器杯", 30013,BOX_STATUS_BOXED),
        BoxChild("小器杯", 30014,BOX_STATUS_BOXED),
        BoxChild("小器杯", 30015,BOX_STATUS_BOXED),
        BoxChild("豪器杯", 30016,BOX_STATUS_BOXED),
        BoxChild("豪器杯", 30017,BOX_STATUS_BOXED),
        BoxChild("豪器杯", 30018,BOX_STATUS_BOXED),
        BoxChild("豪器杯", 30019,BOX_STATUS_BOXED),
        BoxChild("豪器杯", 30020,BOX_STATUS_BOXED),
    )

    var boxA = mutableListOf<BoxLong>(
        BoxLong("好基地",
            4567001,
            "0963328359",
            2021080600,
            null,boxChildList001,
            VIEW_TYPE_ONE)
    )


}