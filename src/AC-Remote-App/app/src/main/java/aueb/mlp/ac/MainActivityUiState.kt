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

data class Time(
    val hours: Int,
    val minutes: Int,
)

sealed class AlarmRepeat {
    object OneTimeRepeat : AlarmRepeat()

    object EverydayRepeat : AlarmRepeat()

    class CustomRepeat(val days: List<Boolean>) : AlarmRepeat()
}

data class MainActivityUiState(
    val error: String = "",
    val activeMenu: Menu = Menu.MODE,
    val acIsOn: Boolean = true,
    val temperature: Int = 25,
    val mode: Mode = Mode.HEAT,
    val fan: Fan = Fan.NORMAL,
    val blinds: Blinds = Blinds.OFF,
    val ecoMode: Boolean = false,
    val turnOnAlarmState: Boolean = false,
    val turnOffAlarmState: Boolean = false,
    val turnOnAlarmTime: Time = Time(0, 0),
    val turnOffAlarmTime: Time = Time(0, 0),
    val turnOnAlarmRepeat: AlarmRepeat = AlarmRepeat.OneTimeRepeat,
    val turnOffAlarmRepeat: AlarmRepeat = AlarmRepeat.OneTimeRepeat,
)
