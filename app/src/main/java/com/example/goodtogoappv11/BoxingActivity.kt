package com.example.goodtogoappv11

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.InputInStockAdapter
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.model.Box
import com.example.goodtogoappv11.model.Boxes
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.mediumList
import com.example.goodtogoappv11.model.Constants.myStationName
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.item_bottom_infobox.rv_bottom_infobox
import java.text.SimpleDateFormat
import java.util.*

class BoxingActivity : BaseInputActivity(), OnAClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        childList.clear()


        //val stationName = intent.getStringExtra("EXTRACT_STATION_NAME")

        val actionBar = supportActionBar
        actionBar?.title = "裝箱｜${myStationName}"

        actionBar?.setDisplayHomeAsUpEnabled(true)


        idInputSlot = findViewById(R.id.et_idInputSlot)
        inputBtn = findViewById(R.id.button3)
        tv_box_count = findViewById(R.id.tv_box_count)
        tv_scanWord = findViewById(R.id.tv_scanWord)
        clearBtn = findViewById(R.id.btn_clear)
        proceedFab = findViewById(R.id.fab_proceed)

        //TODO: set default text
        tv_info_left_top.text = String.format(getString(R.string.boxing_in_stock))


        setBottomBoxRecyclerView()


        //TODO: set should-have input


        ll_infobox_header.setOnClickListener {
            if (rv_bottom_infobox.visibility == View.GONE) {
                rv_bottom_infobox.visibility = View.VISIBLE
                if(childList.size>0){
                    tv_info_left_top.visibility = View.VISIBLE
                }
            } else {
                rv_bottom_infobox.visibility = View.GONE
                tv_info_left_top.visibility = View.GONE
            }


        }


        // 輸入鍵功能宣告
        inputBtn.setOnClickListener {
            inputAction()
        }

        //清除資料功能宣告
        clearBtn.setOnClickListener {
            infoboxDisplay()
        }

        //傳送資料功能宣告
        proceedFab.setOnClickListener {
            //inputCheck(ResultOfEditBoxInStockActivity())
            resolveBox()

        }


    }




    fun resolveBox() {
    if (childList.size > 0)
    {
        val newBox = createBox(childList)
        mediumList.add(newBox)
        //TODO: send request
        // response ok -> next page
              setResult(Activity.RESULT_OK)
        val intent = Intent(this, ResultOfBoxingInStockActivity::class.java)
        intent.putStringArrayListExtra("Test", inputList)
        intent.putExtra("NewBox",newBox.boxid)
        startActivity(intent)
        finish()


        // TODO: response error -> alert dialog
        // TODO: make bad cup visible




    } else
    {
        quickToast("無已登錄容器")
    }

    //TODO: add progressdialog at the proper places
}


    fun setBottomBoxRecyclerView(){
        rv_bottom_infobox.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = InputInStockAdapter(this, childList,this)
        rv_bottom_infobox.adapter = adapter
        rv_bottom_infobox.setHasFixedSize(true)
    }

    fun inputAction(){
        if (
            idInputSlot.text.toString().isEmpty()
        ) {
            quickToast("請輸入號碼!")
        } else  {
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

    fun createChild(cupid: String): Boxes.BoxChild{
        return Boxes.BoxChild("", cupid.toInt() ,
            Constants.BOX_STATUS_READYTOCLEAN, Constants.VIEW_TYPE_ONE)
    }



    fun infoboxDisplay(){
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


    fun createBox(arrayChlidList: ArrayList<Boxes.BoxChild>): Box {
        val now = Date()
        val milliNow = now.time
        val calend = Calendar.getInstance(Locale.TAIWAN)
        calend.time = now
        /*val year:Int = calend.get(Calendar.YEAR)
        val month:Int = calend.get(Calendar.MONTH+1)
        val day:Int = calend.get(Calendar.DAY_OF_MONTH)*/
        //var newboxid: Int=123
        val year = SimpleDateFormat("yy", Locale.TAIWAN).format(now).toInt()
        val month = SimpleDateFormat("MM", Locale.TAIWAN).format(now).toInt()
        val day = SimpleDateFormat("dd", Locale.TAIWAN).format(now)
        //newboxid = (year+3+month)*100000 + day*1000


        var newboxid = Constants.latestBoxId +1
        Constants.latestBoxId +=1
        //TODO: get box id from http


        return Box(
            null,
            null,
            newboxid,
            "0963328359",
            milliNow,
            Constants.BOX_STATUS_BOXED,
            arrayChlidList,
            null,
            childList.size,
            Constants.VIEW_TYPE_ONE
        )

        Constants.latestBoxId += 1

        /*TODO:
           cupType check(also array),
            */

    }


    override fun onAClick(position: Int) {
        val clickItems = childList[position]

        tv_box_count.text = childList.size.toString()

            if (childList.size < 1)  {
                tv_box_count.visibility = View.GONE
                tv_scanWord.text = String.format(getString(R.string.no_input_container_yet))
            } else {
                tv_box_count.visibility = View.VISIBLE
            }
        }











}
