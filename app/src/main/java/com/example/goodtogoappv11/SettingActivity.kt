package com.example.goodtogoappv11


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.myApiKey
import com.example.goodtogoappv11.model.Tokens
import com.example.goodtogoappv11.network.LogoutResponse
import com.example.goodtogoappv11.network.ourService
import kotlinx.android.synthetic.main.activity_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        titleText = "設定"
        setupLightWeightActionBar()

        btn_sign_out.setOnClickListener(){

            showProgressDialog("登出中...")
            logout()


            /*val builder = AlertDialog.Builder(this)
            builder.setTitle("確定登出")
            builder.setMessage("Are you sure?")
            builder.setPositiveButton("登出") { dialog, _ ->
                dialog.dismiss()
                startActivity(Intent(this,LoginActivity::class.java))
                exitProcess(-1)
                finish()

            }
            builder.setNegativeButton("回去工作") { dialog, id ->
                dialog.dismiss()

            }
            builder.show()*/

        }

    }

    fun logout() {
        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(this,"network connected", Toast.LENGTH_SHORT).show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ourService = retrofit
                .create<ourService>(ourService::class.java)

            /** Main Variables */
            val listCall: Call<LogoutResponse> = service.postLogout(
                Tokens.standardAut(), myApiKey
            )

            listCall.enqueue(object : Callback<LogoutResponse> {
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>,
                ) {
                    if(response.isSuccessful){
                        val logoutList: LogoutResponse? = response.body()
                        Toast.makeText(this@SettingActivity,"${logoutList.toString()}",
                            Toast.LENGTH_SHORT).show()
                        //Log.i("Response Result","$LogoutResponse")
                    } else {
                        try {
                            hideProgressDialog()
                            simpleAlertDialog("Error",response.errorBody()!!.string())

                            //Toast.makeText(this@LoginActivity, response.errorBody()?.string(),Toast.LENGTH_LONG).show()
                            //Log.i("errorbody", "onResponse: ${response.errorBody()?.string()}")
                            //Toast.makeText(this@LoginActivity, jsonObject.toString(), LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            Toast.makeText(this@SettingActivity, "Unknown error:", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Log.e("Errorr", t!!.message.toString())
                    Toast.makeText(this@SettingActivity,"${t.message.toString()}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}