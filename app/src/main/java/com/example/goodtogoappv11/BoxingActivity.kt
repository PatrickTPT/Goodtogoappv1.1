package com.example.goodtogoappv11

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.goodtogoappv11.model.Box

class BoxingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)



        val stationName = intent.getStringExtra("EXTRACT_STATION_NAME")

        val actionBar = supportActionBar
        if (stationName != null) {
            actionBar?.title = "裝箱｜${stationName}"
        } else {
            actionBar?.title = "裝箱｜xxx調度站"
        }
        actionBar?.setDisplayHomeAsUpEnabled(true)



        var boxingList: ArrayList<Box>

    }

    fun createBox(){
        /*TODO:
           storeId: null,
           storeName = null,
           boxid = the next largest id,
           packer = me, date=Date() in proper form, status= ENum,
           childList = this boxed BoxChild ,
           cupType check(also array),
           cupNumber=Array.size,
           viewType = 1,
           expandable = false
        */
    }






}
