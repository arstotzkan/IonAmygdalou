package aueb.mlp.ac.model

enum class ACMode {
    HEAT, COLD, DRY , AUTO,
}

enum class ACFan {
    SILENT, NORMAL, TURBO,
}

enum class ACBlinds {
    HORIZONTAL, VERTICAL, FOLLOW_ME, OFF
}
// ...

interface AirConditioner {
    val on: Boolean
    val temperature: Int
    val acMode: ACMode
    val acFan: ACFan
    val blinds: ACBlinds
    val ecoMode: Boolean
    val turnOnAlarm: AirConditionerAlarm //TODO: maybe add ability to add more than one alarm?
    val turnOffAlarm: AirConditionerAlarm //TODO: maybe add ability to add more than one alarm?

    // ...

    fun incrementTemperature(): Boolean

    fun decrementTemperature(): Boolean

    fun toggleOnOff()
    fun setMode(acMode: ACMode)
    fun setFan(acFan: ACFan)
    fun setBlinds(blinds: ACBlinds)
    fun toggleEcoMode()

    fun toggleTurnOnAlarm()
    fun toggleTurnOffAlarm()
    fun setTurnOnAlarmTime(hours: Int, minutes: Int): Boolean
    fun setTurnOffAlarmTime(hours: Int, minutes: Int): Boolean
    fun setTurnOnAlarmType(alarmType: AlarmType)
    fun setTurnOffAlarmType(alarmType: AlarmType)

    // ...
}
