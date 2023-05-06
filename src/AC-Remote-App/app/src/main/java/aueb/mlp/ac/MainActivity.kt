package aueb.mlp.ac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import aueb.mlp.ac.ui.component.ButtonWithText
import aueb.mlp.ac.ui.component.ErrorLabel
import aueb.mlp.ac.ui.component.LargeText
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
        onIncrementTemperature = { mainActivityViewModel.incrementTemperature() },
        onDecrementTemperature = { mainActivityViewModel.decrementTemperature() },
        onModeChanged = { mode: String -> mainActivityViewModel.setMode(mode) }
    )
}

@Composable
fun MainScreenContent(
    uiState: MainActivityUiState,
    onIncrementTemperature: () -> Unit,
    onDecrementTemperature: () -> Unit,
    onModeChanged: (String) -> Unit,
) {
    Surface(
        color = when(uiState.mode) {
            Mode.HEAT -> Color(0xBBDF6B00)
            Mode.COLD -> Color(0xBB80AFB9)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(64.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LargeText(
                text = uiState.temperature.toString(),
            )
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
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ButtonWithText(
                    text = "HEAT",
                    onClick = { onModeChanged("HEAT") },
                )
                ButtonWithText(
                    text = "COLD",
                    onClick = { onModeChanged("COLD") },
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
