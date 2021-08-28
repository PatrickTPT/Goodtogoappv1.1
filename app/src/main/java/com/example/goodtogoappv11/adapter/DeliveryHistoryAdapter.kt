package com.example.goodtogoappv11.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.model.Box
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.BOX_STATUS_TOBEUSED
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import kotlinx.android.synthetic.main.item_tag_layout.view.*
import kotlinx.android.synthetic.main.item_together_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class DeliveryHistoryAdapter(
    private val context: Context,
    private val items: ArrayList<Box>,
    private val listener: OnAClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val boxId: TextView? = view.tv_box_id
        val boxTitle: TextView? = view.tv_box_title
        val cupType: TextView? = view.tv_cup_type1
        val cupNumber: TextView? = view.tv_cup_number1

        val llWholeItem: LinearLayout = view.ll_whole_item
        val lldeliverrecord: LinearLayout? = view.ll_delivery_history

        val returnToStock: TextView = view.tv_return_to_stock

        val llboxinfo: LinearLayout? = view.ll_box_info
        val llparent: LinearLayout? = view.shown_box_info_layout

        //3
        init {
            view.setOnClickListener(this)

            returnToStock.setOnClickListener{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("退回庫存")
                    .setMessage("# ${items[absoluteAdapterPosition].boxid} 將會退回庫存，確定嗎?")
                    .setPositiveButton("確定"){
                            dialog, _->
                        items[absoluteAdapterPosition].status = Constants.BOX_STATUS_BOXED
                        items[absoluteAdapterPosition].expandable = false
                        notifyDataSetChanged()
                        dialog.dismiss()

                    }
                    .setNeutralButton("不要"){
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
                    "${ SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(model.date) } 簽收｜${model.storeName}"
                holder.cupType?.text = model.cupType.toString()
                holder.cupNumber?.text = model.cupNumber.toString()
                holder.lldeliverrecord?.visibility = View.VISIBLE

                holder.llboxinfo?.visibility = if (isExpandable) View.VISIBLE else View.GONE
                holder.llparent?.setOnClickListener{
                    model.expandable = !model.expandable
                    notifyItemChanged(position)
                }
                holder.llWholeItem.visibility =
                    if(model.status == BOX_STATUS_TOBEUSED) View.VISIBLE else View.GONE
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