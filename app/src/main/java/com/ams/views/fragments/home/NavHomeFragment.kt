package com.ams.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ams.R
import com.ams.databinding.FragmentNavHomeBinding
import com.ams.utils.SharedPreferencesHelper
import com.ams.views.adapters.UpcomingCompetitionsAdapter

class NavHomeFragment : Fragment() {
    lateinit var binding: FragmentNavHomeBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavHomeBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = requireActivity().findNavController(R.id.navHostFragmentContainerView)

        setupRecyclerViews()

        binding.apply {
            (rvUpcomingCompetitions.adapter as UpcomingCompetitionsAdapter).apply {
                clearAll()
                addAll(mutableListOf(R.drawable.png_pattern_1, R.drawable.png_pattern_2, R.drawable.png_pattern_3))
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
        }
    }


}