package aueb.mlp.ac.ui.theme.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleAlertDialog(
    onDismiss: () -> Unit = {},
    onAccept: () -> Unit = {},
    onReject: () -> Unit = {},
    title: String,
    text: String
) {
    AlertDialog( //TODO: button letters should be larger
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.6f),
        onDismissRequest = onDismiss,
        confirmButton = {
            LargePlainTextButton(
                onClick = onAccept,
                enabled = true,
                text="OK",
                modifier = Modifier
                    .fillMaxWidth(0.3f)
            )
        },
        dismissButton = {
            LargePlainTextButton(
                onClick = onReject,
                enabled = true,
                text="Cancel",
                modifier = Modifier
                    .fillMaxWidth(0.3f)
            )
        },
        title = { LargeText(text = title) },
        text = { LargeText(text = text) }
    )
}
