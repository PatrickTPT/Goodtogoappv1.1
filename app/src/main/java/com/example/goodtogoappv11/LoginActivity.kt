package com.example.goodtogoappv11

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.vRoleList
import com.example.goodtogoappv11.model.Tokens.standardAut
import com.example.goodtogoappv11.network.*
import com.google.gson.JsonObject
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_custom_progress.*
import kotlinx.android.synthetic.main.layout_test_dialog.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*


class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val btn_sign_in: Button = findViewById(R.id.btn_sign_in)
        val et_password: EditText = findViewById(R.id.et_password)
        val et_name: EditText = findViewById(R.id.et_name)



        //success
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
            et_name.setText("0963328359")

            //startActivity(Intent(this, MainActivity::class.java))

        }





        val testTopic = "DeliveryList - Reload history"
        btn_logout.text = String.format(getString(R.string.test_name),testTopic)
        btn_logout.visibility = View.GONE
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

                }
                .setNegativeButton("Stay"){
                        dialog, _->
                    dialog.dismiss()
                }
                .create()
                .show()
                    

        }
    }







    private fun login(phoneInput:String, passwordInput: String){
        if(Constants.isNetworkAvailable(this)){
            //Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            /** Main Variables */
            val jsonObject = JsonObject()
            jsonObject.addProperty("phone",phoneInput)
            jsonObject.addProperty("password", passwordInput)


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
                        //Toast.makeText(this@LoginActivity,"${loginList!!.roleList.toString()}",Toast.LENGTH_SHORT).show()
                        //Log.i("LoginResponse Result","$loginList")
                        //val savedLoginRoleList = ArrayList<Role>()
                            if (vRoleList.size == 0) {
                                for (i in loginList!!.roleList.indices) {
                                    vRoleList.add(loginList.roleList.get(i))
                                }
                            }


                        val intent = Intent(this@LoginActivity, RoleSelectionActivity::class.java)


                        startActivity(intent)
                        hideProgressDialog()
                        finish()
                    } else {
                        try {
                            hideProgressDialog()
                            simpleAlertDialog("Error",response.errorBody()!!.string())

                            //Toast.makeText(this@LoginActivity, response.errorBody()?.string(),Toast.LENGTH_LONG).show()
                            //Log.i("errorbody", "onResponse: ${response.errorBody()?.string()}")
                            //Toast.makeText(this@LoginActivity, jsonObject.toString(), LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            Toast.makeText(this@LoginActivity, "Unknown error:", LENGTH_SHORT).show()
                            e.printStackTrace()
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


    /*
    object ErrorUtils {
        fun parseError(response: Response<*>): APIError {
            val converter: Converter<ResponseBody, APIError> = ServiceGenerator.retrofit()
                .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))
            val error: APIError
            error = try ({
                converter.convert(response.errorBody())
            })!! catch (e: IOException) {
                return APIError()
            }
            return error
        }
    }
    */
    class APIError {

        val code: String? = null
        val type : String? = null
        val message : String? = null
        val extra : Any? = null

        fun code(): String? {
            return code
        }

        fun type(): String? {
            return type
        }

        fun message(): String? {
            return message
        }

        fun extra(): Any? {
            return extra
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
