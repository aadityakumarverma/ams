package com.ams.views.fragments.sessions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentUpperBodyStrengthBinding
import com.ams.utils.SharedPreferencesHelper

class UpperBodyStrengthFragment : Fragment() {
    lateinit var binding: FragmentUpperBodyStrengthBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpperBodyStrengthBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController = findNavController()


        return binding.root
    }
}