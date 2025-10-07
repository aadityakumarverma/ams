package com.ams.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentNavReportsBinding
import com.ams.utils.SharedPreferencesHelper

class NavReportsFragment : Fragment() {
    lateinit var binding: FragmentNavReportsBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavReportsBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = findNavController()

        binding.apply {

        }

        return binding.root
    }
}