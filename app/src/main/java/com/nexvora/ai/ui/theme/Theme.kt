package com.nexvora.ai.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.nexvora.ai.domain.model.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = NexVoraDarkPrimary,
    onPrimary = NexVoraDarkOnPrimary,
    primaryContainer = NexVoraDarkSurfaceVariant,
    onPrimaryContainer = NexVoraDarkPrimary,
    secondary = NexVoraDarkSecondary,
    onSecondary = NexVoraDarkOnSecondary,
    secondaryContainer = NexVoraDarkSurfaceVariant,
    onSecondaryContainer = NexVoraDarkSecondary,
    tertiary = NexVoraDarkTertiary,
    onTertiary = NexVoraDarkOnTertiary,
    background = NexVoraDarkBackground,
    onBackground = NexVoraDarkOnSurface,
    surface = NexVoraDarkSurface,
    onSurface = NexVoraDarkOnSurface,
    surfaceVariant = NexVoraDarkSurfaceVariant,
    onSurfaceVariant = NexVoraDarkOnSurfaceVariant,
    surfaceContainer = NexVoraDarkSurfaceContainer,
    outline = NexVoraDarkOutline,
    outlineVariant = NexVoraDarkOutlineVariant
)

private val LightColorScheme = lightColorScheme(
    primary = NexVoraLightPrimary,
    onPrimary = NexVoraLightOnPrimary,
    primaryContainer = NexVoraLightSurfaceVariant,
    onPrimaryContainer = NexVoraLightPrimary,
    secondary = NexVoraLightSecondary,
    onSecondary = NexVoraLightOnSecondary,
    secondaryContainer = NexVoraLightSurfaceVariant,
    onSecondaryContainer = NexVoraLightSecondary,
    tertiary = NexVoraLightTertiary,
    onTertiary = NexVoraLightOnTertiary,
    background = NexVoraLightBackground,
    onBackground = NexVoraLightOnSurface,
    surface = NexVoraLightSurface,
    onSurface = NexVoraLightOnSurface,
    surfaceVariant = NexVoraLightSurfaceVariant,
    onSurfaceVariant = NexVoraLightOnSurfaceVariant,
    surfaceContainer = NexVoraLightSurfaceContainer,
    outline = NexVoraLightOutline,
    outlineVariant = NexVoraLightOutlineVariant
)

@Composable
fun NexVoraTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
