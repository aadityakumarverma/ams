package com.ams.views.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.ams.databinding.FragmentLoginBinding
import com.ams.utils.MyColor
import com.ams.utils.SharedPreferencesHelper
import kotlinx.coroutines.delay

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.composeBackground.setContent {
            AnimatedBackground2() // <-- our composable animation
        }

        return binding.root
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
            val visibleRatio = 2f / 5f
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