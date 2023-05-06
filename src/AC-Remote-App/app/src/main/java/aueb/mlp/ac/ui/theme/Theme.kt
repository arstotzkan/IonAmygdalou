package aueb.mlp.ac.ui.theme

import android.app.Activity
import androidx.compose.foundation.checkScrollableContainerConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    surface = Color.White,
    onSurface = Color.Black,

    surfaceVariant = Color(0xFFC5C7F1), // TODO("put as constant into Color.kt")
    onSurfaceVariant = Color(0x88000000), // TODO("put as constant into Color.kt")

    error = Color.Red,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    */
)

@Composable
fun ACRemoteAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = ACShapes,
        typography = ACTypography,
        content = content,
    )
}