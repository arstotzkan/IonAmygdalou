package aueb.mlp.ac

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import aueb.mlp.ac.model.ACBlinds
import aueb.mlp.ac.model.ACFan
import aueb.mlp.ac.model.ACMode
import aueb.mlp.ac.model.AirConditioner
import aueb.mlp.ac.model.AlarmType
import java.time.DayOfWeek

class MainActivityViewModel(
    private val airConditioner: AirConditioner,
): ViewModel() {

    var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState())
        private set


    fun toggleOnOff(){
        airConditioner.toggleOnOff()
        uiState = uiState.copy(
            acIsOn = airConditioner.on
        )
    }


    fun changeMenu(menu: String){
        uiState = uiState.copy(
            activeMenu = when(menu){
                "MAIN"-> Menu.MAIN
                "MODE" -> Menu.MODE
                "FAN"-> Menu.FAN
                "BLINDS"-> Menu.BLINDS
                "TIMER"-> Menu.TIMER
                "CHANGE"->Menu.CHANGE
                "ADDAC"-> Menu.ADDAC
                else -> error("Invalid menu: $menu")
            }
        )

    }

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
                "DRY" -> ACMode.DRY
                "AUTO" -> ACMode.AUTO
                else -> error("Invalid mode: $mode")
            }
        )

        uiState = uiState.copy(
            mode = when(airConditioner.acMode) {
                ACMode.HEAT -> Mode.HEAT
                ACMode.COLD -> Mode.COLD
                ACMode.DRY -> Mode.DRY
                ACMode.AUTO -> Mode.AUTO
            }
        )
    }

    fun setFan(fan : String){
        airConditioner.setFan(
            when(fan) {
                "SILENT" -> ACFan.SILENT
                "NORMAL" -> ACFan.NORMAL
                "TURBO" -> ACFan.TURBO
                else -> error("Invalid fan mode: $fan")
            }
        )

        uiState = uiState.copy(
            fan = when(airConditioner.acFan) {
                ACFan.SILENT -> Fan.SILENT
                ACFan.NORMAL -> Fan.NORMAL
                ACFan.TURBO -> Fan.TURBO
            }
        )
    }

    fun setBlinds (blinds: String){
        airConditioner.setBlinds(
            when(blinds) {
                "HORIZONTAL" -> ACBlinds.HORIZONTAL
                "VERTICAL" -> ACBlinds.VERTICAL
                "FOLLOW ME" -> ACBlinds.FOLLOW_ME
                "OFF" -> ACBlinds.OFF
                else -> error("Invalid blind mode: $blinds")
            }
        )

        uiState = uiState.copy(
            blinds = when(airConditioner.blinds) {
                ACBlinds.HORIZONTAL -> Blinds.HORIZONTAL
                ACBlinds.VERTICAL -> Blinds.VERTICAL
                ACBlinds.FOLLOW_ME -> Blinds.FOLLOW_ME
                ACBlinds.OFF -> Blinds.OFF
            }
        )
    }

    fun toggleEcoMode(){
        airConditioner.toggleEcoMode()
        uiState = uiState.copy(
            ecoMode = airConditioner.ecoMode
        )
    }

    fun toggleTurnOnAlarm() {
        airConditioner.toggleTurnOnAlarm()
        airConditioner.turnOnAlarm.toggleOnOff()

        uiState = uiState.copy(
            turnOnAlarmState = airConditioner.turnOnAlarm.on
        )
    }

    fun toggleTurnOffAlarm() {
        airConditioner.toggleTurnOffAlarm()

        uiState = uiState.copy(
            turnOffAlarmState = airConditioner.turnOffAlarm.on
        )
    }

    fun setTurnOnAlarmTime(hours: Int, minutes: Int) {
        if (airConditioner.setTurnOnAlarmTime(hours, minutes)) {
            uiState = uiState.copy(
                turnOnAlarmTime = Time(hours, minutes),
            )
        } else {
            // TODO: better errors. will this ever be thrown?
            RuntimeException("Invalid arguements");
        }
    }

    fun setTurnOffAlarmTime(hours: Int, minutes: Int) {
        if (airConditioner.setTurnOffAlarmTime(hours, minutes)) {
            uiState = uiState.copy(
                turnOffAlarmTime = Time(hours, minutes),
            )
        } else {
            // TODO: better errors. will this ever be thrown?
            RuntimeException("Invalid arguements");
        }
    }

    fun setTurnOnAlarmRepeat(repeat: String) {
        airConditioner.setTurnOnAlarmType(
            when (repeat) {
                "ONE_TIME" -> AlarmType.OneTime
                "EVERYDAY" -> AlarmType.Everyday
                "CUSTOM" -> AlarmType.Custom
                else -> error("Invalid repeat mode: $repeat")
            }
        )

        uiState = uiState.copy(
            turnOnAlarmRepeat = with(airConditioner.turnOnAlarm.type) {
                when (this) {
                    is AlarmType.OneTime -> AlarmRepeat.OneTimeRepeat
                    is AlarmType.Everyday -> AlarmRepeat.EverydayRepeat
                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )
    }

    fun setTurnOffAlarmRepeat(repeat: String) {
        airConditioner.setTurnOffAlarmType(
            when (repeat) {
                "ONE_TIME" -> AlarmType.OneTime
                "EVERYDAY" -> AlarmType.Everyday
                "CUSTOM" -> AlarmType.Custom
                else -> error("Invalid repeat mode: $repeat")
            }
        )

        uiState = uiState.copy(
            turnOffAlarmRepeat = with(airConditioner.turnOffAlarm.type) {
                when (this) {
                    is AlarmType.OneTime -> AlarmRepeat.OneTimeRepeat
                    is AlarmType.Everyday -> AlarmRepeat.EverydayRepeat
                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )
    }

    fun toggleTurnOnAlarmDay(day: DayOfWeek) {
        with(airConditioner.turnOnAlarm.type) {
            when (this) {
                is AlarmType.OneTime, is AlarmType.Everyday
                    -> error("Can only toggle day on Custom Alarm Type")
                is AlarmType.Custom -> this.toggleDay(day)
            }
        }

        uiState = uiState.copy(
            turnOnAlarmRepeat = with(airConditioner.turnOffAlarm.type) {
                when (this) {
                    is AlarmType.OneTime, is AlarmType.Everyday
                    -> error("Can only toggle day on Custom Alarm Type")
                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )
    }

    fun toggleTurnOffAlarmDay(day: DayOfWeek) {
        with(airConditioner.turnOffAlarm.type) {
            when (this) {
                is AlarmType.OneTime, is AlarmType.Everyday
                -> error("Can only toggle day on Custom Alarm Type")
                is AlarmType.Custom -> this.toggleDay(day)
            }
        }

        uiState = uiState.copy(
            turnOffAlarmRepeat = with(airConditioner.turnOffAlarm.type) {
                when (this) {
                    is AlarmType.OneTime, is AlarmType.Everyday
                    -> error("Can only toggle day on Custom Alarm Type")
                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )
    }
}
