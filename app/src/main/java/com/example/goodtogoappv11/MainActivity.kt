package com.example.goodtogoappv11

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import com.example.goodtogoappv11.model.Constants.myApiKey
import com.example.goodtogoappv11.model.Constants.mySecretKey
import com.example.goodtogoappv11.model.Constants.myStationName
import com.example.goodtogoappv11.model.Constants.vReloadList
import com.example.goodtogoappv11.model.Constants.vStoreList
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
import java.io.IOException

class MainActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setupActionBar()

        storesGetListGeneral()

        if (savedInstanceState != null) {
            myStationName = savedInstanceState.getString(STATION_NAME_GET, "0")
            myApiKey = savedInstanceState.getString(API_KEY_GET,"")
            mySecretKey = savedInstanceState.getString(SECRET_KEY_GET,"")
        }


        val actionBar = supportActionBar
        actionBar!!.title = myStationName




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
            Log.v("test","toSignForButton clicked")

        }

        dispatchRecordBtn.setOnClickListener {

            startActivity(Intent(this, DispatchHistoryActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            Log.v("test","dispatchRecordBtn clicked")

        }

        recycleBtn.visibility = View.GONE
        recycleBtn.setOnClickListener {

            startActivity(Intent(this, RecycleActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            Log.v("test","recycle button clicked")

        }

        storeListBtn.setOnClickListener {

            startActivity(Intent(this, TestActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            Log.v("test", "storeList Button clicked")

        }

        recycleHistoryBtn.setOnClickListener {

            reloaddGetList()
            Log.v("test","recycleHistory Button clicked")

        }



    }




    /** On Duty */
    private fun storesGetListGeneral(){
        if(vStoreList.size == 0) {
            if (Constants.isNetworkAvailable(this)) {
                //Toast.makeText(this,"network connected", Toast.LENGTH_SHORT).show()
                showProgressDialog("載入中...")

                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service: ourService = retrofit
                    .create<ourService>(ourService::class.java)

                val listCall: Call<StoresListResponse> = service.getStoresList(
                    Constants.reqID(), Constants.reqTime
                )

                listCall.enqueue(object : Callback<StoresListResponse> {
                    override fun onResponse(
                        call: Call<StoresListResponse>,
                        response: Response<StoresListResponse>,
                    ) {
                        if (response.isSuccessful) {
                            hideProgressDialog()

                            //var newStoreList = ArrayList<Store>()
                            val list: StoresListResponse? = response.body()
                            for (i in list!!.shop_data.indices) {

                                vStoreList.add(
                                    Store(list.shop_data.get(i).name,
                                        list.shop_data.get(i).id,
                                        list.shop_data.get(i).category, VIEW_TYPE_ONE))
                            }

                        } else {
                            val rc = response.code()
                            when (rc) {
                                400 -> {
                                    Log.e("Error 400", "Bad Connection")
                                }
                                404 -> {
                                    Log.e("Error 404", "Not Found")
                                }
                                401 -> {
                                    Log.e("Error 401", "headers' problem")
                                }
                                else -> {
                                    Log.e("Error", response.message())
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<StoresListResponse>, t: Throwable) {
                        Log.e("Errorr", t!!.message.toString())
                        Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                })

            }
        }
    }

    private fun getReloadHistory(){
        if(Constants.isNetworkAvailable(this)){
            //Toast.makeText(this,"network connected", Toast.LENGTH_SHORT).show()


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
                        //hideProgressDialog()


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

                    }
                }

                override fun onFailure(call: Call<ReloadResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())

                }

            })

        }
    }

    /** On Duty */
    private fun reloaddGetList() {
        if(Constants.isNetworkAvailable(this)){
            showProgressDialog("載入中...")
            //Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            /** Main Variables */
            val listCall: Call<ReloadResponse> = service.getReloadList(
                standardAut(), myApiKey
            )

            listCall.enqueue(object : Callback<ReloadResponse>{
                override fun onResponse(
                    call: Call<ReloadResponse>,
                    response: Response<ReloadResponse>,
                ) {
                    if(response.isSuccessful){

                        var newList = ArrayList<ReloadResponseItem>()
                        val list: ReloadResponse? = response.body()

                        for(i in list!!.indices){
                            newList.add(list[i])
                        }

                        vReloadList = newList


                        val intent = Intent(this@MainActivity, RecycleHistoryActivity::class.java)
                        //intent.putExtra("reloadList",newList)
                        //simpleAlertDialog("RESULT",newList[4].toString())

                        hideProgressDialog()
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)










                        //Toast.makeText(this@MainActivity,"${list.toString()}",Toast.LENGTH_LONG).show()
                        //Log.i("Response Result","${ContainerGetListResponse()}")







                    } else {
                        try {
                            hideProgressDialog()
                            simpleAlertDialog("Error",response.errorBody()!!.string())
                            //Toast.makeText(this@MainActivity, response.errorBody()?.string(),Toast.LENGTH_LONG).show()
                            //Log.i("errorbody", "onResponse: ${response.errorBody()?.string()}")
                        } catch (e: IOException) {
                            Toast.makeText(this@MainActivity, "Unknown error:", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }



                        Log.i("Auth", "onResponse: ${standardAut()}")
                    }
                }

                override fun onFailure(call: Call<ReloadResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@MainActivity,t.message.toString(),Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("確定離開？")
            .setPositiveButton("留下來"){ dialogInterface, which ->
                dialogInterface.dismiss()
            }
            .setNeutralButton("離開"){ dialogInterface, which ->
                super.onBackPressed()
            }
            //.setCancelable(false) //dialog can't be cancel outside
            .create()
            .show()

    }

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
                startActivity(Intent(this@MainActivity,SettingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }

            /*R.id.item_getAuth -> {
                quickToast("Station: $myStationName\nApiKey: $myApiKey\nSecretKey: $mySecretKey")
                return true
            }*/


        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATION_NAME_GET,myStationName)
        outState.putString(API_KEY_GET, myApiKey)
        outState.putString(SECRET_KEY_GET, mySecretKey)

    }

    companion object {
        const val STATION_NAME_GET = "stationName_get"
        const val API_KEY_GET = "apiKey_get"
        const val SECRET_KEY_GET = "secretKey_get"
    }


}


