package com.ams.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ams.R
import com.ams.databinding.LayoutDietBinding
import com.ams.utils.UtilsFunctions.animateItem
import com.ams.utils.UtilsFunctions.setOnClickListeners

class AllDietAdapter(val context: Context, private val arrListItems: MutableList<String>,
                     private val click: OnDietClickListener
) : RecyclerView.Adapter<AllDietAdapter.ViewHolder>(){

    interface OnDietClickListener {
        fun onDietClick(dietId: String)
    }

    class ViewHolder(val binding: LayoutDietBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = LayoutDietBinding.inflate(inflater, parent, false)
        R.layout.layout_diet
//        val lp = binding.root.layoutParams
//        lp.width = (parent.measuredWidth / 1.2).toInt()
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrListItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        arrListItems[position].apply {

            with(holder.binding) {

                root.setOnClickListeners {

                }
            }
        }
        animateItem(holder.itemView, position)
    }

    fun addAll(newData: MutableList<String>) {
        newData?.let {
            arrListItems.addAll(it)
            notifyDataSetChanged()
        }
    }


    fun clearAll() {
        arrListItems.clear()
        notifyDataSetChanged()
    }

}