package aueb.mlp.ac.ui.theme.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import aueb.mlp.ac.R
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme

@Preview
@Composable
private fun IconPreview() {
    ACRemoteAppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                R.drawable.ic_placeholder,
                alt = "placeholder",
                sizeVariation = SizeVariation.SMALL,
            )
            Icon(
                R.drawable.ic_placeholder,
                alt = "placeholder",
                sizeVariation = SizeVariation.MEDIUM,
            )
            Icon(
                R.drawable.ic_placeholder,
                alt = "placeholder",
                sizeVariation = SizeVariation.LARGE,
            )
        }
    }
}

enum class SizeVariation {
    LARGE, MEDIUM, SMALL,
}

@Composable
fun Icon(
    @DrawableRes id: Int,
    alt: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    sizeVariation: SizeVariation = SizeVariation.MEDIUM,
) {
    Image(
        painter = painterResource(id = id),
        contentDescription = alt,
        modifier = modifier
            .clickable(onClick = onClick)
            .size(
                when (sizeVariation) {
                    SizeVariation.SMALL -> 24.dp
                    SizeVariation.MEDIUM -> 32.dp
                    SizeVariation.LARGE -> 40.dp
                }
            ),
    )
}
