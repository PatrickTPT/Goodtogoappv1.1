package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.RoleSelectionAdapter
import com.example.goodtogoappv11.model.Constants.myApiKey
import com.example.goodtogoappv11.model.Constants.mySecretKey
import com.example.goodtogoappv11.model.Constants.myStationName
import com.example.goodtogoappv11.model.Constants.vRoleList
import com.example.goodtogoappv11.network.Role
import kotlinx.android.synthetic.main.activity_role_selection.*

class RoleSelectionActivity : AppCompatActivity(), OnAClickListener {

    private lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private val displayRoleList= ArrayList<Role>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)

        val actionBar = supportActionBar
        actionBar!!.title ="選擇登入身份"



        setUpRoleSelectionRecyclerView()

    }



    private fun setUpRoleSelectionRecyclerView() {

              if (displayRoleList.size == 0) {
                for (i in vRoleList.indices) {
                    if (vRoleList.get(i).roleType == "station" ||
                        vRoleList.get(i).roleType == "store"
                    )
                        displayRoleList.add(vRoleList.get(i))
                }
            }

        rv_role_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = RoleSelectionAdapter(this,displayRoleList,this)
        rv_role_list.adapter = adapter
        rv_role_list.setHasFixedSize(true)

    }

    override fun onAClick(position: Int) {
        val clickedItem = displayRoleList[position]

        fun getName(): String {
            return if (clickedItem.roleType == "station") {
                clickedItem.stationName
            } else {
                clickedItem.storeName
            }
        }


        if (clickedItem.roleType == "station"){
            val intent = Intent(this@RoleSelectionActivity, MainActivity::class.java)
            //intent.putExtra("EXTRACT_STORE_NAME",getName())
            myStationName = getName()
            myApiKey = clickedItem.apiKey
            mySecretKey = clickedItem.secretKey
            /*Toast.makeText(this, "Station: $myStationName\n" +
                    "ApiKey: $myApiKey\n" +
                    "SecretKey: $mySecretKey",
                Toast.LENGTH_SHORT).show()*/
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "店舖：${getName()}\n目前尚未開放，\n請再等一下！", Toast.LENGTH_SHORT).show()
        }





    }






}