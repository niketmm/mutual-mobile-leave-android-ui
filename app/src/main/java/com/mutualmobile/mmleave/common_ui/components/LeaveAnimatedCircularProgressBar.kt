package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.mmleave.ui.theme.secondaryTextColorDark

@Composable
fun LeaveAnimatedCircularProgressBar(
    fontSize: TextUnit = 25.sp,
    radius: Dp = 50.dp,
    animationDelay: Int = 0,
    animationDuration: Int = 1000,
    stokeWidth: Dp = 8.dp,
    color: Color = secondaryTextColorDark,
    percentage: Float,
    totalValue: Int
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0F,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    // Box Which holds the Animation and TV
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(radius * 2)) {
        Canvas(modifier = Modifier.size(radius * 2), onDraw = {
            // Method Provided in the DrawScope by Canvas
            drawArc(
                color = color,
                startAngle = 30f,
                360 * currentPercentage.value,
                false,
                style = Stroke(stokeWidth.toPx(), cap = StrokeCap.Round)
            )
        })
        Text(
            text = (currentPercentage.value * totalValue).toInt().toString(),
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}