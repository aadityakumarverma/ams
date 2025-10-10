package com.ams.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ams.R
import com.ams.databinding.FragmentNavReportsBinding
import com.ams.utils.SharedPreferencesHelper
import com.ams.views.activities.MainActivity.Companion.mySystemBars
import com.ams.views.adapters.AllReportsAdapter
import com.google.android.material.tabs.TabLayout

class NavReportsFragment : Fragment() {
    lateinit var binding: FragmentNavReportsBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    private var currentPage = 1
    private val limit = 10
    private var totalPage = 1
    private var isLoading = false

    private var selectedTabIndex: Int = 0
    var reportsStatus = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavReportsBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = requireActivity().findNavController(R.id.navHostFragmentContainerView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            setupRecyclerViews()
            setupAllPages()


            if (selectedTabIndex != 0) {
                binding.tabAllReports.getTabAt(selectedTabIndex)?.select()
            } else {
                callApi(0)
            }

            swipeRefreshLayout.setOnRefreshListener {
                currentPage = 1
                // api call
                swipeRefreshLayout.isRefreshing = false
            }

            rvReports.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (!isLoading && currentPage < totalPage) {
                            isLoading = true
                            currentPage++
//                            lpbLoaderBottom.visibility = View.VISIBLE
                            //full scroll
                            rvReports.scrollToPosition((rvReports.layoutManager as LinearLayoutManager).itemCount - 1)

                            // api call
                        }
                    }
                }
            })

        }
    }


    private fun setupAllPages() {
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .build()

        binding.apply {
            tabAllReports.apply {
                removeAllTabs()
                addTab(newTab().setText("Biochemistry"))
                addTab(newTab().setText("Training"))
                addTab(newTab().setText("Nutrition"))
                addTab(newTab().setText("Physiotherapy"))

                // Tab selection listener
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        tab?.position?.let {
                            selectedTabIndex = it
                            callApi(selectedTabIndex)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {}
                    override fun onTabReselected(tab: TabLayout.Tab?) {}
                })

                // Adjust tab spacing and make all tabs equal width
                adjustTabLayoutSpacing(this, spacingDp = 8)
            }
        }
    }


    private fun adjustTabLayoutSpacing(tabLayout: TabLayout, spacingDp: Int = 8) {
        tabLayout.post {
            val tabStrip = tabLayout.getChildAt(0) as? LinearLayout ?: return@post

            var maxWidth = 0
            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)
                tabView.measure(
                    View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED
                )
                maxWidth = maxOf(maxWidth, tabView.measuredWidth)
            }

            val spacingPx = (spacingDp * tabLayout.resources.displayMetrics.density).toInt()

            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)
                val layoutParams = tabView.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = maxWidth
                layoutParams.marginEnd = spacingPx
                tabView.layoutParams = layoutParams
            }

            // Optional: remove extra end margin on the last tab for better centering
            val lastTab = tabStrip.getChildAt(tabStrip.childCount - 1)
            (lastTab.layoutParams as LinearLayout.LayoutParams).marginEnd = 0

            tabLayout.requestLayout()
        }
    }



    private fun callApi(currentStatus: Int) {

        reportsStatus = currentStatus

        currentPage = 1
        // api call

        var arrList = arrayListOf<String>("", "", "", "", "", "")

        binding.apply {
            (rvReports.adapter as? AllReportsAdapter)?.apply {
                clearAll()
                addAll(arrList)
            }
        }
    }


    private fun setupRecyclerViews() {
        binding.apply {
            rvReports.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = AllReportsAdapter(
                    requireContext(), mutableListOf(),
                    object : AllReportsAdapter.OnReportClickListener {
                        override fun onReportClick(reportId: String) {
                            val bundle = bundleOf("KeyReportId" to reportId)

                            navController?.navigate(
                                R.id.ReportsDetailsFragment,
                                bundle
                            )
                        }
                    }
                )
            }
        }
    }


}