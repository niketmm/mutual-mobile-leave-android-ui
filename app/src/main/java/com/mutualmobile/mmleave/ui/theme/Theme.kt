package com.mutualmobile.mmleave.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primaryColorLight,
    primaryVariant = blueTextColorLight,
    secondary = secondaryColorLight,
    surface = backgroundLight,
    background = backgroundLight
)

private val LightColorPalette = lightColors(
    primary = primaryColorLight,
    primaryVariant = blueTextColorLight,
    secondary = secondaryColorLight,
    surface = backgroundLight,
    background = backgroundLight

    /* Other default colors to override
    background = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MMLeaveTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}