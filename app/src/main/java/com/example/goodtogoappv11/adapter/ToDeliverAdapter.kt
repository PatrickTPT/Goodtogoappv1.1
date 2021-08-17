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
import com.example.goodtogoappv11.EditBoxToDeliverActivity
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
        val cupType: TextView? = view.tv_cup_type
        val cupNumber: TextView? = view.tv_cup_number

        val llWholeItem: LinearLayout = view.ll_whole_item
        val lltodeliver: LinearLayout? = view.ll_to_deliver
//2
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
            cancelDelivery.setOnClickListener {
                val editOptionList :Array<String> =arrayOf("送入庫存","解除裝箱")
                val builder = AlertDialog.Builder(context)
                builder.setTitle("取消配送")
                    .setItems(editOptionList) { _, which ->
                        when (which) {
                            0 -> {items[absoluteAdapterPosition].status = BOX_STATUS_BOXED
                                Toast.makeText(context,"# ${items[absoluteAdapterPosition].boxid} 已送回庫存",
                                    Toast.LENGTH_SHORT).show()
                                    notifyDataSetChanged()
                            }
                            1 -> {val builder = AlertDialog.Builder(context)
                                builder.setTitle("解除裝箱")
                                    .setMessage("解除裝箱是無法復原的喔！確定解除？")
                                    .setPositiveButton("確定解除"){
                                            dialog, _->
                                        dialog.dismiss()
                                        items.removeAt(absoluteAdapterPosition)
                                        notifyDataSetChanged()
                                    }
                                    .setNeutralButton("把這箱留著"){
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
                val editOptionList :Array<String> =arrayOf("修改裝箱內容","修改出貨日期或地點")
                val builder = AlertDialog.Builder(context)
                builder.setTitle("修改")
                    .setItems(editOptionList){_,which->
                        when(which){
                            0 -> {
                                val intent = Intent(context, EditBoxToDeliverActivity::class.java)
                                intent.putExtra("boxid","${items[absoluteAdapterPosition].boxid}")
                                intent.putExtra("date","${items[absoluteAdapterPosition].date}")
                                ContextCompat.startActivity(context, intent, null)
                                //TODO
                            }
                            1 -> {

                            }
                        }
                    }
                    .create()
                    .show()

                }
            deliveryArrived.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("注意")
                    .setMessage("確認送達？")
                    .setPositiveButton("確定"){
                            dialog, _->
                        items[absoluteAdapterPosition].status = Constants.BOX_STATUS_TOBESIGNED
                        items[absoluteAdapterPosition].expandable = false
                        notifyDataSetChanged()
                        dialog.dismiss()

                    }
                    .setNeutralButton("取消"){
                            dialog, _->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                }
            signBoxForStore.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("注意")
                    .setMessage("確認代簽收 #${items[absoluteAdapterPosition].boxid}？")
                    .setPositiveButton("確定"){
                            dialog, _->
                        items[absoluteAdapterPosition].status = Constants.BOX_STATUS_TOBEUSED
                        items[absoluteAdapterPosition].expandable = false
                        notifyDataSetChanged()
                        dialog.dismiss()

                    }
                    .setNeutralButton("取消"){
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
                holder.boxId?.text = model.boxid.toString()
                holder.boxTitle?.text =
                    "${ SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(model.date) } 出貨｜${model.storeName}"
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
                    if(model.status == BOX_STATUS_TODELIVER ) View.VISIBLE else View.GONE
                holder.waitToSign.visibility =
                    if(model.status == BOX_STATUS_TOBESIGNED) View.VISIBLE else View.GONE



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