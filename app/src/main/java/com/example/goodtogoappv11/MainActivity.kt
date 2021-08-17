package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setupActionBar()

        // TODO: find way to hideProgressDialog()

        Log.d("MainActivity", "onCreate Called")




        val actionBar = supportActionBar
        actionBar!!.title = "好盒器台南調度站"


        inStockBtn.setOnClickListener {
            startActivity(Intent(this, InStockActivity::class.java))
            //showProgressDialog("載入中...")
            /*Handler().postDelayed({
                hideProgressDialog()
            }, 1000)*/
            Log.v("test","inStockButton clicked")

        }

        toDeliverBtn.setOnClickListener {
            startActivity(Intent(this, ToDeliverActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")
            Log.v("test","toDeliverButton clicked")
        }

        deliveryRecordBtn.setOnClickListener {
            startActivity(Intent(this, DeliveryHistoryActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")
            Log.v("test","DeliveryButton clicked")
        }

        toSignForBtn.setOnClickListener {
            startActivity(Intent(this, ToSignForActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")
            Log.v("test","toSignForButton clicked")
        }

        dispatchRecordBtn.setOnClickListener {
            startActivity(Intent(this, DispatchRecordActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")
            Log.v("test","dispatchRecordBtn clicked")
        }

        recycleBtn.setOnClickListener {
            try {
                startActivity(Intent(this, RecycleActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }catch(e:Exception){
                Log.e("TEST","$e")
            }
        }

        recycleRecordBtn.setOnClickListener {
            startActivity(Intent(this, RecycleHistoryActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")
            Log.v("test","recycleRecordBtn clicked")
        }

        storeListBtn.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")
            Log.v("test", "storeListBtn clicked")
        }

    }

    /*override fun setupActionBar(){
        //val mainToolBar = findViewById<Toolbar>(R.id.mainToolBar)
        //setSupportActionBar(mainToolBar)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "好盒器台南調度站"
            actionBar.setDisplayShowCustomEnabled(true)

        }
        *//*mainToolBar.setNavigationOnClickListener {
            onBackPressed()
        }*//*
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items

        when (item.itemId) {
            R.id.item_setting -> {
                //Toast.makeText(this, "setting clicked",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity,SettingActivity::class.java))
                return true

            }



        }
        return super.onOptionsItemSelected(item)
    }



}


