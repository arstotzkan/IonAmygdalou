package aueb.mlp.ac

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import aueb.mlp.ac.model.ACBlinds
import aueb.mlp.ac.model.ACFan
import aueb.mlp.ac.model.ACManager
import aueb.mlp.ac.model.ACMode
import aueb.mlp.ac.model.AirConditioner
import aueb.mlp.ac.model.AlarmType
import java.time.DayOfWeek

class MainActivityViewModel(
    private val acManager: ACManager,
    private val acMenuManager : HashMap<String, Menu>
): ViewModel() {

    private lateinit var airConditioner: AirConditioner

    var acListState by mutableStateOf(listOf<String>())
        private set

    var uiState: MainActivityUiState? by mutableStateOf(null)
        private set

    fun createNewAc(acName: String) {
        acManager.createNewAc(acName)
        acMenuManager[acName] = Menu.MODE
        acListState = acManager.getAllAcNames()
    }

    fun deleteAcByName(acName: String) {
        acManager.deleteAcByName(acName)
        acListState = acManager.getAllAcNames()
    }

    fun setCurrentAcByName(acName: String) {
        airConditioner = acManager.getAcByName(acName)
        updateUiStateWithCurrentAc()
    }

    fun toggleOnOff(){
        airConditioner.toggleOnOff()
        uiState = uiState!!.copy(
            acIsOn = airConditioner.on
        )
    }

    fun changeMenu(menu: String){
        uiState = uiState!!.copy(
            activeMenu = when(menu){
                "MAIN"-> Menu.MAIN
                "MODE" -> Menu.MODE
                "FAN"-> Menu.FAN
                "BLINDS"-> Menu.BLINDS
                "TIMER"-> Menu.TIMER
                "TIMER_ON"-> Menu.TIMER_ON
                "TIMER_OFF"-> Menu.TIMER_OFF
                "CHANGE"->Menu.CHANGE
                "ADDAC"-> Menu.ADDAC
                else -> error("Invalid menu: $menu")
            }
        )

        if (uiState!!.acName != null){
            acMenuManager[uiState!!.acName] = when(menu){
                "MAIN"-> Menu.MAIN
                "MODE" -> Menu.MODE
                "FAN"-> Menu.FAN
                "BLINDS"-> Menu.BLINDS
                "TIMER"-> Menu.TIMER
                "TIMER_ON"-> Menu.TIMER_ON
                "TIMER_OFF"-> Menu.TIMER_OFF
                else -> acMenuManager[uiState!!.acName] as Menu
            }
        }


    }

    fun incrementTemperature() {
        val success = airConditioner.incrementTemperature()

        Log.d("MAVM", "Success: $success")

        uiState = uiState!!.copy(
            error = if (!success) "Temperature cannot go above 30" else "",
            temperature = airConditioner.temperature
        )
    }

    fun decrementTemperature() {
        val success = airConditioner.decrementTemperature()

        Log.d("MAVM", "Success: $success")

        uiState = uiState!!.copy(
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

        uiState = uiState!!.copy(
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

        uiState = uiState!!.copy(
            fan = when(airConditioner.acFan) {
                ACFan.SILENT -> Fan.SILENT
                ACFan.NORMAL -> Fan.NORMAL
                ACFan.TURBO -> Fan.TURBO
            }
        )
    }

    fun setBlinds (blinds: String){
        Log.d("DEBUG", "Blinds ")
        airConditioner.setBlinds(
            when(blinds) {
                "HORIZONTAL" -> ACBlinds.HORIZONTAL
                "VERTICAL" -> ACBlinds.VERTICAL
                "FOLLOW ME" -> ACBlinds.FOLLOW_ME
                "OFF" -> ACBlinds.OFF
                else -> error("Invalid blind mode: $blinds")
            }
        )

        uiState = uiState!!.copy(
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
        uiState = uiState!!.copy(
            ecoMode = airConditioner.ecoMode
        )
    }

    fun toggleTurnOnAlarm() {
        airConditioner.toggleTurnOnAlarm()

        uiState = uiState!!.copy(
            turnOnAlarmState = airConditioner.turnOnAlarm.on,
        )

        updateAlarmsFromParts()
    }

    fun toggleTurnOffAlarm() {
        airConditioner.toggleTurnOffAlarm()

        uiState = uiState!!.copy(
            turnOffAlarmState = airConditioner.turnOffAlarm.on,
        )

        updateAlarmsFromParts()
    }

    fun setTurnOnAlarmTime(time: Time) {
        if (airConditioner.setTurnOnAlarmTime(time.hours, time.minutes)) {
            uiState = uiState!!.copy(
                turnOnAlarmTime = time,
            )

            updateAlarmsFromParts()
        } else {
            // TODO: better errors. will this ever be thrown?
            // most likely no, won't be thrown because of time picker
            // but still good practice to include
            error("Invalid time: $time")
        }
    }

    fun setTurnOffAlarmTime(time: Time) {
        if (airConditioner.setTurnOffAlarmTime(time.hours, time.minutes)) {
            uiState = uiState!!.copy(
                turnOffAlarmTime = time,
            )

            updateAlarmsFromParts()
        } else {
            // TODO: better errors. will this ever be thrown?
            // most likely no, won't be thrown because of time picker
            // but still good practice to include
            error("Invalid time: $time")
        }
    }

    fun setTurnOnAlarmRepeat(repeat: AlarmRepeat) {
        airConditioner.setTurnOnAlarmType(
            when (repeat) {
                AlarmRepeat.OneTimeRepeat -> AlarmType.OneTime
                AlarmRepeat.EverydayRepeat -> AlarmType.Everyday
                is AlarmRepeat.CustomRepeat -> AlarmType.Custom
            }
        )

        uiState = uiState!!.copy(
            turnOnAlarmRepeat = with(airConditioner.turnOnAlarm.type) {
                when (this) {
                    AlarmType.OneTime -> AlarmRepeat.OneTimeRepeat
                    AlarmType.Everyday -> AlarmRepeat.EverydayRepeat
                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )

        updateAlarmsFromParts()
    }

    fun setTurnOffAlarmRepeat(repeat: AlarmRepeat) {
        airConditioner.setTurnOffAlarmType(
            when (repeat) {
                AlarmRepeat.OneTimeRepeat -> AlarmType.OneTime
                AlarmRepeat.EverydayRepeat -> AlarmType.Everyday
                is AlarmRepeat.CustomRepeat -> AlarmType.Custom
            }
        )

        uiState = uiState!!.copy(
            turnOffAlarmRepeat = with(airConditioner.turnOffAlarm.type) {
                when (this) {
                    AlarmType.OneTime -> AlarmRepeat.OneTimeRepeat
                    AlarmType.Everyday -> AlarmRepeat.EverydayRepeat
                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )

        updateAlarmsFromParts()
    }

    fun toggleTurnOnAlarmDay(day: DayOfWeek) {
        with(airConditioner.turnOnAlarm.type) {
            when (this) {
                AlarmType.OneTime, AlarmType.Everyday
                -> error("Can only toggle day on Custom Alarm Type")

                is AlarmType.Custom -> this.toggleDay(day)
            }
        }

        uiState = uiState!!.copy(
            turnOnAlarmRepeat = with(airConditioner.turnOnAlarm.type) {
                when (this) {
                    AlarmType.OneTime, AlarmType.Everyday
                    -> error("Can only toggle day on Custom Alarm Type")

                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )

        updateAlarmsFromParts()
    }

    fun toggleTurnOffAlarmDay(day: DayOfWeek) {
        with(airConditioner.turnOffAlarm.type) {
            when (this) {
                AlarmType.OneTime, AlarmType.Everyday
                -> error("Can only toggle day on Custom Alarm Type")

                is AlarmType.Custom -> this.toggleDay(day)
            }
        }

        uiState = uiState!!.copy(
            turnOffAlarmRepeat = with(airConditioner.turnOffAlarm.type) {
                when (this) {
                    AlarmType.OneTime, AlarmType.Everyday
                    -> error("Can only toggle day on Custom Alarm Type")

                    is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*this.days))
                }
            }
        )

        updateAlarmsFromParts()
    }

    private fun updateUiStateWithCurrentAc() {

        with (airConditioner) {

            val newActiveMenu = if (acMenuManager[name] != null) acMenuManager[name] as Menu else Menu.MODE

            uiState = MainActivityUiState(
                error = "",
                acName = name,
                activeMenu = newActiveMenu,
                acIsOn = on,
                temperature = temperature,
                mode = when (acMode) {
                    ACMode.HEAT -> Mode.HEAT
                    ACMode.COLD -> Mode.COLD
                    ACMode.DRY -> Mode.DRY
                    ACMode.AUTO -> Mode.AUTO
                },
                fan = when (this.acFan) {
                    ACFan.SILENT -> Fan.SILENT
                    ACFan.NORMAL -> Fan.NORMAL
                    ACFan.TURBO -> Fan.TURBO
                },
                blinds = when (this.blinds) {
                    ACBlinds.HORIZONTAL -> Blinds.HORIZONTAL
                    ACBlinds.VERTICAL -> Blinds.VERTICAL
                    ACBlinds.FOLLOW_ME -> Blinds.FOLLOW_ME
                    ACBlinds.OFF -> Blinds.FOLLOW_ME
                },
                ecoMode = ecoMode,
                turnOnAlarm = Alarm(), // will be set from #updateAlarmsFromParts()
                turnOffAlarm = Alarm(), // will be set from #updateAlarmsFromParts()
                turnOnAlarmState = turnOnAlarm.on,
                turnOffAlarmState = turnOffAlarm.on,
                turnOnAlarmTime = with(turnOnAlarm) { Time(hours, minutes) },
                turnOffAlarmTime = with(turnOffAlarm) { Time(hours, minutes) },
                turnOnAlarmRepeat = turnOnAlarm.type.let {
                    when (it) {
                        AlarmType.OneTime -> AlarmRepeat.OneTimeRepeat
                        AlarmType.Everyday -> AlarmRepeat.EverydayRepeat
                        is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*it.days))
                    }
                },
                turnOffAlarmRepeat = turnOffAlarm.type.let {
                    when (it) {
                        AlarmType.OneTime -> AlarmRepeat.OneTimeRepeat
                        AlarmType.Everyday -> AlarmRepeat.EverydayRepeat
                        is AlarmType.Custom -> AlarmRepeat.CustomRepeat(days = listOf(*it.days))
                    }
                },
            )
        }

        updateAlarmsFromParts()
    }

    private fun updateAlarmsFromParts() {
        uiState = with(uiState!!) {
            copy(
                turnOnAlarm = Alarm(turnOnAlarmState, turnOnAlarmTime, turnOnAlarmRepeat),
                turnOffAlarm = Alarm(turnOffAlarmState, turnOffAlarmTime, turnOffAlarmRepeat)
            )
        }
    }
}
