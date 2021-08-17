package com.example.goodtogoappv11


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.InStockAdapter
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.BOXING_REQUEST_CODE
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import com.example.goodtogoappv11.model.Constants.marrayList
import com.example.goodtogoappv11.model.Constants.mediumList
import kotlinx.android.synthetic.main.activity_instock.*
import kotlinx.android.synthetic.main.item_sort.*
import java.util.*

class InStockActivity : BaseActivity(), OnAClickListener {


    //private lateinit var binding: ActivityMainBinding


    //private lateinit var adapter: InStockAdapter
    private var filterChoice: Int? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instock) //<-- 原本的直接setContentView
        //新的利用binding, layoutInflater與root
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)*/
        titleText = "庫存"
        setupLightActionBar()


        displayList.clear()
        displayList.addAll(mediumList.filter{it.status == Constants.BOX_STATUS_BOXED})

        setupBottomInformationBox()
        setupInstockRecyclerView()





        ll_sort_button.setOnClickListener(){

            val sortMethod:Array<String> =arrayOf("容器種類","裝箱日期↓(新到舊)","裝箱日期↑(舊到新)")
            val builder = AlertDialog.Builder(this)

            builder.setTitle("排序")

            builder.setItems(sortMethod) { _, which ->
                tv_sort_title.text = sortMethod[which]
                when (which) {
                    0 -> sortByCupType()
                    1 -> sortByBoxingDateDescending()
                    2 -> sortByBoxingDate()
                }
            //Toast.makeText(applicationContext,sortMethod[which],Toast.LENGTH_SHORT).show()
            }

            builder.create().show()
        }


        tv_filter_not.setOnClickListener(){
            if (filterChoice != FILTER_NOT) {
                ll_filter_not.setBackgroundResource(R.drawable.layout_filter_bg)
                tv_filter_not.setTextColor(getResources().getColor(R.color.goodtogo_yellow))
                ll_filter_grande.setBackgroundResource(R.drawable.layout_filter_grey_bg)
                tv_filter_grande.setTextColor(getResources().getColor(R.color.mediumGrey))
                ll_filter_tall.setBackgroundResource(R.drawable.layout_filter_grey_bg)
                tv_filter_tall.setTextColor(getResources().getColor(R.color.mediumGrey))
                displayList.clear()
                mediumList.clear()
                mediumList.addAll(marrayList)
                displayList.addAll(marrayList)
                filterChoice = FILTER_NOT
                adapter.notifyDataSetChanged()
            }
        }

        tv_filter_grande.setOnClickListener(){
            if(filterChoice != FILTER_GRANDE) {
                ll_filter_grande.setBackgroundResource(R.drawable.layout_filter_bg)
                tv_filter_grande.setTextColor(getResources().getColor(R.color.goodtogo_yellow))
                ll_filter_not.setBackgroundResource(R.drawable.layout_filter_grey_bg)
                tv_filter_not.setTextColor(getResources().getColor(R.color.mediumGrey))
                ll_filter_tall.setBackgroundResource(R.drawable.layout_filter_grey_bg)
                tv_filter_tall.setTextColor(getResources().getColor(R.color.mediumGrey))
                displayList.clear()
                mediumList.clear()
                marrayList.forEach {
                    if (it.cupType == "Grande") {
                        displayList.add(it)
                        mediumList.add(it)
                    }
                }
                filterChoice = FILTER_GRANDE
                adapter.notifyDataSetChanged()
            }
        }

        tv_filter_tall.setOnClickListener(){
            if(filterChoice != FILTER_TALL) {
                ll_filter_tall.setBackgroundResource(R.drawable.layout_filter_bg)
                tv_filter_tall.setTextColor(getResources().getColor(R.color.goodtogo_yellow))
                ll_filter_not.setBackgroundResource(R.drawable.layout_filter_grey_bg)
                tv_filter_not.setTextColor(getResources().getColor(R.color.mediumGrey))
                ll_filter_grande.setBackgroundResource(R.drawable.layout_filter_grey_bg)
                tv_filter_grande.setTextColor(getResources().getColor(R.color.mediumGrey))
                displayList.clear()
                mediumList.clear()
                marrayList.forEach {
                    if (it.cupType == "Tall") {
                        mediumList.add(it)
                        displayList.add(it)
                    }
                }
                filterChoice = FILTER_TALL
                adapter.notifyDataSetChanged()
            }
        }

        //TODO: (add tag view generator)

        // 定義頂部頁面列
       /*
        // 設定回parent鍵(需至AndroidManifest.xml定義parent)
        actionBar.setDisplayHomeAsUpEnabled(true)*/


        packingFab.setOnClickListener {
            startActivityForResult(Intent(this, BoxingActivity::class.java),BOXING_REQUEST_CODE,null)

        }



        /*val countrySearch = findViewById<SearchView>(R.id.country_search)*/




        }

    private fun setupBottomInformationBox() {
        tv_box_number.text = displayList.size.toString()
        val totalCups:Int = displayList.sumBy{it.cupNumber!!}
        tv_cup_number.text = totalCups.toString()
    }


    private fun setupInstockRecyclerView(){
            try{
            rv_InStock.layoutManager =
                LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            adapter = InStockAdapter(this, displayList,this)
            rv_InStock.adapter = adapter
                rv_InStock.setHasFixedSize(true)
            }
                catch(e:Exception){
                    Log.e("TAG",  "$e")
                }

            if (rv_InStock != null && displayList.size > 0) {
                tv_noContainer1_1.visibility = View.GONE
                sortArrayListbyPageDefault()
                filterChoice = FILTER_NOT

            } else {
                tv_noContainer1_1.visibility = View.VISIBLE
            }

        }

    override fun onAClick(position: Int) {
        //Toast.makeText(this,"Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = Datasource().loadBoxes()[position]

        //clickedItem.expandable = !clickedItem.expandable
        /*Toast.makeText(this,
            "Item $position expanded: ${clickedItem.expandable}, New Boxid:${clickedItem.boxid}",
            Toast.LENGTH_SHORT).show()
        *///adapter.notifyItemChanged(position)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.mu_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            val editText =
                searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

            editText.hint = "箱子ID、容器ID..."

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.lowercase(Locale.getDefault())
                        marrayList.forEach {
                            if (it.viewType == VIEW_TYPE_ONE) {
                                if (it.boxid.toString().lowercase().contains(search)
                                    || it.date.toString().lowercase().contains(search)
                                ) {
                                    displayList.add(it)
                                }
                            }
                        }
                        adapter.notifyDataSetChanged()

                    } else {
                        sortByBoxingDateDescending()
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


    private fun sortArrayListbyPageDefault(){
        sortByBoxingDateDescending()
    }





    companion object{

        val FILTER_NOT = 1
        val FILTER_GRANDE = 2
        val FILTER_TALL = 3

        val SORT_TYPE_CUP = 1
        val SORT_TYPE_DATE_DESCENDIMG = 2
        val SORT_TYPE_DATE = 3
        val SORT_TYPE_STORE = 4

        val EDIT_BOX_IN_STOCK_REQUEST_CODE = 1
        val DELIVERY_OPTION_REQUEST_CODE = 2

    }

}
