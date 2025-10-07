package com.ams.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Extension for Int
val Int.sdp: Dp
    @Composable
    get() {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        // Base width for scaling (like in sdp library)
        val baseWidth = 360f
        val scale = screenWidth / baseWidth
        return (this * scale).dp
    }

// Extension for Float
val Float.sdp: Dp
    @Composable
    get() {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val baseWidth = 360f
        val scale = screenWidth / baseWidth
        return (this * scale).dp
    }
