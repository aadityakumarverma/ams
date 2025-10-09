package com.ams.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentMyProfileBinding
import com.ams.utils.SharedPreferencesHelper
import com.ams.utils.UtilsFunctions.setOnClickListeners
import com.ams.views.activities.MainActivity.Companion.mySystemBars

class MyProfileFragment : Fragment() {
    lateinit var binding: FragmentMyProfileBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyProfileBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController = findNavController()

        binding.apply {
            ivBackBtn.setOnClickListeners {
                navController.popBackStack()
            }
            llParent.setPadding(0, mySystemBars.top,0,0)
        }


        return binding.root
    }
}