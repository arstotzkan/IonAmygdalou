package aueb.mlp.ac

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import aueb.mlp.ac.model.ACFan
import aueb.mlp.ac.model.ACMode
import aueb.mlp.ac.model.ACBlinds
import aueb.mlp.ac.model.AirConditioner
import aueb.mlp.ac.model.OneTimeAlarm
import aueb.mlp.ac.model.RepeatedAlarm
import aueb.mlp.ac.model.WeeklySchedule

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
        airConditioner.turnOnAlarm.toggleOnOff()
        uiState = uiState.copy(
            turnOnAlarm = uiState.turnOnAlarm.copy(
                on = !uiState.turnOnAlarm.on,
            ),
        )
    }

    fun toggleTurnOffAlarm() {
        airConditioner.turnOffAlarm.toggleOnOff()
        uiState = uiState.copy(
            turnOnAlarm = uiState.turnOffAlarm.copy(
                on = !uiState.turnOffAlarm.on,
            ),
        )
    }

    fun setTurnOnAlarmTime(hours: Int, minutes: Int) {
        if (hours in (0..23 ) && minutes in (0..59 )){
            airConditioner.turnOnAlarm.setTime(hours, minutes)
            uiState = uiState.copy(
                turnOnAlarm = uiState.turnOnAlarm.copy(
                    hours = hours,
                    minutes = minutes
                ),
            )
        } else {
            RuntimeException("Invalid arguements");
        }

    }

    fun setTurnOffAlarmTime(hours: Int, minutes: Int) {
        if (hours in (0..23 ) && minutes in (0..59 )){
            airConditioner.turnOffAlarm.setTime(hours, minutes)
            uiState = uiState.copy(
                turnOnAlarm = uiState.turnOffAlarm.copy(
                    hours = hours,
                    minutes = minutes
                ),
            )
        } else {
            RuntimeException("Invalid arguements");
        }
    }

    fun setTurnOnAlarmRepeatToOneTime() {
        airConditioner.setTurnOnAlarm(
            OneTimeAlarm(airConditioner.turnOnAlarm.hours, airConditioner.turnOnAlarm.minutes) //TODO: make new constructor work
        )

        uiState = uiState.copy(
            turnOnAlarm = uiState.turnOnAlarm.copy(
                alarmType = OneTimeAlarmType(),
            ),
        )
    }

    fun setTurnOnAlarmRepeatToEveryday() {
        airConditioner.setTurnOnAlarm(
            RepeatedAlarm(airConditioner.turnOnAlarm.hours, airConditioner.turnOnAlarm.minutes) //TODO: make new constructor work
        )

        uiState = uiState.copy(
            turnOnAlarm = uiState.turnOnAlarm.copy(
                alarmType = RepeatingAlarmType(mutableListOf(true,true,true,true,true,true,true))
            )
        )
    }

//    fun setTurnOnAlarmRepeatToCustom(days: List<Boolean>) {
//
//        airConditioner.setTurnOnAlarm(
//            RepeatedAlarm( airConditioner.turnOnAlarm.hours, airConditioner.turnOnAlarm.minutes)
//        )
//
//        for (i in 0..days.size){
//        }
//
//        uiState = uiState.copy(
//            turnOnAlarm = uiState.turnOnAlarm.copy(
//                alarmType = RepeatingAlarmType(
//                    days,
//                )
//            ),
//        )
//    }

    fun setTurnOffAlarmRepeatToOneTime() {
        airConditioner.setTurnOffAlarm(
            OneTimeAlarm(airConditioner.turnOffAlarm.hours, airConditioner.turnOffAlarm.minutes) //TODO: make new constructor work
        )

        uiState = uiState.copy(
            turnOnAlarm = uiState.turnOffAlarm.copy(
                alarmType = OneTimeAlarmType(),
            ),
        )
    }

    fun setTurnOffAlarmRepeatToEveryday() {
        airConditioner.setTurnOnAlarm(
            RepeatedAlarm(airConditioner.turnOffAlarm.hours, airConditioner.turnOffAlarm.minutes) //TODO: make new constructor work
        )

        uiState = uiState.copy(
            turnOnAlarm = uiState.turnOffAlarm.copy(
                alarmType = RepeatingAlarmType(mutableListOf(true,true,true,true,true,true,true)), //TODO: WILL CHECK A WAY TO DO THIS MORE EFFICIENTLY LATER
            ),
        )
    }

}
