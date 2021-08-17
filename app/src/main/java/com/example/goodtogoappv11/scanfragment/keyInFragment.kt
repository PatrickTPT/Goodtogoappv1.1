package com.example.goodtogoappv11.scanfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.goodtogoappv11.R

// TODOo: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class keyInFragment : Fragment() {
    // TODOo: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        /*
        var idInputSlot = findViewById<EditText>(R.id.idInputSlot)
        val inputBtn = findViewById<Button>(R.id.button3)
        var count = findViewById<TextView>(R.id.count)
        var scanWord: TextView = findViewById(R.id.scanWord)
        var inputList = arrayListOf<String>()
        val clearBtn: Button = findViewById(R.id.clearBtn)
        val dataSendFab: FloatingActionButton = findViewById(R.id.proceedBtn)


        inputBtn.setOnClickListener {
            if (
                idInputSlot.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "請輸入號碼!", Toast.LENGTH_SHORT).show()
            } else {

                inputList.add(idInputSlot.text.toString())
                idInputSlot.text.clear()
                count.text = inputList.size.toString() //要記的是list的資料筆數
                println("資料增加，總筆數: ${inputList.size}")
                count.visibility = View.VISIBLE
                scanWord.text = "已登錄容器"

            }
        }

        clearBtn.setOnClickListener {
            when {
                inputList.size == 1 -> {
                    scanWord.text = "尚未掃瞄任何容器"
                    count.visibility = View.GONE
                    inputList.removeAt(0)

                }
                inputList.size > 1 -> {
                    inputList.removeAt(0)
                }
                else -> {
                    scanWord.text = "尚未掃瞄任何容器"
                    count.visibility = View.GONE
                }
            }
            println("資料減少，總筆數: ${inputList.size}")
            count.text = inputList.size.toString()
        }

        dataSendFab.setOnClickListener {

            if(inputList.size > 0){

                val intent = Intent(this, ResultActivity::class.java)
                intent.putStringArrayListExtra("Test",inputList)
                startActivity(intent)
            } else {
                Toast.makeText(this, "無輸入資料!", Toast.LENGTH_SHORT).show()
            }
*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_key_in, container, false)
    }

    /*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment keyInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            keyInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
     */
}
