package com.ams.views.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentNavAddBinding
import com.ams.utils.SharedPreferencesHelper
import com.ams.utils.UtilsFunctions.setOnClickListeners

class NavAddFragment : Fragment() {
    lateinit var binding: FragmentNavAddBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavAddBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = requireActivity().findNavController(R.id.navHostFragmentContainerView)

        binding.apply {

            llBiochemicalReport.setOnClickListeners {
                navController?.navigate(R.id.ReportsBiochemicalFragment)
            }

            llHematologyReport.setOnClickListeners {
                navController?.navigate(R.id.ReportsHematologyFragment)
            }
            llUrinalysisReport.setOnClickListeners {
                navController?.navigate(R.id.ReportsUrinalysisFragment)
            }
        }

        return binding.root
    }
}