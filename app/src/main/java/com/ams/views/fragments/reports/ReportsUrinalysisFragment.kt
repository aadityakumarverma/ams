package com.ams.views.fragments.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentReportsUrinalysisBinding
import com.ams.utils.SharedPreferencesHelper

class ReportsUrinalysisFragment : Fragment() {
    lateinit var binding: FragmentReportsUrinalysisBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportsUrinalysisBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController = findNavController()


        return binding.root
    }
}