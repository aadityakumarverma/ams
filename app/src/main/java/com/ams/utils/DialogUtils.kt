package com.ams.utils

import android.view.LayoutInflater
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.ams.R
import com.ams.databinding.DialogBouncyBinding

object DialogUtils {

    @Composable
    fun BouncyDialog(onDismiss: () -> Unit) {
        Dialog(onDismissRequest = { onDismiss() }) {
            var visible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                visible = true
            }

            AnimatedVisibility(
                visible = visible,
                enter = scaleIn(
                    initialScale = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                exit = scaleOut(
                    targetScale = 0f,
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.sdp))
                        .padding(0.sdp),
                    contentAlignment = Alignment.Center
                ) {
                    val context = LocalContext.current

                    // Inflate XML layout inside Compose
                    AndroidView(
                        modifier = Modifier.fillMaxWidth(),
                        factory = { inflaterContext ->
                            val binding = DialogBouncyBinding.inflate(
                                LayoutInflater.from(inflaterContext)
                            )

                            // Example: set button click listener
//                            binding.closeButton.setOnClickListener {
//                                onDismiss()
//                            }

                            binding.root
                        }
                    )
                }
            }
        }
    }


    @Composable
    fun BouncyOverlay(
        onDismiss: () -> Unit,
        content: @Composable () -> Unit
    ) {
        var visible by remember { mutableStateOf(false) }

        // trigger animation
        LaunchedEffect(Unit) { visible = true }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000))
                .padding(20.sdp), // optional dim background
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = scaleIn(
                    initialScale = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                exit = scaleOut(
                    targetScale = 0f,
                    animationSpec = tween(300)
                )
            ) {
                Surface(
                    shape = RoundedCornerShape(16.sdp),
                    color = Color.White,
                    modifier = Modifier.wrapContentSize()
                ) {
                    content()
                }
            }
        }
    }
}
