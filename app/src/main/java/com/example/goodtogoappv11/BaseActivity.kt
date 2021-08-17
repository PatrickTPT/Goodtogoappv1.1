package com.example.goodtogoappv11

import android.app.Dialog
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.model.Box
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_custom_progress.*

open class BaseActivity : AppCompatActivity() {


    private lateinit var mProgressDialog: Dialog
    open lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var titleText: String? = null

    //var arrayList = ArrayList<Box>()
    var displayList = ArrayList<Box>()


    var sortType: Int? = null
    //var displayListTest = ArrayList<Boxes.BoxLong>()

    fun showProgressDialog(text: String){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_custom_progress)
        mProgressDialog.tv_progress_text.text = text
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    /*open fun setupActionBar(){
        //val mainToolBar = findViewById<Toolbar>(R.id.mainToolBar)
        //setSupportActionBar(mainToolBar)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = titleText
        }
        *//*mainToolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }*/

    open fun setupLightWeightActionBar(){

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = titleText
        }
        // TODO: (NTH) add BackPressed listener to finish() current Activity
    }

    open fun setupFullActionBar(titleText: String){
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = titleText
        }
        // TODO: (NTH) add BackPressed listener to finish() current Activity
    }

    /*open fun setupIntegratedRecyclerView(
        rv_this: RecyclerView,
        ad_this: GoodToGoAdapter,
        tv_this: TextView
    ) {

        rv_this.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_this.adapter = adapter
        rv_this.setHasFixedSize(true)

        if (rv_this != null) {
            tv_this.visibility = View.GONE
        } else {
            tv_this.visibility = View.VISIBLE
        }
    }*/

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {

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
                        arrayList.forEach {
                            if (it.boxid.toString().lowercase().contains(search)
                                ||it.date.toString().lowercase().contains(search)) {
                                displayList.add(it)
                            }
                        }
                        adapter!!.notifyDataSetChanged()

                    } else {
                        displayList.clear()
                        displayList.addAll(arrayList)
                        adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })

        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }*/


    fun showSnackbarTest(message: String, channel: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        val params = snackBarView.layoutParams as FrameLayout.LayoutParams
        //params.gravity = Gravity.TOP
        snackBarView.layoutParams = params
        if (channel){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.snackbar_valid_color
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.snackbar_error_color
                )
            )
        }
        snackBar.show()


    }

    fun quickToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    /*private fun sortArrayListbyPageDefault(){
        displayList.clear()
        displayList.addAll(arrayList.sortedBy { it.date }.reversed())
        adapter.notifyDataSetChanged()
    }*/

    /*fun sortDialog() {
        lateinit var sortMethod: Array<String>
        val builder = AlertDialog.Builder(this)

        builder.setTitle("排序")

        builder.setItems(sortMethod) { _, which ->
            tv_sort_title.text = sortMethod[which]
            when (which) {
                0 -> sortByCupType()
                1 -> sortByDateDescending()
                2 -> sortByDate()
            }

            //Toast.makeText(applicationContext,sortMethod[which],Toast.LENGTH_SHORT).show()
        }

        builder.create().show()

    }*/



    fun sortByCupType(){
        displayList.sortWith(compareBy<Box>{ it.cupType }.thenBy
        {it.date})
        sortType = InStockActivity.SORT_TYPE_CUP
        adapter.notifyDataSetChanged()
    }

    fun sortByStoreDRA(){
        displayList.sortWith(compareBy<Box> { it.storeId }.thenBy
        { it.boxid })
        sortType = InStockActivity.SORT_TYPE_STORE
        adapter.notifyDataSetChanged()
    }

    fun sortByDeliveryDateDescending(){

        displayList.sortWith(compareBy<Box>{ it.date }.thenBy{it.storeId}.thenBy
        {it.boxid})
        displayList.reverse()
        sortType = InStockActivity.SORT_TYPE_DATE_DESCENDIMG
        adapter.notifyDataSetChanged()
    }

    fun sortByDeliveryDate(){
        displayList.sortWith(compareBy<Box>{ it.date }.thenBy{it.storeId}.thenBy
        {it.boxid})
        sortType = InStockActivity.SORT_TYPE_DATE
        adapter.notifyDataSetChanged()
    }

    fun sortByBoxingDateDescending(){
        displayList.sortWith(compareBy<Box>{ it.date }.thenBy
        {it.boxid})
        displayList.reverse()
        sortType = InStockActivity.SORT_TYPE_DATE_DESCENDIMG
        adapter.notifyDataSetChanged()
    }

    fun sortByBoxingDate(){
        displayList.sortWith(compareBy<Box>{ it.date }.thenBy
        {it.boxid})
        sortType = InStockActivity.SORT_TYPE_DATE
        adapter.notifyDataSetChanged()
    }



}