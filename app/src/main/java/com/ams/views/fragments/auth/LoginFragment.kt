package com.ams.views.fragments.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentLoginBinding
import com.ams.utils.MyColor
import com.ams.utils.NetworkUtils
import com.ams.utils.SharedPreferencesHelper
import com.ams.utils.UtilsFunctions
import com.ams.utils.UtilsFunctions.setOnClickListeners
import com.ams.utils.UtilsFunctions.setTextAndFocusChangeListener
import com.ams.views.activities.MainActivity
import com.ams.views.activities.MainActivity.Companion.mySystemBars
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = findNavController()

        binding.apply {
            llParent.setPadding(0, mySystemBars.top,0,0)
        }
        binding.composeBackground.setContent {
            AnimatedBackground2() // <-- our composable animation
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            tvForgotPass.setOnClickListeners {
                val nsrsId = etNsrsId.text.toString()
                if (nsrsId.isEmpty()) {
                    etNsrsId.apply {
                        (this.parent.parent as TextInputLayout).error = "NSRS ID is required!"
                    }
                } else {
                    navController?.navigate(R.id.VerifyOtpFragment)
                }
            }
            tvSignUp.setOnClickListeners {

            }

            setTextAndFocusChangeListener(etNsrsId, etPassword)

            cvMainBtn.setOnClickListeners {


                val nsrsId = etNsrsId.text.toString()
                val password = etPassword.text.toString()
//                val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$")


                if (nsrsId.isEmpty()) {
                    etNsrsId.apply {
                        (this.parent.parent as TextInputLayout).error = "NSRS ID is required!"
                    }
                } else if (password.isEmpty()) {
                    etPassword.apply {
                        (this.parent.parent as TextInputLayout).error = "Password is required!"
                    }
                } /*else if (!passwordPattern.matches(password)) {
                    etPassword.apply {
                        (this.parent.parent as TextInputLayout).error = "Min 8 chars required, with upper, lower, number & symbol."
                    }
                }*/ else if (password.length<8) {
                    etPassword.apply {
                        (this.parent.parent as TextInputLayout).error = "Minimum 8 characters required!"
                    }
                } else if (!NetworkUtils.isInternetAvailable(requireContext())) {
                    NetworkUtils.askToEnableInternet(requireContext())
                } else {
//                    authViewModel.login(LoginReq(countryCode = countryCode, phone = phoneNumber))

                    UtilsFunctions.morphToProgress(tvMain, lpbProgress)
                    cvMainBtn.isEnabled = false


                    Handler(Looper.getMainLooper()).postDelayed({
                        UtilsFunctions.morphToTick(ivTick, cardView = cvMainBtn)

                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                ivTick.isVisible = false
                                lpbProgress.isVisible = false
                                tvMain.text = "Log In"
                                cvMainBtn.isEnabled = true
                            }, 1000
                        )
                    }, 2000)

                }


            }

            cvExploreBtn.setOnClickListeners {
                navController?.navigate(R.id.HomeFragment)
            }
        }
    }

    @Composable
    @Preview(showSystemUi = true)
    fun AnimatedBackground2() {
        // Y animation (rise)
        val progressY = remember { Animatable(0f) }

        // Size animation (grow + bounce)
        val progressSize = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            // Animate Y first
            progressY.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }

        LaunchedEffect(Unit) {
            // Animate size with bounce
            progressSize.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            // Gradient background
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(MyColor.CenterBlue, MyColor.OuterBlue),
                    center = Offset(size.width / 2, size.height / 3),
                    radius = size.minDimension / 1.2f
                ),
                size = size
            )

            // Circle size = 3:4 of screen height
            val circleDiameter = size.height * (3f / 4f)
            val circleRadius = circleDiameter / 2f

            // Only 2/3 visible → push circle center downward
            val visibleRatio = 1.5f / 5f
            val hiddenPart = circleDiameter * (1 - visibleRatio)

            // Animate rise
            val startY = size.height + circleRadius
            val endY = size.height - (circleDiameter - hiddenPart)
            val centerY = startY + (endY - startY) * progressY.value

            val centerX = size.width / 2f

            // Animate radius growth
            val animatedRadius = circleRadius * (0.0f + 1.1f * progressSize.value)
            // Here 1.1f means "overshoot 110% size" before bouncing back

            drawCircle(
                color = MyColor.White,
                radius = animatedRadius,
                center = Offset(centerX, centerY)
            )
        }
    }


}