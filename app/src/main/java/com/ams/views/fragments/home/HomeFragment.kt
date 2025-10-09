package com.ams.views.fragments.home

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentHomeBinding
import com.ams.utils.SharedPreferencesHelper
import com.ams.utils.UtilsFunctions.setOnClickListeners
import com.ams.views.activities.MainActivity.Companion.mySystemBars

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController
    lateinit var navHomeController: NavController

    var currentNav = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController =findNavController()


        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)


        // Access the nested navController for home_nav_graph
        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navHomeController = navHostFragment.navController

        binding.apply {
            parentView.setPadding(0, mySystemBars.top,0,0)

            ivNotificationBtn.setOnClickListeners {
                navController.navigate(R.id.NotificationFragment)
            }

            setSelectedNav(0)
            llNavHome.setOnClickListeners {
                setSelectedNav(0)
            }
            llNavMyZone.setOnClickListeners {
                setSelectedNav(1)
            }
            llNavAdd.setOnClickListeners {
                setSelectedNav(2)
            }
            llNavMyDiet.setOnClickListeners {
                setSelectedNav(3)
            }
            llNavReports.setOnClickListeners {
                setSelectedNav(4)
            }

        }

        binding.apply {
            ivProfilePic.setOnClickListeners {
                navController.navigate(R.id.MyProfileFragment)
            }
            llProfile.setOnClickListeners {
                navController.navigate(R.id.MyProfileFragment)
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            parentView.setPadding(0, mySystemBars.top,0,0)
        }


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (currentNav == 0) {
                        Log.d("TAG4784","if condition: true")
                        isEnabled = false // disable this callback to let system handle the back
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                    else {
                        Log.d("TAG4784","else condition: true")
                        navHomeController.popBackStack()
                        setSelectedNav(0)
//                        currentNav = 0
                    }
                }
            })

    }

    fun setSelectedNav(selectedNav: Int) {
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .build()

        binding.apply {
            tvNavHome.isSelected = false
            tvNavMyZone.isSelected = false
            tvNavAdd.isSelected = false
            tvNavMyDiet.isSelected = false
            tvNavReports.isSelected = false

            ivNavHome.isSelected = false
            ivNavMyZone.isSelected = false
            ivNavAdd.isSelected = false
            ivNavMyDiet.isSelected = false
            ivNavReports.isSelected = false

            when (selectedNav) {
                0 -> {
                    tvNavHome.isSelected = true
                    ivNavHome.isSelected = true
                    if (currentNav != 0) {
                        navHomeController.navigate(R.id.NavHomeFragment, null, navOptions)
                        currentNav = 0
                    }
                }
                1 -> {
                    tvNavMyZone.isSelected = true
                    ivNavMyZone.isSelected = true
                    if (currentNav != 1) {
                        navHomeController.navigate(R.id.NavMyZoneFragment, null, navOptions)
                        currentNav = 1
                    }
                }
                2 -> {
                    tvNavAdd.isSelected = true
                    ivNavAdd.isSelected = true
                    if (currentNav != 2) {
                        navHomeController.navigate(R.id.NavAddFragment, null, navOptions)
                        currentNav = 2
                    }
                }
                3 -> {
                    tvNavMyDiet.isSelected = true
                    ivNavMyDiet.isSelected = true
                    if (currentNav != 3) {
                        navHomeController.navigate(R.id.NavMyDietFragment, null, navOptions)
                        currentNav = 3
                    }
                }
                4 -> {
                    tvNavReports.isSelected = true
                    ivNavReports.isSelected = true
                    if (currentNav != 4) {
                        val extras = FragmentNavigatorExtras(ivProfilePic to "profile_pic")
                        navHomeController.navigate(R.id.NavReportsFragment, null, navOptions, extras)
                        currentNav = 4
                    }
                }
            }
        }
    }
}