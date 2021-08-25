package com.example.goodtogoappv11

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.MY_APIKEY_STATION
import com.example.goodtogoappv11.model.Constants.MY_PASSWORD
import com.example.goodtogoappv11.model.Constants.MY_PHONE
import com.example.goodtogoappv11.model.Tokens.standardAut
import com.example.goodtogoappv11.network.*
import com.example.goodtogoappv11.network.reload.ReloadResponse
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_custom_progress.*
import kotlinx.android.synthetic.main.layout_test_dialog.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val btn_sign_in: Button = findViewById(R.id.btn_sign_in)
        val et_password: EditText = findViewById(R.id.et_password)
        val et_name: EditText = findViewById(R.id.et_name)




        btn_sign_in.setOnClickListener{
            Log.i("Login", "login button clicked")
            if (
                et_name.text.toString().isEmpty())  {
                    Toast.makeText(this, "輸入錯誤喔!",LENGTH_SHORT).show()
                } else {
                    login(et_name.text.toString(),et_password.text.toString())
                    showProgressDialog("載入中...")


                }

            }


        val btnSignInShortcut: Button = findViewById(R.id.btnSignInPass)
        btnSignInShortcut.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            Log.i("QuickLogin","quick pass clicked")
        }


        val testTopic = "DeliveryList - Reload history"
        btn_logout.text = String.format(getString(R.string.test_name),testTopic)
        btn_logout.setOnClickListener{
            Log.i("Logout", "logout button clicked")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Log Out")
                .setMessage("確定？")
                .setPositiveButton("Log out"){
                        dialog, _->
                    dialog.dismiss()
                    //logout(Constants.BASE_URL)
                    //challengetoken()
                    //containersGetList()
                    //boxtosignGetList()
                    //customDialog("test")
                    reloaddGetList()
                }
                .setNegativeButton("Stay"){
                        dialog, _->
                    dialog.dismiss()
                }
                .create()
                .show()
                    

        }
    }




    fun boxtosignGetList() {
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)


            val listCall: Call<BoxToSignListResponse> = service.getBoxToSignList(
                standardAut(), Constants.MY_APIKEY_STATION
            )

            listCall.enqueue(object : Callback<BoxToSignListResponse>{
                override fun onResponse(
                    call: Call<BoxToSignListResponse>,
                    response: Response<BoxToSignListResponse>,
                ) {
                    if(response.isSuccessful){
                        val list: BoxToSignListResponse? = response.body()
                        Toast.makeText(this@LoginActivity,"${list.toString()}",Toast.LENGTH_SHORT).show()
                        Log.i("Response Result","BoxToSignListResponse")




                    } else {
                        val rc = response.code()
                        when(rc){
                            400 -> {
                                Log.e("Error 400", "Bad Connection")
                            }
                            404 -> {
                                Log.e("Error 404", "Not found")
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

                override fun onFailure(call: Call<BoxToSignListResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,"${t.message.toString()}",Toast.LENGTH_SHORT).show()
                }

            })

        }
    }


    private fun login(phoneInput:String, passwordInput: String?){
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val jsonObject = JSONObject()
            jsonObject.put("phone",MY_PHONE)
            jsonObject.put("password", MY_PASSWORD)

            val listCall: Call<LoginResponse> = service.postLogin(
                Constants.reqID(), Constants.reqTime, jsonObject
            )

            listCall.enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) {
                    if(response.isSuccessful){
                        val loginList: LoginResponse? = response.body()
                        Toast.makeText(this@LoginActivity,"${loginList.toString()}",Toast.LENGTH_SHORT).show()
                        Log.i("Response Result","$loginList")
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        //TODO: update secretKey and APIkey in Constants<object>

                        startActivity(intent)
                        hideProgressDialog()
                    } else {
                        val rc = response.code()
                        when(rc){
                            400 -> {
                                Log.e("Error 400", "Bad Connection")
                            }
                            404 -> {
                                Log.e("Error 404", "Not found")
                            }
                            /*401 -> {
                                Log.e("Error 401", response.message())
                            }*/
                            else ->{
                                Log.e("Error", response.message())
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,"${t.message.toString()}",Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

    fun logout(url : String) {
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected", LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val listCall: Call<LogoutResponse> = service.postLogout(
                standardAut(), Constants.MY_APIKEY_STATION
            )

            listCall.enqueue(object : Callback<LogoutResponse>{
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>,
                ) {
                    if(response.isSuccessful){
                        val logoutList: LogoutResponse? = response.body()
                        Toast.makeText(this@LoginActivity,"${logoutList.toString()}", LENGTH_SHORT).show()
                        //Log.i("Response Result","$LogoutResponse")
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

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,"${t.message.toString()}", LENGTH_SHORT).show()
                }

            })
        }
    }

    fun challengetoken(){
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected", LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val listCall: Call<UriResponse> = service.getContainersChallengeToken(
                standardAut(), Constants.MY_APIKEY_STATION
            )

            listCall.enqueue(object : Callback<UriResponse>{
                override fun onResponse(
                    call: Call<UriResponse>,
                    response: Response<UriResponse>,
                ) {
                    if(response.isSuccessful){
                        val list: UriResponse? = response.body()
                        Toast.makeText(this@LoginActivity, list.toString(), LENGTH_SHORT).show()
                        Log.i("Response Result","$list")
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

                override fun onFailure(call: Call<UriResponse>, t: Throwable) {
                    Log.e("ErrorrErrorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,"${t.message.toString()}", LENGTH_SHORT).show()
                }

            })
        }


    }

    private fun containersGetList() {
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val listCall: Call<ContainerGetListResponse> = service.containersGetList(
                Constants.reqID(), Constants.reqTime
            )

            listCall.enqueue(object : Callback<ContainerGetListResponse>{
                override fun onResponse(
                    call: Call<ContainerGetListResponse>,
                    response: Response<ContainerGetListResponse>,
                ) {
                    if(response.isSuccessful){
                        val loginList: ContainerGetListResponse? = response.body()
                        Toast.makeText(this@LoginActivity,"${loginList.toString()}",Toast.LENGTH_LONG).show()
                        //Log.i("Response Result","${ContainerGetListResponse()}")
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

                override fun onFailure(call: Call<ContainerGetListResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,t.message.toString(),Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

    private fun reloaddGetList() {
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            /** Main Variables */
            val listCall: Call<ReloadResponse> = service.getReloadList(
                standardAut(), MY_APIKEY_STATION
            )

            listCall.enqueue(object : Callback<ReloadResponse>{
                override fun onResponse(
                    call: Call<ReloadResponse>,
                    response: Response<ReloadResponse>,
                ) {
                    if(response.isSuccessful){
                        val loginList: ReloadResponse? = response.body()
                        Toast.makeText(this@LoginActivity,"${loginList.toString()}",Toast.LENGTH_LONG).show()
                        //Log.i("Response Result","${ContainerGetListResponse()}")

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
                        Log.i("Auth", "onResponse: ${standardAut()}")
                    }
                }

                override fun onFailure(call: Call<ReloadResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,t.message.toString(),Toast.LENGTH_SHORT).show()
                }

            })

        }
    }




    fun customDialog(text: String) {
        val newBuilder = Dialog(this)
        newBuilder.setContentView(R.layout.layout_test_dialog)
        newBuilder.tv_dialog_test.text = text
        newBuilder.show()
    }

    fun dexterpermission(){
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.CAMERA
        )
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?,
                ) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun contest(){
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val listCall: Call<TestResponse> = service.testConnection(123,"I'm trying!")

            listCall.enqueue(object : Callback<TestResponse>{
                override fun onResponse(
                    call: Call<TestResponse>,
                    response: Response<TestResponse>,
                ) {
                    if(response.isSuccessful){
                        val testList: TestResponse? = response.body()
                        Toast.makeText(this@LoginActivity,"${testList.toString()}",Toast.LENGTH_SHORT).show()
                        Log.i("Response Result","$testList")
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

                override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,"${t.message.toString()}",Toast.LENGTH_SHORT).show()
                }

            })

        }else{




        }
    }























}
