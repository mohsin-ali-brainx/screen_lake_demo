package com.example.screen_lake.ui.theme

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@VisibleForTesting
private val LightDefaultColorScheme = lightColors(
    primary = PrimaryLight,
    background = ShapeBackgroundLightColor,
    onBackground = PrimaryDark,
    surface = PrimaryLightButtonBackground,
    secondary = PrimaryLight,
    onSurface = PrimaryDark,
    onSecondary = DisableLightColor,
    secondaryVariant = LightBorderColor,
    primaryVariant = LightImageButtonBg
)

@VisibleForTesting
private val DarkDefaultColorScheme = darkColors(
    primary = PrimaryDark,
    background = ShapeBackgroundDarkColor,
    onBackground = PrimaryLight,
    surface = PrimaryDarkButtonBackground,
    secondary = PrimaryDark,
    onSurface = PrimaryLight,
    onSecondary = DisableDarkColor,
    secondaryVariant = DarkBorderColor,
    primaryVariant = DarkImageButtonBg
)

@Composable
fun ScreenLakeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkDefaultColorScheme
    } else {
        LightDefaultColorScheme
    }

    MaterialTheme(
        colors = colors,
        typography = ScreenLakeTypography,
        shapes = Shapes,
        content = content
    )
}