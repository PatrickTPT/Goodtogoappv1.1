package com.example.goodtogoappv11

import android.Manifest
import android.app.Activity
import android.app.Dialog
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
import com.example.goodtogoappv11.model.Boxes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.dialog_custom_progress.*
import kotlinx.android.synthetic.main.item_bottom_infobox.*
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
    lateinit var rvInfoBox: RecyclerView
    var childList: ArrayList<Boxes.BoxChild> = ArrayList()
    var cupTypeList: ArrayList<String> = ArrayList()
    open lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private lateinit var mProgressDialog: Dialog

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
            setMessage("??????!?????????????????????$name")
            setTitle("??????????????????")
            setPositiveButton("ok") { dialog, which ->
                ActivityCompat.requestPermissions(this@BaseInputActivity,
                    arrayOf(permission),
                    requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


    // ??????MenuItem
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.layout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // ???Item???????????????
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
                                Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show()
                            }

                            //Toast.makeText(this,"Scan Result: ${it.text}",Toast.LENGTH_LONG).show()
                            /*
                            //??????-??????????????????2: ????????????
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

                //???????????????
                //val scannerFragment = ScannerFragment()
                //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,scannerFragment).commit()

                //var scannedList : MutableList<String> = ArrayList()
                //val scannedCode = findViewById<TextView>(com.budiyev.android.codescanner.R.id.scannedCode)

                return true
                //scannerView.visibility = View.VISIBLE

            }
            R.id.keyin_input -> {

                //???????????????
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
            //actionBar.title = "??????(from $storeName )"
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
                .setMessage("???????????????????????????????????????")
                .setNeutralButton("??????") { dialog, _ ->
                    dialog.dismiss()
                    super.onBackPressed()
                }
                .setPositiveButton("?????????") { dialog, _ ->
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

    fun infoboxExpansion2(){
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


    fun showProgressDialog(text: String){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_custom_progress)
        mProgressDialog.tv_progress_text.text = text

        mProgressDialog.show()

        //TODO: add this when timeout function added:
        // mProgressDialog.setCancelable(false)
        //
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun simpleAlertDialog(title: String, message:String){
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK"){ dialogInterface, which ->
                dialogInterface.dismiss()
            }
        val simpleAlertDialog = builder.create()
        //simpleAlertDialog.setCancelable(false) //dialog can't be cancel outside
        simpleAlertDialog.show()
    }

}


