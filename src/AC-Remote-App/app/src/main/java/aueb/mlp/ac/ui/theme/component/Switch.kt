package aueb.mlp.ac.ui.theme.component

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AcSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = Color(0xFF0085FF),
        checkedTrackColor = Color(0xFFD9D9D9),
        checkedBorderColor = Color(0xFF000000),
        uncheckedThumbColor = Color(0xFFBFBFBF),
        uncheckedTrackColor = Color(0x80D9D9D9),
        uncheckedBorderColor = Color(0xFF000000),
    )
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = colors,
    )
}
