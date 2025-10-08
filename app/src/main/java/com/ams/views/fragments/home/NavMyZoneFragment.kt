package com.ams.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ams.R
import com.ams.databinding.FragmentNavMyZoneBinding
import com.ams.domain.models.History
import com.ams.utils.SharedPreferencesHelper
import com.ams.views.adapters.CompetitionsHistoryAdapter
import com.ams.views.adapters.UpcomingCompetitionsAdapter

class NavMyZoneFragment : Fragment() {
    lateinit var binding: FragmentNavMyZoneBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavMyZoneBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = findNavController()


        setupRecyclerViews()

        binding.apply {
            (rvUpcomingCompetitions.adapter as UpcomingCompetitionsAdapter).apply {
                clearAll()
                addAll(mutableListOf(R.drawable.png_pattern_1, R.drawable.png_pattern_2, R.drawable.png_pattern_3))
            }


            (rvCompetitionsHistory.adapter as CompetitionsHistoryAdapter).apply {
                clearAll()
                addAll(mutableListOf(
                    History("15", "AUG", "National Championships", R.color.gold),
                    History("20", "AUG", "State Championships", R.color.silver),
                    History("05", "SEP", "District Championships", R.color.bronze),
                    ))
            }
        }

        return binding.root
    }

    private fun setupRecyclerViews() {
        binding.apply {
            rvUpcomingCompetitions.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = UpcomingCompetitionsAdapter(requireContext(), mutableListOf(), object : UpcomingCompetitionsAdapter.OnCompetitionClickListener {
                    override fun onCompetitionClick(item: String) {

                    }
                })
            }

            rvCompetitionsHistory.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = CompetitionsHistoryAdapter(requireContext(), mutableListOf(), object : CompetitionsHistoryAdapter.OnHistoryClickListener {
                    override fun onHistoryClick(item: String) {

                    }
                })
            }


        }
    }


}