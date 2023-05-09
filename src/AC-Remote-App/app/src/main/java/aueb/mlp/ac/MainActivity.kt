package aueb.mlp.ac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aueb.mlp.ac.model.LoggingAirConditioner
import aueb.mlp.ac.ui.component.ButtonWithIcon
import aueb.mlp.ac.ui.component.ErrorLabel
import aueb.mlp.ac.ui.component.LargeText
import aueb.mlp.ac.ui.component.ButtonWithMediumText
import aueb.mlp.ac.ui.component.MediumText
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ACRemoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        MainActivityViewModel(
                            LoggingAirConditioner()
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    mainActivityViewModel: MainActivityViewModel
) {
    MainScreenContent(
        uiState = mainActivityViewModel.uiState,
        onSwitchOnOff = {mainActivityViewModel.toggleOnOff()},
        onIncrementTemperature = { mainActivityViewModel.incrementTemperature() },
        onDecrementTemperature = { mainActivityViewModel.decrementTemperature() },
        onModeChanged = { mode: String -> mainActivityViewModel.setMode(mode) },
        onFanChanged = { mode: String -> mainActivityViewModel.setFan(mode) },
        onBlindsChanged = { mode: String -> mainActivityViewModel.setBlinds(mode) },
        onEcoModeChanged = { mainActivityViewModel.toggleEcoMode() },
    )
}

@Composable
fun MainScreenContent(
    uiState: MainActivityUiState,
    onSwitchOnOff: () -> Unit,
    onIncrementTemperature: () -> Unit,
    onDecrementTemperature: () -> Unit,
    onModeChanged: (String) -> Unit,
    onFanChanged: (String) -> Unit,
    onBlindsChanged: (String) -> Unit,
    onEcoModeChanged: () -> Unit,
) {
    Surface(
        color = when(uiState.mode) {
            Mode.HEAT -> Color(0xBBDF6B00)
            Mode.COLD -> Color(0xBB80AFB9)
            Mode.DRY -> Color(0xBB00BBCC)
            Mode.AUTO -> Color(0xBBAAAAAA)
        },
        //malakisthka ligo sta teleutaia dyo
        border = when(uiState.ecoMode){
            true -> BorderStroke(10.dp, Color(0xBB00AA11))
            false -> BorderStroke(0.dp, Color(0xBB00AA11))
        },
        contentColor = when(uiState.acIsOn){
            true -> Color(0xBB222222)
            false -> Color(0xBBEEEEEE)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ) {
                LargeText(
                    text = "Temperature: " + uiState.temperature.toString(),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ) {
                LargeText(
                    text = "Mode: " + uiState.mode.toString(),
                )

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                LargeText(
                    text = "Fan: " + uiState.fan.toString(),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                LargeText(
                    text = "Blinds: " + uiState.blinds.toString(),
                )

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                LargeText(
                    text = "Eco mode is on: " + uiState.ecoMode.toString(),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                LargeText(
                    text = "AC is on: " + uiState.acIsOn.toString(),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                MediumText(
                    text = "Temp Control",
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ) {
                ButtonWithIcon(
                    id = R.drawable.ic_placeholder,
                    alt = "Decrement Temperature",
                    onClick = onDecrementTemperature,
                    enabled = false,
                )
                ButtonWithIcon(
                    id = R.drawable.ic_minus,
                    alt = "Decrement Temperature",
                    onClick = onDecrementTemperature,
                )
                ButtonWithIcon(
                    id = R.drawable.ic_plus,
                    alt = "Increment Temperature",
                    onClick = { onIncrementTemperature() },
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                MediumText(
                    text = "AC Modes",
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ButtonWithMediumText(
                    text = "HEAT",
                    onClick = { onModeChanged("HEAT") },
                )
                ButtonWithMediumText(
                    text = "COLD",
                    onClick = { onModeChanged("COLD") },
                )
                ButtonWithMediumText(
                        text = "DRY",
                onClick = { onModeChanged("DRY") },
                )
                ButtonWithMediumText(
                    text = "AUTO",
                    onClick = { onModeChanged("AUTO") },
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                MediumText(
                    text = "Fans",
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ButtonWithMediumText(
                    text = "SILENT",
                    onClick = { onFanChanged("SILENT") },
                )
                ButtonWithMediumText(
                    text = "NORMAL",
                    onClick = { onFanChanged("NORMAL") },
                )
                ButtonWithMediumText(
                    text = "TURBO",
                    onClick = { onFanChanged("TURBO") },
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                MediumText(
                    text = "Blinds",
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ButtonWithMediumText(
                    text = "HORIZONTAL",
                    onClick = { onBlindsChanged("HORIZONTAL") },
                )
                ButtonWithMediumText(
                    text = "VERTICAL",
                    onClick = { onBlindsChanged("VERTICAL") },
                )
                ButtonWithMediumText(
                    text = "FOLLOW ME",
                    onClick = { onBlindsChanged("FOLLOW ME") },
                )
                ButtonWithMediumText(
                    text = "OFF",
                    onClick = { onBlindsChanged("OFF") },
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .wrapContentSize()
            ){
                MediumText(
                    text = "Misc",
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ButtonWithMediumText(
                    text = "TOGGLE ECO MODE",
                    onClick = { onEcoModeChanged() },
                )

                ButtonWithMediumText(
                    text = "SWITCH AC ON/OFF",
                    onClick = { onSwitchOnOff() },
                )
            }
            if (uiState.error != "") {
                ErrorLabel(
                    text = uiState.error,
                )
            }
        }
    }
}
