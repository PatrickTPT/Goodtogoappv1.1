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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.DeliveryDetailActivity
import com.example.goodtogoappv11.EditBoxInStockActivity
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.model.Box
import com.example.goodtogoappv11.model.Constants
import kotlinx.android.synthetic.main.item_tag_layout.view.*
import kotlinx.android.synthetic.main.item_together_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class InStockAdapter (
    private val context: Context,
    private val items: ArrayList<Box>,
    private val listener: OnAClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


//6
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val boxId: TextView? = view.tv_box_id
        val boxTitle: TextView? = view.tv_box_title
        val cupType: TextView? = view.tv_cup_type1
        val cupType2: TextView? = view.tv_cup_type2
        val cupNumber: TextView? = view.tv_cup_number1
        val cupNumber2: TextView? = view.tv_cup_number2

        val llWholeItem: LinearLayout = view.ll_whole_item
        val llinstock: LinearLayout? = view.ll_in_stock

        val deleteBox: TextView = view.tv_dismiss_box
        val editBoxContent: TextView = view.tv_edit_box_content
        val deliverBox: TextView = view.tv_deliver_box
    //2
        val llboxinfo: LinearLayout? = view.ll_box_info
        val llparent: LinearLayout? = view.shown_box_info_layout


        //3
        init {
            view.setOnClickListener(this)

            deleteBox.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                    builder.setTitle("????????????")
                        .setMessage("???????????????????????????????????????????????????")
                        .setPositiveButton("????????????"){
                            dialog, _->
                            dialog.dismiss()
                            items.removeAt(absoluteAdapterPosition)
                            Toast.makeText(context,"??????????????????",Toast.LENGTH_SHORT).show()
                            notifyDataSetChanged()
                        }
                        .setNeutralButton("???????????????"){
                            dialog, _->
                            dialog.dismiss()
                        }
                        .create()
                        .show()

            }
            editBoxContent.setOnClickListener {
                //Toast.makeText(context,"Edit Clicked!",Toast.LENGTH_SHORT).show()
                val editOptionList :Array<String> =arrayOf("??????????????????")
                val builder = AlertDialog.Builder(context)
                    builder.setTitle("??????")
                        .setItems(editOptionList){_,which->
                            when(which){
                                0 -> {
                                    val intent = Intent(context,EditBoxInStockActivity::class.java)
                                    intent.putExtra("boxid","${items[absoluteAdapterPosition].boxid}")
                                    intent.putExtra("date","The boxing date")
                                    items[absoluteAdapterPosition].expandable = false

                                    //TODO: put Array of container List in the Extra( using serializable)
                                    ContextCompat.startActivity(context,intent,null)
                                }
                            }
                        }
                        .create()
                        .show()

            }
            deliverBox.setOnClickListener {

                val intent = Intent(context,DeliveryDetailActivity::class.java)
                intent.putExtra("boxid","${items[absoluteAdapterPosition].boxid}")
                intent.putExtra("position",absoluteAdapterPosition)
                items[absoluteAdapterPosition].expandable = false

                //Toast.makeText(context,"position: $absoluteAdapterPosition",Toast.LENGTH_SHORT).show()
                startActivity(context,intent,null)
                notifyDataSetChanged()
            }
        }

        //4
        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onAClick(position)
            }
        }
        //var boxIDTXT: TextView = view.findViewById(R.id.tv_box_id)
        //var cupTypeTXT: TextView = view.findViewById(R.id.tv_cup_type)
        //var historyTXT: TextView = itemView.findViewById(R.id.history)
        //var explodeBtn: TextView = view.findViewById(R.id.tv_dismiss_box)
        //var shownLayout: LinearLayout = view.findViewById(R.id.shown_box_info_layout)
        //var hiddenLayout: LinearLayout = view.findViewById(R.id.hidden_box_info_layout)
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val label: TextView? = view.tv_tagName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == Constants.VIEW_TYPE_ONE){
            return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_together_layout, parent, false))
        } else  {
            return TagViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tag_layout, parent, false))
        }


    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = items[position]
        //
        val isExpandable : Boolean = items[position].expandable



        when (holder) {
            is ViewHolder -> {
                holder.boxId?.text = model.boxid.toString()
                holder.boxTitle?.text =
                    "${ SimpleDateFormat("yyyy-MM-dd").format(model.date) } ??????"
                holder.cupType?.text = model.cupType.toString()
                holder.cupNumber?.text = model.cupNumber.toString()

                holder.llWholeItem.visibility =
                    if(model.status == Constants.BOX_STATUS_BOXED) View.VISIBLE else View.GONE
                holder.llinstock?.visibility = View.VISIBLE
        //5
                holder.llboxinfo?.visibility = if (isExpandable) View.VISIBLE else View.GONE

                holder.llparent?.setOnClickListener{
                    model.expandable = !model.expandable
                    notifyItemChanged(position)
                }


            }
            is TagViewHolder -> {
                //TODO: try to add a tagtype in somewhere
                val tagType : type? = null
                if(tagType == type.DATE) {
                    holder.label?.text =
                        SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(model.date).toString()
                } else {
                    holder.label?.text =
                        model.cupType.toString()
                }

            }
        }




        /*
        // ????????????????????????
        holder.constraintLayout.setOnClickListener {
        val versio = dataset[position]
        versio.expandable = !versio.expandable
        notifyItemChanged(position)
        }*/

        // ??????AlertDialog?????????1
        //holder.explodeBtn.setOnClickListener {
            /*val builder = AlertDialog.builder()

            builder.setTitle("Notice")
            builder.setMessage("Are you sure?")
            builder.setPositiveButton("????????????", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            builder.setNegativeButton("???????????????", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            builder.show()
        *///}
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }


    enum class type {
        DATE, CUPTYPE
    }

    }



