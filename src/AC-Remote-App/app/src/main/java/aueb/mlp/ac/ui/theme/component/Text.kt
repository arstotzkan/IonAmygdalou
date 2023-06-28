package aueb.mlp.ac.ui.theme.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
