package com.ams.views.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.ams.R
import com.ams.databinding.FragmentSplashBinding
import com.ams.utils.SharedPreferencesHelper

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)


        return binding.root
    }
}