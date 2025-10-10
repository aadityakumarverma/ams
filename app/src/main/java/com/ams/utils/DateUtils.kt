package com.ams.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import com.ams.domain.models.DateItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {


    fun showDatePickerDialogForStringFormat(
        context: Context,
        textView: TextView,
        onDatePicked: (dayOfWeek: String, dayOfMonth: String, month: String, year: String) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, yearF, monthF, dayF ->
                val selectedCal = Calendar.getInstance().apply {
                    set(yearF, monthF, dayF)
                }

                // Format values
                val sdfDayOfWeek = SimpleDateFormat("EEE", Locale.ENGLISH)   // e.g. Fri
                val sdfDayOfMonth = SimpleDateFormat("d", Locale.ENGLISH)    // e.g. 19
                val sdfMonth = SimpleDateFormat("MMM", Locale.ENGLISH)       // e.g. Sep

                val dayOfWeek = sdfDayOfWeek.format(selectedCal.time)
                val dayOfMonth = sdfDayOfMonth.format(selectedCal.time)
                val month = sdfMonth.format(selectedCal.time)
                val year = yearF.toString()

                // Update TextView
                textView.text = "$dayOfMonth $month $year"

                // Return formatted values
                onDatePicked(dayOfWeek, dayOfMonth, month, year)
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }
    fun generateDatesForMonth(calendar: Calendar): List<DateItem> {
        val dateItems = mutableListOf<DateItem>()
        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)
        val dateFormat = SimpleDateFormat("dd", Locale.ENGLISH)
        val monthFormat = SimpleDateFormat("MMM", Locale.ENGLISH)
        for (i in 0 until daysInMonth) {
            val dayOfWeek = dayFormat.format(tempCalendar.time)
            val dayOfMonth = dateFormat.format(tempCalendar.time)
            val month = monthFormat.format(tempCalendar.time)
            val year = tempCalendar.get(Calendar.YEAR).toString()
            dateItems.add(DateItem(dayOfWeek, dayOfMonth, month, year))
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dateItems
    }



    fun getTodayIndex(dates: List<DateItem>): Int {
        val today = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("dd", Locale.ENGLISH)
        val monthFormat = SimpleDateFormat("MMM", Locale.ENGLISH)
        val todayDay = dayFormat.format(today.time)
        val todayMonth = monthFormat.format(today.time)
        return dates.indexOfFirst { it.dayOfMonth == todayDay && it.month == todayMonth }
    }


}