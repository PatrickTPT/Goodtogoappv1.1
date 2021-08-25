package com.example.goodtogoappv11.data

import com.example.goodtogoappv11.model.*
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_BOXED
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_INUSE
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_TOBEUSED
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_TODELIVER
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_TWO

class Datasource {
    fun loadOldboxes(): List<OldBox> {
        return listOf(
            OldBox("#3030001","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030002","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030003","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030004","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030005","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030006","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030007","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030008","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030009","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030010","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030011","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030012","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030013","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030014","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030015","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030016","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030017","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030018","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030019","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030020","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030021","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030022","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030023","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030024","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030025","小器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030026","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030027","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030028","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030029","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030030","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030031","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030032","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030033","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030034","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030035","大器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030036","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030037","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030038","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030039","豪器杯 x 10","創建：2021/6/16 02:23"),
            OldBox("#3030040","豪器杯 x 10","創建：2021/6/16 02:23"),
        )
        }

    fun loadStores(): ArrayList<Store> {
        return arrayListOf(
            Store(null,null,1, VIEW_TYPE_TWO),
            Store("Fast & Furious 9", 121, 1,VIEW_TYPE_ONE),
            Store("Black Widow", 721, 1, VIEW_TYPE_ONE),
            Store(null,null,1,VIEW_TYPE_TWO),
            Store("ZOO", 421, 1,VIEW_TYPE_ONE),
            Store("Vincenzo",384,0,VIEW_TYPE_ONE),
            Store(null,null,0,VIEW_TYPE_TWO),
            Store("Attack on Titan",843,0,VIEW_TYPE_ONE),
            Store("Ghost in the shell", 871, 0,VIEW_TYPE_ONE),
            Store(null,null,1,VIEW_TYPE_TWO),
            Store("PICKINUP MAN",433,1,VIEW_TYPE_ONE)

        )
    }

    fun loadBoxes(): MutableList<Box> {
        return mutableListOf(

            Box(12,"方糖咖啡(府連店)",3124014,"0963328359",
                1629047793643,BOX_STATUS_TODELIVER,null,"Grande",10,VIEW_TYPE_ONE),
            Box(328,"hecho 做咖啡一店", 3122008,"0911789727",
                1629047793643,BOX_STATUS_BOXED,null,"Venti", 10,VIEW_TYPE_ONE),
            Box(304,"白巷子府前店" ,3122017,"0963328359",
                1629047793643,BOX_STATUS_BOXED,null,"Venti", 10,VIEW_TYPE_ONE),
            Box(12, "方糖咖啡(府連店)",3101036,"0911789727",
                461865600000, BOX_STATUS_TOBEUSED,null,"Tall",50,VIEW_TYPE_ONE),
            Box(304,"白巷子府前店" ,3102009,"0963328359",
                461865600000,BOX_STATUS_TODELIVER,null,"Grande",50,VIEW_TYPE_ONE),
            Box(41,"雲平咖啡",3121333,"0911789727",
                1604035600000,BOX_STATUS_BOXED,null,"Grande", 50,VIEW_TYPE_ONE),
            Box(328,"hecho 做咖啡一店",3123415,"0963328359",
                1604035600000,BOX_STATUS_INUSE,null,"Tall",10, VIEW_TYPE_ONE),
            Box(12,"方糖咖啡(府連店)",3123416,"0963328359",
                1604035600000,BOX_STATUS_TODELIVER,null,"Tall",10, VIEW_TYPE_ONE)
        )
    }



    fun loadBoxesbkup(): ArrayList<Box> {
        return arrayListOf(
            /*Box(12,null,null,1627502400,null,
                null,null,null,VIEW_TYPE_TWO),
            Box(12,null,null,1627502400,null,
                null,null,null,VIEW_TYPE_TWO),*/
            Box(12,"方糖咖啡(府連店)",3124014,"0963328359",
                1627502400,BOX_STATUS_BOXED,null,"Grande",10,VIEW_TYPE_ONE),
            Box(328,"hecho 做咖啡一店", 3122008,"0911789727",
                1627502400,BOX_STATUS_BOXED,null,"Venti", 10,VIEW_TYPE_ONE),
            Box(304,"白巷子府前店" ,3122010,"0963328359",
                1627502400,BOX_STATUS_BOXED,null,"Venti", 10,VIEW_TYPE_ONE),
            Box(12, "方糖咖啡(府連店)",3101036,"0911789727",
                461865600,BOX_STATUS_BOXED,null,"Tall",50,VIEW_TYPE_ONE),
            Box(304,"白巷子府前店" ,3102009,"0963328359",
                461865600,BOX_STATUS_BOXED,null,"Grande",50,VIEW_TYPE_ONE),
            Box(41,"雲平咖啡",3121333,"0911789727",
                1604035600,BOX_STATUS_BOXED,null,"Grande", 50,VIEW_TYPE_ONE),
            Box(328,"hecho 做咖啡一店",3123415,"0963328359",
                1604035600,BOX_STATUS_INUSE,null,"Tall",10, VIEW_TYPE_ONE),
            Box(12,"方糖咖啡(府連店)",3123416,"0963328359",
                1627502400,BOX_STATUS_BOXED,null,"Tall",10, VIEW_TYPE_ONE)
        )
    }

    fun ordinalBoxChild(i: Int, initialValue: Int): ArrayList<Boxes.BoxChild>{
        var initValue = initialValue
        val arrayList = ArrayList<Boxes.BoxChild>()

        for (i in 1..10) {
            arrayList.add(
                Boxes.BoxChild("大器杯", initValue + i, Constants.BOX_STATUS_BOXED,VIEW_TYPE_ONE))
        }

        return arrayList

    }
}



