package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import com.example.goodtogoappv11.model.Store
import com.example.goodtogoappv11.model.Tokens.standardAut
import com.example.goodtogoappv11.network.StoresListResponse
import com.example.goodtogoappv11.network.ourService
import com.example.goodtogoappv11.network.reload.ReloadResponse
import com.example.goodtogoappv11.network.reload.ReloadResponseItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity() {



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
            startActivity(Intent(this, DispatchHistoryActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")
            Log.v("test","dispatchRecordBtn clicked")
        }

        recycleBtn.visibility = View.GONE
        recycleBtn.setOnClickListener {
            try {
                startActivity(Intent(this, RecycleActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }catch(e:Exception){
                Log.e("TEST","$e")
            }
        }

        recycleHistoryBtn.setOnClickListener {

            startActivity(Intent(this, RecycleHistoryActivity::class.java))
            Log.v("test","recycleHistoryBtn clicked")
        }

        storeListBtn.setOnClickListener {
            Log.v("test", "storeListBtn clicked")

            storesGetList()

            //startActivity(Intent(this, TestActivity::class.java))
            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            //showProgressDialog("載入中...")

        }

    }


    private fun storesGetList(){
        if(Constants.isNetworkAvailable(this)){
            //Toast.makeText(this,"network connected", Toast.LENGTH_SHORT).show()
            showProgressDialog("載入中...")

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val listCall: Call<StoresListResponse> = service.getStoreslist(
                Constants.reqID(), Constants.reqTime
            )

            listCall.enqueue(object : Callback<StoresListResponse> {
                override fun onResponse(
                    call: Call<StoresListResponse>,
                    response: Response<StoresListResponse>,
                ) {
                    if(response.isSuccessful){
                        hideProgressDialog()


                        var newStoreList = ArrayList<Store>()
                        val list: StoresListResponse? = response.body()
                        for(i in list!!.shop_data.indices){
                            newStoreList.add(
                                Store(list.shop_data.get(i).name,
                                list.shop_data.get(i).id,
                                list.shop_data.get(i).category,VIEW_TYPE_ONE))
                        }
                        //Toast.makeText(this@MainActivity,newStoreList.toString(), Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MainActivity, TestActivity::class.java)
                        intent.putExtra("storeList",newStoreList)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)



                    } else {
                        val rc = response.code()
                        when(rc){
                            400 -> {
                                Log.e("Error 400", "Bad Connection")
                            }
                            404 -> {
                                Log.e("Error 404", "Not Found")
                            }
                            401 -> {
                                Log.e("Error 401", "headers' problem")
                            }
                            else ->{
                                Log.e("Error", response.message())
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<StoresListResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@MainActivity,t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

    private fun getReloadHistory(){
        if(Constants.isNetworkAvailable(this)){
            //Toast.makeText(this,"network connected", Toast.LENGTH_SHORT).show()
            showProgressDialog("載入中...")

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val listCall: Call<ReloadResponse> = service.getReloadList(
                standardAut(), Constants.MY_APIKEY_STATION
            )

            listCall.enqueue(object : Callback<ReloadResponse> {
                override fun onResponse(
                    call: Call<ReloadResponse>,
                    response: Response<ReloadResponse>,
                ) {
                    if(response.isSuccessful){
                        hideProgressDialog()


                        var newList = ArrayList<ReloadResponseItem>()
                        val list: ReloadResponse? = response.body()

                        for(i in list!!.indices){
                            newList.add(list[i])
                        }
                        //Toast.makeText(this@MainActivity,newStoreList.toString(), Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MainActivity, RecycleHistoryActivity::class.java)
                        intent.putExtra("reloadList",newList)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)



                    } else {
                        val rc = response.code()
                        when(rc){
                            400 -> {
                                Log.e("Error 400", "Bad Connection")
                            }
                            404 -> {
                                Log.e("Error 404", "Not Found")
                            }
                            401 -> {
                                Log.e("Error 401", "headers' problem")
                            }
                            else ->{
                                Log.e("Error", response.message())
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ReloadResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@MainActivity,t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })

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


