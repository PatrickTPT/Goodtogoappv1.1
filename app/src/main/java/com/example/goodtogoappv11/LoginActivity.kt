package com.example.goodtogoappv11

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.UriResponse
import com.example.goodtogoappv11.network.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders.*
import kotlinx.android.synthetic.main.activity_login.*
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
            if (
                et_name.text.toString().isEmpty())  {
                    Toast.makeText(this, "輸入錯誤喔!",LENGTH_SHORT).show()
                } else {

                    val intent = Intent(this, MainActivity::class.java)
                showProgressDialog("載入中...")

                    intent.putExtra("username",et_name.text.toString())
                    intent.putExtra("password",et_password.text.toString())
                    startActivity(intent)
                Handler().postDelayed({
                    hideProgressDialog()
                }, 1000)
                }

            }
        val btnSignInShortcut: Button = findViewById(R.id.btnSignInShortcut)

        btnSignInShortcut.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

            //Toast.makeText(this,"Login: $MY_PHONE",Toast.LENGTH_SHORT).show()
            Log.i("QuickLogin","quick pass clicked")
            //login(Constants.MY_PHONE,Constants.MY_PASSWORD)
            //contest()
        }

        btn_logout.setOnClickListener{
            Log.i("Logout", "logout button clicked")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Log Out")
                .setMessage("R U Sure?")
                .setPositiveButton("Log out"){
                        dialog, _->
                    dialog.dismiss()
                    //logout(Constants.BASE_URL)
                    challengetoken()
                }
                .setNegativeButton("Stay"){
                        dialog, _->
                    dialog.dismiss()
                }
                .create()
                .show()
                    

        }
    }


    fun dexterpermission(){
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.CAMERA
        )
            .withListener(object: MultiplePermissionsListener{
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

    fun login(phone:String, password: String?){
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected",Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            val listCall: Call<LoginResponse> = service.postLogin(
                Constants.reqID(), Constants.reqTime,phone, password
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

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,"${t.message.toString()}",Toast.LENGTH_SHORT).show()
                }

            })

        }else{

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
                standardAut(), Constants.APIKEY_MY_ADMIN
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
                standardAut(), Constants.APIKEY_MY_ADMIN
            )

            listCall.enqueue(object : Callback<UriResponse>{
                override fun onResponse(
                    call: Call<UriResponse>,
                    response: Response<UriResponse>,
                ) {
                    if(response.isSuccessful){
                        val list: UriResponse? = response.body()
                        Toast.makeText(this@LoginActivity,"${list.toString()}", LENGTH_SHORT).show()
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

                override fun onFailure(call: Call<UriResponse>, t: Throwable) {
                    Log.e("ErrorrErrorr", t!!.message.toString())
                    Toast.makeText(this@LoginActivity,"${t.message.toString()}", LENGTH_SHORT).show()
                }

            })
        }


    }


    private fun standardAut(): String {

            val d = Date()
            d.time = d.time + 259200





        /*if (Build.VERSION.SDK_INT >= 26) {
            base64Encoded = Base64.getEncoder().encode(Constants.SECRET_KEY_CURRENT.toByteArray()).toString();
        } else {
            base64Encoded = android.util.Base64.encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray(), 0)
        }*/

        //var base64Key = BASE64.encode(Constants.SECRET_KEY_CURRENT.getEncoded())
            /*val base64Encoded = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray())
            } else {

            }*/
        //val hmacKey: ByteArray = Constants.SECRET_KEY_CURRENT.toByteArray(StandardCharsets.UTF_8)
            //var key : Key = SecretKeySpec(Constants.SECRET_KEY_CURRENT.toByteArray(),SignatureAlgorithm.HS256.getJcaName())
            val claims : Claims = Jwts.claims()

        val base64Encoded = Base64.encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray())

            return Jwts.builder()
                .setId(Constants.reqID())
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(d)
                .signWith(SignatureAlgorithm.HS256,base64Encoded)
                .compact()
        return ""
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
