package com.ams.views.fragments.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentReportsDetailsBinding
import com.ams.utils.SharedPreferencesHelper
import com.ams.views.activities.MainActivity.Companion.mySystemBars

class ReportsDetailsFragment : Fragment() {
    lateinit var binding: FragmentReportsDetailsBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportsDetailsBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController = findNavController()

        binding.parentView.setPadding(0, mySystemBars.top,0,0)

        binding.apply {

        }

        return binding.root
    }
}