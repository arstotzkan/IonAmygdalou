package aueb.mlp.ac

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import aueb.mlp.ac.model.ACMode
import aueb.mlp.ac.model.AirConditioner

class MainActivityViewModel(
    private val airConditioner: AirConditioner,
): ViewModel() {

    var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState())
        private set

    fun incrementTemperature() {
        val success = airConditioner.incrementTemperature()

        Log.d("MAVM", "Success: $success")

        uiState = uiState.copy(
            error = if (!success) "Temperature cannot go above 30" else "",
            temperature = airConditioner.temperature
        )
    }

    fun decrementTemperature() {
        val success = airConditioner.decrementTemperature()

        Log.d("MAVM", "Success: $success")

        uiState = uiState.copy(
            error = if (!success) "Temperature cannot go below 18" else "",
            temperature = airConditioner.temperature
        )
    }

    fun setMode(mode: String) {
        airConditioner.setMode(
            when(mode) {
                "HEAT" -> ACMode.HEAT
                "COLD" -> ACMode.COLD
                // ...
                else -> error("Invalid mode: $mode")
            }
        )

        uiState = uiState.copy(
            mode = when(airConditioner.acMode) {
                ACMode.HEAT -> Mode.HEAT
                ACMode.COLD -> Mode.COLD
                // ...
            }
        )
    }

    // ...
}
