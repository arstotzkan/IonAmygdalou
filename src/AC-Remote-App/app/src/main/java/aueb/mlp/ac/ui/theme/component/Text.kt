package aueb.mlp.ac.ui.theme.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme
import aueb.mlp.ac.ui.theme.ACTypography

@Preview
@Composable
private fun AcTextPreview() {
    ACRemoteAppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextSizeVariation.values().forEach {
                AcText(
                    text = it.name,
                    textSizeVariation = it,
                )
            }
        }
    }
}

enum class TextSizeVariation {
    BODY_SMALL, BODY_MEDIUM, BODY_LARGE, DISPLAY_SMALL, DISPLAY_MEDIUM, DISPLAY_LARGE,
}

@Composable
fun AcText(
    text: String,
    textSizeVariation: TextSizeVariation = TextSizeVariation.BODY_MEDIUM,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        style = with(ACTypography) {
            when(textSizeVariation) {
                TextSizeVariation.BODY_SMALL -> bodySmall.copy(color = color)
                TextSizeVariation.BODY_MEDIUM -> bodyMedium.copy(color = color)
                TextSizeVariation.BODY_LARGE -> bodyLarge.copy(color = color)
                TextSizeVariation.DISPLAY_SMALL -> displaySmall.copy(color = color)
                TextSizeVariation.DISPLAY_MEDIUM -> displayMedium.copy(color = color)
                TextSizeVariation.DISPLAY_LARGE -> displayLarge.copy(color = color)
            }
        }
    )
}
