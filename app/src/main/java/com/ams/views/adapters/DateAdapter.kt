package com.ams.views.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ams.R
import com.ams.databinding.ItemDateBinding
import com.ams.domain.models.DateItem
import com.ams.utils.UtilsFunctions.setOnClickListeners


class DateAdapter(
    private val context: Context,
    private val dates: MutableList<DateItem>,
    private var selectedPosition: Int,
    private val clickListener: OnDateClickListener
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    interface OnDateClickListener {
        fun onDateClick(dateItem: DateItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dates[position], position)
    }

    override fun getItemCount(): Int = dates.size

    inner class DateViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dateItem: DateItem, position: Int) {
            with(binding){
                dayOfWeekText.text = dateItem.dayOfWeek
                dayOfMonthText.text = dateItem.dayOfMonth

                if (position == selectedPosition) {
                    llItem.setBackgroundResource(R.drawable.ripple_blue_10cornered_bg)
                    dayOfMonthText.setTextColor(ContextCompat.getColor(context, R.color.white))
                    dayOfWeekText.setTextColor(ContextCompat.getColor(context, R.color.bg_gray))
                } else {
                    llItem.setBackgroundColor(Color.TRANSPARENT)
                    dayOfMonthText.setTextColor(ContextCompat.getColor(context, R.color.black))
                    dayOfWeekText.setTextColor(ContextCompat.getColor(context, R.color.app_gray))
                }

                root.setOnClickListeners {
                    clickListener.onDateClick(dateItem)
                    val previousSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    fun selectItem(dateItem: DateItem) {
        val index = dates.indexOf(dateItem)
        if (index != -1) {
            val previousSelectedPosition = selectedPosition
            selectedPosition = index
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
    }

}
