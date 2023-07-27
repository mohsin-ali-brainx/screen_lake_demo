package com.example.screenlake.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.screenlake.ui.theme.utils.BackgroundTheme
import com.example.screenlake.ui.theme.utils.GradientColors
import com.example.screenlake.ui.theme.utils.LocalBackgroundTheme
import com.example.screenlake.ui.theme.utils.LocalGradientColors
import com.example.screenlake.ui.theme.utils.LocalTintTheme
import com.example.screenlake.ui.theme.utils.TintTheme

@VisibleForTesting
private val LightDefaultColorScheme = lightColorScheme(
    primary = WhitePrimary,
    onPrimary = BlackPrimary,
    onErrorContainer = WhitePrimary,
    background = WhitePrimary,
    onBackground = BlackPrimary,
    surface = WhitePrimary,
    onSurface = BlackPrimary,
    outline = WhitePrimary,
)

@VisibleForTesting
private val DarkDefaultColorScheme = lightColorScheme(
    primary = BlackPrimary,
    onPrimary = WhitePrimary,
    onErrorContainer = BlackPrimary,
    background = BlackPrimary,
    onBackground = WhitePrimary,
    surface = BlackPrimary,
    onSurface = WhitePrimary,
    outline = BlackPrimary,
)

@VisibleForTesting
private val AndroidLightColorScheme = lightColorScheme(
    primary = WhitePrimary,
    onPrimary = BlackPrimary,
    onErrorContainer = WhitePrimary,
    background = WhitePrimary,
    onBackground = BlackPrimary,
    surface = WhitePrimary,
    onSurface = BlackPrimary,
    outline = WhitePrimary,
)

@VisibleForTesting
private val AndroidDarkColorScheme = lightColorScheme(
    primary = BlackPrimary,
    onPrimary = WhitePrimary,
    onErrorContainer = BlackPrimary,
    background = BlackPrimary,
    onBackground = WhitePrimary,
    surface = BlackPrimary,
    onSurface = WhitePrimary,
    outline = BlackPrimary,
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/**
 * Light Android gradient colors
 */
val LightAndroidGradientColors = GradientColors(container = WhitePrimary)

/**
 * Dark Android gradient colors
 */
val DarkAndroidGradientColors = GradientColors(container = BlackPrimary)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = WhitePrimary)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = BlackPrimary)

@Composable
fun ScreenLakeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = when {
        androidTheme -> if (darkTheme) AndroidDarkColorScheme else AndroidLightColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }
    // Gradient colors
    val emptyGradientColors = GradientColors(container = colorScheme.surfaceColorAtElevation(2.dp))
    val defaultGradientColors = GradientColors(
        top = colorScheme.inverseOnSurface,
        bottom = colorScheme.primaryContainer,
        container = colorScheme.surface,
    )
    val gradientColors = when {
        androidTheme -> if (darkTheme) DarkAndroidGradientColors else LightAndroidGradientColors
        !disableDynamicTheming && supportsDynamicTheming() -> emptyGradientColors
        else -> defaultGradientColors
    }
    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )
    val backgroundTheme = when {
        androidTheme -> if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
        else -> defaultBackgroundTheme
    }
    val tintTheme = when {
        androidTheme -> TintTheme()
        !disableDynamicTheming && supportsDynamicTheming() -> TintTheme(colorScheme.primary)
        else -> TintTheme()
    }
    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = ScreenLakeTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S