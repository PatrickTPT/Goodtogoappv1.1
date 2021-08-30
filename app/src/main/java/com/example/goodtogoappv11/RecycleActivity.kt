package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.budiyev.android.codescanner.CodeScanner
import com.example.goodtogoappv11.adapter.InputInStockAdapter
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.model.Boxes
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.recycleStore
import com.example.goodtogoappv11.model.Constants.vStoreList
import com.example.goodtogoappv11.model.Tokens
import com.example.goodtogoappv11.network.ourService
import com.example.goodtogoappv11.network.readytoclean.ReadyToCleanResponse
import com.example.goodtogoappv11.network.returnobject.ReturnResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.activity_scan.rv_bottom_infobox
import kotlinx.android.synthetic.main.item_bottom_infobox.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*


class RecycleActivity : BaseInputActivity(), OnAClickListener {

    private lateinit var codeScanner: CodeScanner
    var titleText: String? = null
    private var currentStoreId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("TAG", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val storeName = intent.getStringExtra("EXTRACT_STORE_NAME")
        val lookupStore = vStoreList.first{it.name == storeName}
        currentStoreId = lookupStore.id!!

        //setupActionBar()
        recycleStore= "店舖"

        //標題列設定
        val actionBar= supportActionBar
        actionBar!!.title = "回收"
        if (storeName != null) {
            titleText = "回收(從 $storeName)"
            actionBar.title = titleText
        }
        actionBar.setDisplayHomeAsUpEnabled(true)

        setBottomBoxRecyclerView()

        idInputSlot = findViewById<EditText>(R.id.et_idInputSlot)
        inputBtn = findViewById<Button>(R.id.button3)
        tv_box_count = findViewById<TextView>(R.id.tv_box_count)
        tv_scanWord = findViewById(R.id.tv_scanWord)
        clearBtn = findViewById(R.id.btn_clear)
        proceedFab = findViewById(R.id.fab_proceed)
        rvInfoBox = findViewById(R.id.rv_bottom_infobox)


        ll_infobox_header.setOnClickListener {
            if (rvInfoBox.visibility == View.GONE) {
                rvInfoBox.visibility = View.VISIBLE
                if(childList.size>0){
                    tv_info_left_top.visibility = View.VISIBLE
                }
            } else {
                rvInfoBox.visibility = View.GONE
                tv_info_left_top.visibility = View.GONE
            }
        }

        // 輸入鍵功能宣告
        inputBtn.setOnClickListener {
            inputAction()
            //infoboxDisplayCheck()
        }

        //清除資料功能宣告
        clearBtn.setOnClickListener {
            //infoboxDisplayCheck()
        }

        //傳送資料功能宣告
        proceedFab.setOnClickListener {
            //inputCheck(ResultOfRecycleActivity())
            //TODO:
            if (inputList.size >0) {
                returnContainer(currentStoreId)
            } else {
                Toast.makeText(this, "沒有已登錄的容器", Toast.LENGTH_SHORT).show()
            }
        //設定讀取fragment(暫緩）
            //val keyInFragment = keyInFragment()
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,keyInFragment).commit()

        }
    }

    fun setBottomBoxRecyclerView(){
        rv_bottom_infobox.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = InputInStockAdapter(this, childList,this)
        rv_bottom_infobox.adapter = adapter
        rv_bottom_infobox.setHasFixedSize(true)
    }

    override fun onStart(){
        Log.i("TAG", "onStart()")
        super.onStart()
        if(titleText == null) {
            Handler().postDelayed({
                val dialog = StoreListBottomSheetDialogFragment()
                dialog.show(supportFragmentManager, "StoreListFragment")
            }, 1000)
        }
    }

    override fun onResume() {
        Log.i("TAG", "onResumeD()")
        /*val actionBar= supportActionBar

        if (recycleStore == "店舖") {

            actionBar!!.title = "回收"

        } else {
            titleText = "回收(從 $recycleStore)"
            actionBar!!.title = titleText
        }*/
        super.onResume()


        //codeScanner.startPreview()
    }

    override fun onPause() {
        //codeScanner.releaseResources()
        Log.i("TAG", "onPause()")
        super.onPause()
    }

    fun inputAction(){
        if (
            idInputSlot.text.toString().isEmpty()
        ) {
            quickToast("請輸入容器號碼")
        } else  {
            if(inputList.contains(idInputSlot.text.toString())){
                quickToast("此容器已輸入！")
            } else {
                inputList.add(idInputSlot.text.toString())
                childList.add(createChild(idInputSlot.text.toString()))
                //cupTypeList.add("${childList{it.cupType}}")

                idInputSlot.text.clear()
                tv_box_count.text = childList.size.toString() //要記的是list的資料筆數
                //println("資料增加，總筆數: ${childList.size}")
                tv_box_count.visibility = View.VISIBLE
                tv_scanWord.text = "已登錄容器"
                showSnackbar("輸入成功")
            }
        }
    }

    fun createChild(cupid: String): Boxes.BoxChild{
        return Boxes.BoxChild("", cupid.toInt() ,
            Constants.BOX_STATUS_READYTOCLEAN, Constants.VIEW_TYPE_ONE)
    }

    fun infoboxDisplayCheck(){
        when {
            childList.size == 1 -> {
                tv_scanWord.text = String.format(getString(R.string.no_input_container_yet))
                tv_box_count.visibility = View.GONE
                inputList.removeAt(0)
            }
            childList.size > 1 -> {
                inputList.removeAt(0)
            }
            childList.size == 0 -> {
                tv_scanWord.text = String.format(getString(R.string.no_input_container_yet))
                tv_box_count.visibility = View.GONE
                quickToast(String.format(getString(R.string.no_input_to_clean)))
            }
        }
        println("資料減少，總筆數: ${childList.size}")
        tv_box_count.text = childList.size.toString()
    }


    private fun readyToClean() {
        if(Constants.isNetworkAvailable(this)){
            //Toast.makeText(this,"network connected", Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val jsonObject = JsonObject()
            if (inputList.size != 0) {
                val jsonInputArray = JsonArray()
                for(i in 0..inputList.size-1) {
                    jsonInputArray.add(inputList[i])
                }
                jsonObject.add("containers", jsonInputArray)
            }


            /** Main Variables */
            val listCall: Call<ReadyToCleanResponse> = service.postReadyToClean(
                Tokens.standAuthWithTime(Date()), Constants.myApiKey, jsonObject
            )

            listCall.enqueue(object : Callback<ReadyToCleanResponse> {
                override fun onResponse(
                    call: Call<ReadyToCleanResponse>,
                    response: Response<ReadyToCleanResponse>,
                ) {
                    if(response.isSuccessful){
                        val list: ReadyToCleanResponse? = response.body()
                        //Toast.makeText(this@SettingActivity,"${list.toString()}",
                        //    Toast.LENGTH_SHORT).show()
                        hideProgressDialog()
                        simpleAlertDialog("Result",list.toString())

                        val intent = Intent(this@RecycleActivity,ResultOfRecycleActivity::class.java)
                        intent.putExtra("successRecycleList",inputList)
                        startActivity(intent)
                        finish()


                    } else {
                        try {
                            hideProgressDialog()
                            val errorList = response.errorBody()
                            simpleAlertDialog("Error",errorList!!.string())
                            //Toast.makeText(this@LoginActivity, response.errorBody()?.string(),Toast.LENGTH_LONG).show()
                            //Log.i("errorbody", "onResponse: ${response.errorBody()?.string()}")
                            //Toast.makeText(this@LoginActivity, jsonObject.toString(), LENGTH_SHORT).show()

                        } catch (e: IOException) {
                            Toast.makeText(this@RecycleActivity, "Unknown error:", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<ReadyToCleanResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@RecycleActivity,"${t.message.toString()}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun returnContainer(storeid:Int) {
        quickToast("$currentStoreId")
        if(Constants.isNetworkAvailable(this)){
            //Toast.makeText(this,"network connected", Toast.LENGTH_SHORT).show()
            showProgressDialog("驗證中")

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val jsonObject = JsonObject()
            if (inputList.size != 0) {
                val jsonInputArray = JsonArray()
                for (i in 0..inputList.size-1 ){
                    jsonInputArray.add(inputList[i])
                }
                jsonObject.add("containers", jsonInputArray)
                jsonObject.addProperty("storeId",storeid)
            }

            /** Main Variables */
            val listCall: Call<ReturnResponse> = service.postReturn(
                Tokens.standAuthWithTime(Date()), Constants.myApiKey, jsonObject
            )

            listCall.enqueue(object : Callback<ReturnResponse> {
                override fun onResponse(
                    call: Call<ReturnResponse>,
                    response: Response<ReturnResponse>,
                ) {
                    if(response.isSuccessful){
                        val list: ReturnResponse? = response.body()
                        //Toast.makeText(this@SettingActivity,"${logoutList.toString()}",
                        //    Toast.LENGTH_SHORT).show()

                        readyToClean()

                        //val intent = Intent(this@SettingActivity,LoginActivity::class.java)
                        //startActivity(intent)
                        //finish()



                    } else {
                        try {
                            simpleAlertDialog("Error",response.errorBody()!!.string())

                            //Toast.makeText(this@LoginActivity, response.errorBody()?.string(),Toast.LENGTH_LONG).show()
                            //Log.i("errorbody", "onResponse: ${response.errorBody()?.string()}")
                            //Toast.makeText(this@LoginActivity, jsonObject.toString(), LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            Toast.makeText(this@RecycleActivity, "Unknown error:", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    }
                    hideProgressDialog()
                }

                override fun onFailure(call: Call<ReturnResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@RecycleActivity,"${t.message.toString()}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    override fun onAClick(position: Int) {
        val clickItems = childList[position]
    }



}








