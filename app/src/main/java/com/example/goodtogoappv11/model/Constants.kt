package com.example.goodtogoappv11.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.goodtogoappv11.data.Datasource
import java.util.*

object Constants {

    //viewholder類型定義
    const val VIEW_TYPE_ONE = 1
    const val VIEW_TYPE_TWO = 2

    //杯子狀態定義
    const val BOX_STATUS_READYTOCLEAN = 1 //已回收
    const val BOX_STATUS_BOXED = 2 //已裝箱
    const val BOX_STATUS_TODELIVER = 3 //待配送
    const val BOX_STATUS_TOBESIGNED = 4 //待簽收 UNSIGNED
    const val BOX_STATUS_TOBEUSED = 5 //待使用 SIGNED
    const val BOX_STATUS_INUSE = 6 //使用中
    //UNDELIVERED, DELIVERED, UNSIGNED, SIGNED, RELOAD, STOCKED


    //連線驗證定義
    const val APP_ID: String = "test"
    const val APIKEY_MY_ADMIN = "4309892e10"
    const val SECRET_KEY_CURRENT = "fBX1EGWYgjyx9eyU"
    const val BASE_URL: String = "https://app.goodtogo.tw/"
    const val TEST_URL: String = "https://jsonplaceholder.typicode.com/"

    const val MY_PHONE: String = "0963328359"
    const val MY_PASSWORD: String = ""

    val reqTime: String = Date().time.toString()
    fun reqID(): String {
        val hex: Array<Char> =
            arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        var id: String = ""
        for (i in 0..9) id += hex[Random().nextInt(16)].toString()
        return id
    }

    //Checking Network
    fun isNetworkAvailable(context: Context) : Boolean{
        val connectivityManager = context.
        getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo !=null && networkInfo.isConnectedOrConnecting
        }

    }



    //Request Code
    const val BOXING_REQUEST_CODE =1
    const val ADD_DELIVERY_DETAIL_REQUEST_CODE =2




    // some variable
    var instockList= ArrayList<Box>()
    val marrayList = Datasource().loadBoxes()
    var mediumList: MutableList<Box> = marrayList
    var randomCupNumberStand = 30020
    var latestBoxId = 3018001
}