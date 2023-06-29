package aueb.mlp.ac.ui.theme.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
                sizeVariation = SizeVariation.LARGE,
            )
        }
    }
}

enum class SizeVariation {
    SMALL, LARGE,
}

@Composable
fun Icon(
    @DrawableRes id: Int,
    alt: String,
    modifier: Modifier = Modifier,
    size: Dp,
) {
    Image(
        painter = painterResource(id = id),
        contentDescription = alt,
        modifier = modifier.size(size),
    )
}

@Composable
fun Icon(
    @DrawableRes id: Int,
    alt: String,
    modifier: Modifier = Modifier,
    sizeVariation: SizeVariation,
) {
    Icon(
        id = id,
        alt = alt,
        modifier = modifier,
        size = when (sizeVariation) {
            SizeVariation.SMALL -> 32.dp
            SizeVariation.LARGE -> 64.dp
        }
    )
}
