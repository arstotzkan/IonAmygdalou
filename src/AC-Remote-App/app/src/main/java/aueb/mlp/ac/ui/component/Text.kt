package aueb.mlp.ac.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme
import aueb.mlp.ac.ui.theme.ACTypography

@Preview
@Composable
private fun LargeTextPreview() {
    ACRemoteAppTheme {
        LargeText(
            text = "This is some large text"
        )
    }
}

@Preview
@Composable
private fun ErrorLabelPreview() {
    ACRemoteAppTheme {
        ErrorLabel(
            text = "This is an error message"
        )
    }
}

@Composable
fun LargeText(
    text: String,
) {
    Text(
        text = text,
        style = ACTypography.bodyLarge,
    )
}

@Composable
fun MediumText(
    text: String,
) {
    Text(
        text = text,
        style = ACTypography.bodyMedium,
    )
}

@Composable
fun ErrorLabel(
    text: String,
) {
    Text(
        text = text,
        style = ACTypography.labelMedium,
        color = MaterialTheme.colorScheme.error,
    )
}
