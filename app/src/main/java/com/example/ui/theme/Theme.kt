package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = DeepAccentBlue,
    onPrimary = Color.White,
    primaryContainer = AccentContainer,
    onPrimaryContainer = OnAccentContainer,
    secondary = NeutralSecondaryText,
    onSecondary = Color.White,
    secondaryContainer = SurfaceVariantLight,
    onSecondaryContainer = NeutralDarkText,
    background = BackgroundLight,
    onBackground = NeutralDarkText,
    surface = SurfaceLight,
    onSurface = NeutralDarkText,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = NeutralSecondaryText,
    outline = DividerDiscreet,
    error = StatusError,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    // Sober theme strictly adhering to specifications (light background, single accent)
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
