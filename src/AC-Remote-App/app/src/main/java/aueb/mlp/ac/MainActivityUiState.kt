package aueb.mlp.ac

enum class Menu {
    MODE, FAN, MAIN, BLINDS, TIMER, TIMER_ON, TIMER_OFF, CHANGE, ADDAC
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
) {
    override fun toString(): String = "%02d:%02d".format(hours, minutes)
}

sealed class AlarmRepeat {
    object OneTimeRepeat : AlarmRepeat() {
        override fun toString(): String = "ΜΙΑ ΦΟΡΑ"
    }

    object EverydayRepeat : AlarmRepeat() {
        override fun toString(): String = "ΚΑΘΕ ΜΕΡΑ"
    }

    class CustomRepeat(val days: List<Boolean>) : AlarmRepeat() {
        override fun toString(): String =
            listOf("Δε", "Τρ", "Τε", "Πε", "Πα", "Σα", "Κυ")
                .filterIndexed { i, _ -> days[i] }
                .joinToString(separator = "/")
    }
}

data class Alarm(
    val state: Boolean = false,
    val time: Time = Time(0, 0),
    val repeat: AlarmRepeat = AlarmRepeat.OneTimeRepeat,
)

data class MainActivityUiState(
    val error: String = "",
    val acName: String = "AC 0",
    val activeMenu: Menu = Menu.MAIN,
    val acIsOn: Boolean = true,
    val temperature: Int = 25,
    val mode: Mode = Mode.HEAT,
    val fan: Fan = Fan.NORMAL,
    val blinds: Blinds = Blinds.OFF,
    val ecoMode: Boolean = false,
    // Keep both Alarm and alarm parts: change
    // parts and always rebuild whole Alarm
    // see MainActivityViewModel#updateAlarmsFromParts
    val turnOnAlarm: Alarm = Alarm(),
    val turnOffAlarm: Alarm = Alarm(),
    val turnOnAlarmState: Boolean = true,
    val turnOffAlarmState: Boolean = false,
    val turnOnAlarmTime: Time = Time(0, 0),
    val turnOffAlarmTime: Time = Time(0, 0),
    val turnOnAlarmRepeat: AlarmRepeat = AlarmRepeat.OneTimeRepeat,
    val turnOffAlarmRepeat: AlarmRepeat = AlarmRepeat.OneTimeRepeat,
)
