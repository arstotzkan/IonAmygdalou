package aueb.mlp.ac.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import aueb.mlp.ac.R
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme
import aueb.mlp.ac.ui.theme.ACShapes

@Preview
@Composable
private fun ButtonWithTextPreview() {
    ACRemoteAppTheme {
        ButtonWithText(
            text = "Button Text",
            onClick = {  },
        )
    }
}

@Preview
@Composable
private fun ButtonWithIconPreview() {
    ACRemoteAppTheme {
        ButtonWithIcon(
            id = R.drawable.ic_placeholder,
            alt = "image alt text",
            onClick = { },
        )
    }
}

@Composable
fun ButtonWithText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = ACShapes.medium,
        border = null,
        contentPadding = PaddingValues(8.dp),
    ) {
        LargeText(text = text)
    }
}

@Composable
fun ButtonWithMediumText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = ACShapes.medium,
        border = null,
        contentPadding = PaddingValues(8.dp),
    ) {
        MediumText(text = text)
    }
}
@Composable
fun ButtonWithIcon(
    @DrawableRes id: Int,
    alt: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    IconButton(
        onClick = { }, // Icon consumes the onClick event
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        Icon(
            id = id,
            alt = alt,
            onClick = onClick,
        )
    }
}
