package aueb.mlp.ac.ui.theme.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aueb.mlp.ac.ui.theme.ACShapes

data class AcButtonColors(
    val containerColor: Color,
    val contentColor: Color,
) {
    companion object {
        val Disabled = AcButtonColors(
            containerColor = Color(0xFFC5C7F1),
            contentColor = Color(0x80000000),
        )
        val Enabled = AcButtonColors(
            containerColor = Color(0xFFECF0FF),
            contentColor = Color(0xFF000000),
        )
        val Selected = AcButtonColors(
            containerColor = Color(0xFF008DAC),
            contentColor = Color(0xFFFFFFFF),
        )
    }
}

@Composable
fun PlainButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    contentColorSelector: () -> Color = {
        if (enabled) enabledColors.contentColor else disabledColors.contentColor
    },
    content: @Composable (() -> Color) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(onClick = { if (enabled) onClick() })
            .padding(8.dp)
            .clip(shape = ACShapes.medium)
            .background(if (enabled) enabledColors.containerColor else disabledColors.containerColor)
            .then(modifier)
//            .wrapContentHeight() // TODO: ### remove. do size the proper way ###
//            .size(300.dp, 100.dp) // TODO: ### remove. do size the proper way ###
    ) {
        content(contentColorSelector)
    }
}

@Composable
//TODO: Maybe needs some refactoring/renaming??
fun PlainButtonWithSwitchAndText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    switchChecked: Boolean,
    textSizeVariation: TextSizeVariation = TextSizeVariation.BODY_MEDIUM,
){
    PlainButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        enabledColors = enabledColors,
        disabledColors = disabledColors,
    ) { contentColorSelector ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
        ){
            AcText(
                text = text,
                textSizeVariation = textSizeVariation,
                color = contentColorSelector(),
            )
            Switch(
                checked = switchChecked,
                onCheckedChange = { _: Boolean -> onClick() },
            )
        }
    }
}

@Composable
/**
 * `!enabled` is checked first; disabled buttons have highest precedence. `selected` is checked
 * second; enabled buttons change depending on whether they are selected. If `!selected` fall back
 * to enabledColors.
 */
fun StatefulButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    selected: Boolean,
    selectedColors: AcButtonColors = AcButtonColors.Selected,
    contentColorSelector: () -> Color = {
        if (!enabled) disabledColors.contentColor else {
            if (selected) selectedColors.contentColor else enabledColors.contentColor
        }
    },
    content: @Composable (() -> Color) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(onClick = { if (enabled) onClick() })
            .padding(8.dp) // TODO: ### remove. do spacing the proper way ###
            .clip(shape = ACShapes.medium)
            .background(
                if (!enabled)
                    disabledColors.containerColor
                else
                    if (selected) selectedColors.containerColor
                    else enabledColors.containerColor
            )
            .then(modifier)
            // moved after .then because first size is used
//            .wrapContentHeight() // TODO: ### remove. do size the proper way ###
//            .size(300.dp, 100.dp) // TODO: ### remove. do size the proper way ###
    ) {
        content(contentColorSelector)
    }
}

@Composable
fun PlainTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    textSizeVariation: TextSizeVariation = TextSizeVariation.BODY_MEDIUM,
) {
    PlainButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        enabledColors = enabledColors,
        disabledColors = disabledColors,
    ) { contentColorSelector ->
        AcText(
            text = text,
            textSizeVariation = textSizeVariation,
            color = contentColorSelector(),
        )
    }
}

@Composable
fun StatefulTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    selected: Boolean,
    selectedColors: AcButtonColors = AcButtonColors.Selected,
    textSizeVariation: TextSizeVariation = TextSizeVariation.BODY_MEDIUM,
) {
    StatefulButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        enabledColors = enabledColors,
        disabledColors = disabledColors,
        selected = selected,
        selectedColors = selectedColors,
    ) { contentColorSelector ->
        AcText(
            text = text,
            textSizeVariation = textSizeVariation,
            color = contentColorSelector(),
        )
    }
}

@Composable
fun PlainIconButton(
    @DrawableRes id: Int,
    alt: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    sizeVariation: SizeVariation,
) {
    PlainButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        enabledColors = enabledColors,
        disabledColors = disabledColors,
    ) { _ ->
        AcIcon(
            id = id,
            alt = alt,
            sizeVariation = sizeVariation,
            // tint = contentColorSelector(),
        )
    }
}

@Composable
fun RowScope.ModeButton(
    text: String,
    @DrawableRes id: Int,
    alt: String,
    onClick: () -> Unit,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    selected: Boolean,
    selectedColors: AcButtonColors = AcButtonColors.Selected,
) {
    Box(
        modifier = Modifier.weight(1f, fill = true),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 50.dp,
                    vertical = 20.dp,
                ),
        ) {
            StatefulButton(
                onClick = onClick,
                modifier = Modifier.fillMaxSize(),
                enabled = enabled,
                enabledColors = enabledColors,
                disabledColors = disabledColors,
                selected = selected,
                selectedColors = selectedColors,
            ) { contentColorSelector ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    AcText(
                        text = text,
                        textSizeVariation = TextSizeVariation.BODY_MEDIUM,
                        color = contentColorSelector(),
                    )
                    AcIcon(
                        id = id,
                        alt = alt,
                        sizeVariation = SizeVariation.SMALL,
                        tint = contentColorSelector(),
                    )
                }
            }
        }
    }
}

@Composable
fun RowButton(
    @DrawableRes id: Int,
    alt: String,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    sizeVariation: SizeVariation,
    textSizeVariation: TextSizeVariation = TextSizeVariation.BODY_MEDIUM,
){
    PlainButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        enabledColors = enabledColors,
        disabledColors = disabledColors,
    ) { contentColorSelector ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .wrapContentSize()
                .padding(24.dp)
        ) {
            AcText(
                text = text,
                textSizeVariation = textSizeVariation,
                color = contentColorSelector(),
            )
            AcIcon(
                id = id,
                alt = alt,
                sizeVariation = sizeVariation,
            )
        }
    }
}

@Composable
fun RowButtonWithIconCallback(
    @DrawableRes id: Int,
    alt: String,
    text: String,
    onClick: () -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    enabledColors: AcButtonColors = AcButtonColors.Enabled,
    disabledColors: AcButtonColors = AcButtonColors.Disabled,
    sizeVariation: SizeVariation,
    textSizeVariation: TextSizeVariation = TextSizeVariation.BODY_MEDIUM,
){
    PlainButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(1f),
        enabled = enabled,
        enabledColors = enabledColors,
        disabledColors = disabledColors,
    ) { contentColorSelector ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            AcText(
                text = text,
                textSizeVariation = textSizeVariation,
                color = contentColorSelector(),
            )
            PlainIconButton(
                id = id,
                alt = alt,
                onClick = onIconClick,
                enabled = enabled,
                sizeVariation = sizeVariation,
            )
        }
    }
}
