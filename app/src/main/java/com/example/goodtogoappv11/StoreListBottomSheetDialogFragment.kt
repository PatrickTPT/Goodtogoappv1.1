package com.example.goodtogoappv11

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.StoreListAdapter
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.recycleStore
import com.example.goodtogoappv11.model.Constants.vStoreList
import com.example.goodtogoappv11.model.Store
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_store_dialog.*
import kotlinx.android.synthetic.main.fragment_store_dialog.view.*
import java.util.*
import kotlin.collections.ArrayList

class StoreListBottomSheetDialogFragment : BottomSheetDialogFragment(), OnAClickListener {

    //lateinit var s: ArrayList<Store>
    private var displayStoreList = ArrayList<Store>()
    private lateinit var searchView : androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //s = intent.getSerializableExtra("storeList") as ArrayList<Store>
        displayStoreList.addAll(vStoreList.sortedBy{it.id})


    }

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_store_dialog, container, false)

        view.rv_store_list_fragment.layoutManager = LinearLayoutManager(activity)
        view.rv_store_list_fragment.adapter = StoreListAdapter(displayStoreList, requireContext(), this)
        view.rv_store_list_fragment.setHasFixedSize(true)


         view.iv_close_fragment.setOnClickListener {
             dismiss()
         }

         view.sv_search_store_list.setOnQueryTextListener(
             object: SearchView.OnQueryTextListener{
                 override fun onQueryTextSubmit(query: String?): Boolean {
                     return true
                 }

                 override fun onQueryTextChange(newText: String?): Boolean {
                     if (newText != null) {
                         displayStoreList.clear()
                         val search = newText.lowercase(Locale.getDefault())
                         Constants.vStoreList.forEach {
                             if (it.viewType == Constants.VIEW_TYPE_ONE) {
                                 if (it.id.toString().lowercase().contains(search)
                                     || it.name.toString().lowercase().contains(search)
                                 ) {
                                     displayStoreList.add(it)
                                 }
                             }
                         }
                         rv_store_list_fragment.adapter?.notifyDataSetChanged()

                     } else {

                     }
                     return true
                 }


             })

        return view




    }

    override fun onAClick(position: Int) {

        val clickedItem = displayStoreList[position]

        recycleStore = clickedItem.name.toString()
        Toast.makeText(context,"你按到 $recycleStore", Toast.LENGTH_SHORT).show()
        Log.i("TAG", "onAClick: store clickEd!")
        dismiss()

    }


}