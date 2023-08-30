package com.example.screen_lake.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightDefaultColorScheme = lightColorScheme(
    primary = PrimaryLight,
    background = ShapeBackgroundLightColor,
    onBackground = PrimaryDark,
    surface = PrimaryLightButtonBackground,
    secondary = PrimaryLight,
    onSurface = PrimaryDark,
    onSecondary = DisableLightColor,
    secondaryContainer = LightBorderColor,
    primaryContainer = LightImageButtonBg,
    onPrimary = DisableButtonLightColor,
    onError = DisableTextLightColor
)

private val DarkDefaultColorScheme = darkColorScheme(
    primary = PrimaryDark,
    background = ShapeBackgroundDarkColor,
    onBackground = PrimaryLight,
    surface = PrimaryDarkButtonBackground,
    secondary = PrimaryDark,
    onSurface = PrimaryLight,
    onSecondary = DisableDarkColor,
    secondaryContainer = DarkBorderColor,
    primaryContainer = DarkImageButtonBg,
    onPrimary = DisableButtonDarkColor,
    onError = DisableTextDarkColor
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
        colorScheme = colors,
        typography = SiftTypography,
        content = content
    )

}