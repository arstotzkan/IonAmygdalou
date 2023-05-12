package aueb.mlp.ac

enum class Menu {
    MODE, FAN, MAIN, BLINDS, TIMER, CHANGE, ADDAC
}

enum class Mode {
    HEAT, COLD, DRY, AUTO
}

enum class Fan {
    SILENT, NORMAL, TURBO,
}

enum class Blinds {
    HORIZONTAL, VERTICAL, FOLLOW_ME, OFF
}

enum class Repeat {
    ONE_TIME, EVERYDAY, CUSTOM,
}

sealed class AlarmType

class OneTimeAlarmType: AlarmType()
class RepeatingAlarmType(
    val days: List<Boolean>,
): AlarmType()

/*
when(alarm) {
    is OneTimeAlarmType -> // ...
    is RepeatingAlarmType -> alarm.days
}
*/

data class Alarm(
    val on: Boolean = false,
    val hours: Int = 0,
    val minutes: Int = 0,
    val alarmType: AlarmType = OneTimeAlarmType(),
)

data class MainActivityUiState(
    val error: String = "",
    val activeMenu: Menu = Menu.MODE,
    val acIsOn: Boolean = true,
    val temperature: Int = 25,
    val mode: Mode = Mode.HEAT,
    val fan: Fan = Fan.NORMAL,
    val blinds: Blinds = Blinds.OFF,
    val ecoMode: Boolean = false,
    val turnOnAlarm: Alarm = Alarm(),
    val turnOffAlarm: Alarm = Alarm(),
)
