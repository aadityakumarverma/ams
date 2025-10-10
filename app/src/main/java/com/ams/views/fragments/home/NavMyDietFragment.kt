package com.ams.views.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ams.R
import com.ams.databinding.FragmentNavMyDietBinding
import com.ams.domain.models.DateItem
import com.ams.utils.DateUtils.generateDatesForMonth
import com.ams.utils.DateUtils.getTodayIndex
import com.ams.utils.DateUtils.showDatePickerDialogForStringFormat
import com.ams.utils.SharedPreferencesHelper
import com.ams.utils.UtilsFunctions.getCurrentFormattedDate
import com.ams.utils.UtilsFunctions.setOnClickListeners
import com.ams.utils.UtilsFunctions.showLog
import com.ams.views.adapters.AllDietAdapter
import com.ams.views.adapters.DateAdapter
import java.util.Calendar

class NavMyDietFragment : Fragment() {
    lateinit var binding: FragmentNavMyDietBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    private val calendar = Calendar.getInstance()

    private lateinit var initialDates: List<DateItem>

    private lateinit var dateAdapter: DateAdapter

    private var currentPage = 1
    private val limit = 10
    private var totalPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavMyDietBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = requireActivity().findNavController(R.id.navHostFragmentContainerView)

        initialDates = generateDatesForMonth(calendar)

        setupRecyclerViews()
        binding.apply {
            tvSelectedDate.text = getCurrentFormattedDate()
            tvSelectedDate.setOnClickListeners {
                showDatePickerDialogForStringFormat(
                    requireContext(),
                    tvSelectedDate
                ) { dayOfWeek, dayOfMonth, month, year ->
                    val dateItem = DateItem(dayOfWeek, dayOfMonth, month, year)
                    showLog("TAG12773", "dateItem: $dateItem")

                    val index = initialDates.indexOf(dateItem)
                    if (index != -1) {
                        dateAdapter.selectItem(dateItem)
                        rvDayDate.scrollToPosition(index)
                    }
                }
            }


            swipeRefreshLayout.setOnRefreshListener {
                currentPage = 1
                // api call
                swipeRefreshLayout.isRefreshing = false
            }

            (rvDiet.adapter as? AllDietAdapter)?.apply {
                clearAll()
                addAll(arrayListOf("","","","","","",""))
            }
        }


        return binding.root
    }

    private fun setupRecyclerViews() {
        binding.apply {
            rvDayDate.apply {
                val todayIndex = getTodayIndex(initialDates)

                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                dateAdapter = DateAdapter(
                    requireContext(),
                    initialDates.toMutableList(),
                    todayIndex,
                    object : DateAdapter.OnDateClickListener {
                        override fun onDateClick(dateItem: DateItem) {
                            tvSelectedDate.text =
                                "${dateItem.dayOfMonth} ${dateItem.month} ${dateItem.year}"
                        }
                    })

                adapter = dateAdapter
                scrollToPosition(todayIndex)
            }
            rvDiet.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = AllDietAdapter(
                    requireContext(), mutableListOf(),
                    object : AllDietAdapter.OnDietClickListener {
                        override fun onDietClick(reportId: String) {
                            val bundle = bundleOf("KeyDietId" to reportId)


                        }
                    }
                )
            }

        }
    }
}