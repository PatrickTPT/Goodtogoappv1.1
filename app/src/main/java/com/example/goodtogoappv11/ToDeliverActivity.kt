package com.example.goodtogoappv11

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.collection.ArraySet
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.ToDeliverAdapter
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.mediumList
import kotlinx.android.synthetic.main.activity_to_deliver.*
import kotlinx.android.synthetic.main.item_bottom_infobox_fullwidth_2.*
import kotlinx.android.synthetic.main.item_sort.*
import java.util.*

class ToDeliverActivity : BaseActivity(), OnAClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_deliver)
        titleText = "待配送"
        setupLightWeightActionBar()
        tv_sort_title.text = "出貨日期↓(新到舊)"

        //ll_bottom_sheet_fullwidth2

        displayList.clear()
        //displayList.addAll(mediumList)
        displayList.addAll(mediumList.filter{it.status == Constants.BOX_STATUS_TODELIVER})

        //Log.i("displayList","${displayList}")


        setupToDeliverRecyclerView()
        setupBottomInformationBox()

        ll_sort_button.setOnClickListener(){

            val sortMethod:Array<String> =arrayOf("店舖","出貨日期↓(新到舊)","出貨日期↑(舊到新)")
            val builder = AlertDialog.Builder(this)


            builder.setTitle("排序")

            builder.setItems(sortMethod) { _, which ->
                tv_sort_title.text = sortMethod[which]
                when (which) {
                    0 -> sortByStoreDRA()
                    1 -> sortByDeliveryDateDescending()
                    2 -> sortByDeliveryDate()
                }

                //Toast.makeText(applicationContext,sortMethod[which],Toast.LENGTH_SHORT).show()
            }

            builder.create().show()
        }


        //ccv_box_info
        ccv_box_info.setOnClickListener {
            /*val behavior = BottomSheetBehavior.from(ccv_box_info)
            if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                //如果是展開狀態，則關閉，反之亦然
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }*/
        if (ll_bottom_expandable_info_detail.visibility == View.GONE) {
                ll_bottom_expandable_info_detail.visibility = View.VISIBLE

            } else {
                ll_bottom_expandable_info_detail.visibility = View.GONE

            }


            //bottomBoxExpansionState = !bottomBoxExpansionState
        }
    }

    private fun setupToDeliverRecyclerView() {
        rv_to_deliver.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ToDeliverAdapter(this, displayList, this)
        rv_to_deliver.adapter = adapter
        rv_to_deliver.setHasFixedSize(true)
        if (rv_to_deliver != null && displayList.size > 0) {
            tv_noContainer2_1.visibility = View.GONE
            sortByDeliveryDateDescending()
        } else {
            tv_noContainer2_1.visibility = View.VISIBLE
            rv_to_deliver.visibility = View.GONE
        }



    }

    override fun onAClick(position: Int) {
        val clickedItem = Datasource().loadBoxes()[position]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.mu_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            val editText =
                searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

            editText.hint = "箱子ID..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.lowercase(Locale.getDefault())
                        mediumList.forEach {
                            if (it.boxid.toString().lowercase().contains(search)
                                ||it.date.toString().lowercase().contains(search)) {
                                displayList.add(it)
                            }
                        }
                        adapter.notifyDataSetChanged()

                    } else {
                        sortByDeliveryDateDescending()
                    }
                    return true
                }

            })

        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun setupBottomInformationBox() {
        //main
        val storeSet = ArraySet<String>()

        tv_box_number2.text = displayList.size.toString()
        tv_unitA.text = "店 | "
        val totalCups:Int = displayList.sumBy{it.cupNumber!!}
        tv_cup_number2.text = totalCups.toString()
        tv_unitB.text = "個"
        //expanded
        tv_bottom_box_detail2.text = "a x 123\nb x 231\nc x 312"

    }



}