package com.ams.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentNavMyDietBinding
import com.ams.utils.SharedPreferencesHelper

class NavMyDietFragment : Fragment() {
    lateinit var binding: FragmentNavMyDietBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavMyDietBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = findNavController()

        binding.apply {

        }

        return binding.root
    }
}