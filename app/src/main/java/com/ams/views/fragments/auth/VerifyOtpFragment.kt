package com.ams.views.fragments.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.DialogBouncyBinding
import com.ams.databinding.FragmentVerifyOtpBinding
import com.ams.utils.DialogUtils.BouncyOverlay
import com.ams.utils.MyColor
import com.ams.utils.SharedPreferencesHelper
import com.ams.utils.UtilsFunctions.setOnClickListeners
import com.ams.utils.sdp
import com.ams.views.activities.MainActivity.Companion.mySystemBars

class VerifyOtpFragment : Fragment() {
    private lateinit var binding: FragmentVerifyOtpBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var navController: NavController? = null


    lateinit var editTexts : List<EditText>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyOtpBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        navController = findNavController()

        binding.apply {
            llParent.setPadding(0, mySystemBars.top,0,0)
        }
        binding.composeBackground.setContent {
            AnimatedBackground2() // your animated background

            var showOverlay by remember { mutableStateOf(true) }

            if (showOverlay) {
                BouncyOverlay(
                    onDismiss = { showOverlay = false },
                ) {
                    // Your dialog XML content using AndroidView
                    AndroidView(
                        modifier = Modifier.fillMaxWidth().padding(20.sdp),
                        factory = { inflaterContext ->
                            val dialogBinding = DialogBouncyBinding.inflate(
                                LayoutInflater.from(inflaterContext)
                            )

                            dialogBinding.apply {
                                editTexts = listOf(etOtp1, etOtp2, etOtp3, etOtp4, etOtp5, etOtp6)
                                for (i in editTexts.indices) {
                                    val currentEditText = editTexts[i]
                                    val nextEditText = if (i < editTexts.lastIndex) editTexts[i + 1] else null
                                    val previousEditText = if (i > 0) editTexts[i - 1] else null

                                    // Add TextWatcher
                                    currentEditText.addTextChangedListener(object : TextWatcher {
                                        override fun afterTextChanged(editable: Editable) {
                                            if (editable.length == 1 && nextEditText != null) {
                                                nextEditText.requestFocus()
                                            }
                                        }

                                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                            // Reset background when typing
                                            currentEditText.background = ContextCompat.getDrawable(requireContext(), R.drawable.et_bg_otp)

                                            currentEditText.isSelected = currentEditText.text.toString().trim().isNotEmpty()
                                        }
                                    })


                                    // Add OnKeyListener
                                    currentEditText.setOnKeyListener { v, keyCode, event ->
                                        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentEditText.text.isEmpty() && previousEditText != null) {
                                            previousEditText.text = null
                                            previousEditText.requestFocus()
                                            true
                                        } else {
                                            false
                                        }
                                    }
                                }


                                tvResendOtp.setOnClickListeners {
                                    navController?.popBackStack()
                                }
                            }

                            dialogBinding.root
                        }
                    )
                }
            }
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {


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