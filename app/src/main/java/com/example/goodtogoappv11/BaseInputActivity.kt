package com.example.goodtogoappv11

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.*
import com.example.goodtogoappv11.model.Box
import com.example.goodtogoappv11.model.Boxes
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_BOXED
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import com.example.goodtogoappv11.model.Constants.latestBoxId
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.item_bottom_infobox.*
import java.text.SimpleDateFormat
import java.util.*


open class BaseInputActivity : AppCompatActivity() {
    val CAMERA_RQ = 102 //camera permission elements
    private lateinit var codeScanner: CodeScanner
    var inputList: ArrayList<String> = ArrayList()
    var infoboxExpanded: Boolean = false
    lateinit var idInputSlot : EditText
    lateinit var inputBtn : Button
    lateinit var tv_box_count : TextView
    lateinit var tv_scanWord: TextView
    lateinit var clearBtn: Button
    lateinit var proceedFab: FloatingActionButton
    var childList: ArrayList<Boxes.BoxChild> = ArrayList()
    var cupTypeList: ArrayList<String> = ArrayList()
    open lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>


    //val scannerView = findViewById<CodeScannerView>(com.budiyev.android.codescanner.R.id.scanner_View)



    // permission elements
    private fun cameraPermissionCheck(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext,
                    permission) == PackageManager.PERMISSION_GRANTED -> {
                    println("permission granted")
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission,
                    name,
                    requestCode)
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    // permission elements
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        when (requestCode) {
            CAMERA_RQ -> innerCheck("camera")
        }


    }

    //camera permission elements
    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("喔不!需要以下權限：$name")
            setTitle("開啟權限請求")
            setPositiveButton("ok") { dialog, which ->
                ActivityCompat.requestPermissions(this@BaseInputActivity,
                    arrayOf(permission),
                    requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


    // 建立MenuItem
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.layout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 各Item選擇後動作
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items

        when (item.itemId) {
            R.id.qrcode_input -> {
                //camera permission elements
                cameraPermissionCheck(Manifest.permission.CAMERA, "camera", CAMERA_RQ)

/*
                codeScanner = CodeScanner(this,scannerView)
                codeScanner.apply {
                    camera = CodeScanner.CAMERA_BACK
                    formats = CodeScanner.ALL_FORMATS
                    autoFocusMode = AutoFocusMode.SAFE
                    scanMode = ScanMode.CONTINUOUS
                    isAutoFocusEnabled = true
                    isFlashEnabled = false

                    decodeCallback = DecodeCallback {
                        runOnUiThread {
                            if (it.text.contains("http://app.goodtogo.tw/c/")) {
                                var filteredText = it.text.removeRange(0, 25)
                                inputList.add(filteredText)
                                //scannedCode.text = "$inputList"
                            } else {
                                Toast.makeText(this, "不是好盒器", Toast.LENGTH_SHORT).show()
                            }

                            //Toast.makeText(this,"Scan Result: ${it.text}",Toast.LENGTH_LONG).show()
                            /*
                            //追加-儲存掃瞄結果2: 儲存結果
                            var filteredText = it.text.removeRange(0,2)
                            scannedList.add(filteredText)
                            //println("$scannedList")
                            scannedCode.text = "$scannedList"
                        */

                        }
                    }
                    errorCallback = ErrorCallback {
                        runOnUiThread {
                            Toast.makeText(this, "Camera Error : ${it.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
*/

                //掃瞄頁叫出
                //val scannerFragment = ScannerFragment()
                //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,scannerFragment).commit()

                //var scannedList : MutableList<String> = ArrayList()
                //val scannedCode = findViewById<TextView>(com.budiyev.android.codescanner.R.id.scannedCode)

                return true
                //scannerView.visibility = View.VISIBLE

            }
            R.id.keyin_input -> {

                //輸入頁叫出
                //val keyInFragment = keyInFragment()
                //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,keyInFragment).commit()
                //scannerView.visibility = View.GONE

                return true
            }
            R.id.other_input -> {

                //scannerView.visibility = View.GONE
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupActionBar() {
        /*val mToolBar = findViewById<Toolbar>(R.id.scanToolBar)
        setSupportActionBar(mToolBar)*/
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            //actionBar.title = "回收(from $storeName )"
            actionBar.setDisplayShowCustomEnabled(true)

        }
        /*mToolBar.setNavigationOnClickListener {
            onBackPressed()
        }*/
    }


    override fun onResume() {
        super.onResume()
        //codeScanner.startPreview()
    }

    override fun onPause() {
        //codeScanner.releaseResources()
        super.onPause()
    }

    override fun onBackPressed() {
        if(childList.size >0 || idInputSlot.text.toString().isNotEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Notice")
                .setMessage("操作到一半，確認要離開？")
                .setNeutralButton("離開") { dialog, _ ->
                    dialog.dismiss()
                    super.onBackPressed()
                }
                .setPositiveButton("留下來") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        } else {
            super.onBackPressed()
        }
    }

    fun showSnackbar(message: String, channel: Boolean=true) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        val params = snackBarView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackBarView.layoutParams = params
        if (channel) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorVerified
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorUnverified
                )
            )
        }
        snackBar.show()


    }

    fun inputCheck(activity: Activity) {}


    fun quickToast(message: String, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this, "${message}", Toast.LENGTH_SHORT).show()

    }

    fun infoboxExpansion(){
        if (infoboxExpanded) {
            tv_input_cup_wide.visibility = View.VISIBLE
        } else {
            tv_input_cup_wide.visibility = View.GONE
        }
        infoboxExpanded = !infoboxExpanded
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
            println("資料增加，總筆數: ${childList.size}")
            tv_box_count.visibility = View.VISIBLE
            tv_scanWord.text = "已登錄容器"
            showSnackbar("假裝驗證成功")


        }
    }

    fun createChild(cupid: String): Boxes.BoxChild{
        return Boxes.BoxChild("大器杯", cupid.toInt() ,
            Constants.BOX_STATUS_READYTOCLEAN, Constants.VIEW_TYPE_ONE)
    }



    fun infoboxDisplay(){
        when {
            childList.size == 1 -> {
                tv_scanWord.text = "尚未掃瞄任何容器"
                tv_box_count.visibility = View.GONE
                inputList.removeAt(0)

            }
            childList.size > 1 -> {
                inputList.removeAt(0)
            }
            childList.size == 0 -> {
                tv_scanWord.text = "尚未掃瞄任何容器"
                tv_box_count.visibility = View.GONE
                quickToast("沒有可清除的資料喔!")
            }
        }
        println("資料減少，總筆數: ${childList.size}")
        tv_box_count.text = childList.size.toString()
    }


    fun createBox(arrayChlidList: ArrayList<Boxes.BoxChild>):Box {
        val now = Date()
        val milliNow = now.time
        val calend = Calendar.getInstance(Locale.TAIWAN)
        calend.time = now
        /*val year:Int = calend.get(Calendar.YEAR)
        val month:Int = calend.get(Calendar.MONTH+1)
        val day:Int = calend.get(Calendar.DAY_OF_MONTH)*/
        //var newboxid: Int=123
        val year = SimpleDateFormat("yy", Locale.TAIWAN).format(now).toInt()
        val month = SimpleDateFormat("MM",Locale.TAIWAN).format(now).toInt()
        val day = SimpleDateFormat("dd",Locale.TAIWAN).format(now)
        //newboxid = (year+3+month)*100000 + day*1000


        var newboxid = latestBoxId+1
        latestBoxId+=1
        //TODO: get box id from http


        return Box(
            null,
            null,
            newboxid,
            "0963328359",
            milliNow,
            BOX_STATUS_BOXED,
            arrayChlidList,
            null,
            childList.size,
            VIEW_TYPE_ONE
        )

        latestBoxId += 1

        /*TODO:
           cupType check(also array),
            */

    }



}


