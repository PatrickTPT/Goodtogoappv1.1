package com.example.goodtogoappv11.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.EditBoxContentToDeliverActivity
import com.example.goodtogoappv11.EditBoxDateDestinationToDeliverActivity
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.model.Box
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_BOXED
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_TOBESIGNED
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_TODELIVER
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import kotlinx.android.synthetic.main.item_tag_layout.view.*
import kotlinx.android.synthetic.main.item_together_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class ToDeliverAdapter(
    private val context: Context,
    private val items: ArrayList<Box>,
    private val listener: OnAClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//1


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener{
        val boxId: TextView? = view.tv_box_id
        val boxTitle: TextView? = view.tv_box_title
        val cupType: TextView? = view.tv_cup_type1
        val cupType2: TextView? = view.tv_cup_type2
        val cupNumber: TextView? = view.tv_cup_number1
        val cupNumber2: TextView? = view.tv_cup_number2

        val llWholeItem: LinearLayout = view.ll_whole_item
        val lltodeliver: LinearLayout? = view.ll_to_deliver
//2
        val cancelArrival: TextView = view.tv_cancel_arrival
        val cancelDelivery: TextView = view.tv_cancel_delivery
        val editDelivery: TextView = view.tv_edit_delivery
        val deliveryArrived: TextView = view.tv_delivery_arrived
        val signBoxForStore: TextView = view.tv_sign_box_for_store

        val llboxinfo: LinearLayout? = view.ll_box_info
        val llparent: LinearLayout? = view.shown_box_info_layout

        val deliverToday: TextView = view.tv_delivery_today
        val waitToSign: TextView = view.tv_wait_to_sign


        //3
        init {
            view.setOnClickListener(this)

            cancelArrival.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("??????")
                    .setMessage("??????????????????")
                    .setPositiveButton("??????"){
                            dialog, _->
                        items[absoluteAdapterPosition].status = BOX_STATUS_TODELIVER
                        items[absoluteAdapterPosition].expandable = false
                        notifyDataSetChanged()
                        dialog.dismiss()

                    }
                    .setNeutralButton("??????"){
                            dialog, _->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }

            cancelDelivery.setOnClickListener {
                val editOptionList :Array<String> =arrayOf("????????????","????????????")
                val builder = AlertDialog.Builder(context)
                builder.setTitle("????????????")
                    .setItems(editOptionList) { _, which ->
                        when (which) {
                            0 -> {items[absoluteAdapterPosition].status = BOX_STATUS_BOXED
                                Toast.makeText(context,"# ${items[absoluteAdapterPosition].boxid} ???????????????",
                                    Toast.LENGTH_SHORT).show()
                                items[absoluteAdapterPosition].expandable = false
                                notifyDataSetChanged()
                            }
                            1 -> {val builder = AlertDialog.Builder(context)
                                builder.setTitle("????????????")
                                    .setMessage("???????????????????????????????????????????????????")
                                    .setPositiveButton("????????????"){
                                            dialog, _->
                                        dialog.dismiss()
                                        items.removeAt(absoluteAdapterPosition)
                                        notifyDataSetChanged()
                                    }
                                    .setNeutralButton("???????????????"){
                                            dialog, _->
                                        dialog.dismiss()
                                    }
                                    .create()
                                    .show()
                            }
                        }
                    }
                    .create()
                    .show()
                }
            editDelivery.setOnClickListener {
                val editOptionList :Array<String> =arrayOf("??????????????????","???????????????????????????")
                val builder = AlertDialog.Builder(context)
                builder.setTitle("??????")
                    .setItems(editOptionList){_,which->
                        when(which){
                            0 -> {
                                val intent = Intent(context, EditBoxContentToDeliverActivity::class.java)
                                intent.putExtra("boxid","${items[absoluteAdapterPosition].boxid}")
                                intent.putExtra("date","${items[absoluteAdapterPosition].date}")
                                ContextCompat.startActivity(context, intent, null)
                                items[absoluteAdapterPosition].expandable = false
                                //TODO
                            }
                            1 -> {
                                val intent = Intent(context, EditBoxDateDestinationToDeliverActivity::class.java)
                                intent.putExtra("boxid","${items[absoluteAdapterPosition].boxid}")
                                intent.putExtra("date","${items[absoluteAdapterPosition].date}")
                                ContextCompat.startActivity(context, intent, null)
                                items[absoluteAdapterPosition].expandable = false
                            }
                        }
                    }
                    .create()
                    .show()

                }
            deliveryArrived.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("??????")
                    .setMessage("???????????????")
                    .setPositiveButton("??????"){
                            dialog, _->
                        items[absoluteAdapterPosition].status = BOX_STATUS_TOBESIGNED
                        notifyDataSetChanged()
                        dialog.dismiss()

                    }
                    .setNeutralButton("??????"){
                            dialog, _->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                }
            signBoxForStore.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("??????")
                    .setMessage("??????????????? #${items[absoluteAdapterPosition].boxid}???")
                    .setPositiveButton("??????"){
                            dialog, _->
                        items[absoluteAdapterPosition].status = Constants.BOX_STATUS_TOBEUSED
                        items[absoluteAdapterPosition].expandable = false
                        notifyDataSetChanged()
                        dialog.dismiss()

                    }
                    .setNeutralButton("??????"){
                            dialog, _->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                }

        }

        //4
        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onAClick(position)
            }
        }
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val label: TextView? = view.tv_tagName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE){
            return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_together_layout, parent, false))
        } else  {
            return TagViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tag_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = items[position]
        val isExpandable : Boolean = items[position].expandable

        when (holder) {
            is ViewHolder -> {

                //val oldsdf = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy" ,Locale.TAIWAN)
                //val deliveryDate = oldsdf.parse(model.date.toString())
                //val newsdf = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN)
                //val today = newsdf.parse(Date().toString())



                holder.boxId?.text = model.boxid.toString()
                holder.boxTitle?.text =
                    "${ SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(model.date) } ?????????${model.storeName}"
                holder.cupType?.text = model.cupType.toString()
                holder.cupNumber?.text = model.cupNumber.toString()
                holder.lltodeliver?.visibility = View.VISIBLE

                holder.llWholeItem.visibility =
                    if(model.status == BOX_STATUS_TODELIVER ||
                            model.status == BOX_STATUS_TOBESIGNED) View.VISIBLE else View.GONE
                holder.llboxinfo?.visibility = if (isExpandable) View.VISIBLE else View.GONE
                holder.llparent?.setOnClickListener{
                    model.expandable = !model.expandable
                    notifyItemChanged(position)
                }



                holder.deliveryArrived.visibility =
                    if(model.status == BOX_STATUS_TODELIVER) View.VISIBLE else View.GONE
                holder.signBoxForStore.visibility =
                    if(model.status == BOX_STATUS_TOBESIGNED) View.VISIBLE else View.GONE
                holder.deliverToday.visibility =
                    if(model.status == BOX_STATUS_TODELIVER  ) View.VISIBLE else View.GONE
                holder.waitToSign.visibility =
                    if(model.status == BOX_STATUS_TOBESIGNED) View.VISIBLE else View.GONE
                holder.cancelArrival.visibility =
                    if(model.status == BOX_STATUS_TOBESIGNED) View.VISIBLE else View.GONE
                holder.cancelDelivery.visibility =
                    if(model.status == BOX_STATUS_TODELIVER ) View.VISIBLE else View.GONE


            }
            is TagViewHolder -> {
                holder.label?.text = model.date.toString()

            }

        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

}