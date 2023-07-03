package aueb.mlp.ac.model

import android.util.Log

class LoggingAirConditioner(override val name: String): AirConditioner {

    override var on: Boolean = true
        private set
    override var temperature: Int = 25
        private set

    override var acMode: ACMode = ACMode.HEAT
        private set

    override var acFan: ACFan = ACFan.NORMAL
        private set

    override var blinds: ACBlinds = ACBlinds.OFF
        private set

    override var ecoMode: Boolean = false
        private set

    override var turnOnAlarm: AirConditionerAlarm = AirConditionerAlarm(1, 12)
        private set

    override var turnOffAlarm: AirConditionerAlarm = AirConditionerAlarm(10, 34)
        private set
    // ...

    override fun toggleOnOff() {
        this.on = !this.on
    }
    override fun incrementTemperature(): Boolean {
        this.temperature += 1
        val moreThan30 = this.temperature > 30

        if (moreThan30)
            this.temperature = 30

        log("temperature", this.temperature)

        return !moreThan30
    }

    override fun decrementTemperature(): Boolean {
        this.temperature -= 1
        val lessThan18 = this.temperature < 18

        if (lessThan18)
            this.temperature = 18

        log("temperature", this.temperature)

        return !lessThan18
    }

    override fun setMode(acMode: ACMode) {
        this.acMode = acMode
        log("mode", this.acMode)
    }

    override fun setFan(acFan: ACFan) {
        this.acFan = acFan
        log("fan", this.acFan)
    }

    override fun setBlinds(blinds: ACBlinds) {
        this.blinds = blinds
        log("blinds", this.blinds)
    }

    override fun toggleEcoMode() {
        this.ecoMode = !this.ecoMode
        log("Eco mode", this.ecoMode)
    }

    override fun toggleTurnOnAlarm() {
        this.turnOnAlarm.toggleOnOff()
        log("TurnOnAlarmToggle", this.turnOnAlarm.on)
    }

    override fun toggleTurnOffAlarm() {
        this.turnOffAlarm.toggleOnOff()
        log("TurnOffAlarmToggle", this.turnOffAlarm.on)
    }

    override fun setTurnOnAlarmTime(hours: Int, minutes: Int): Boolean {
        val success = this.turnOnAlarm.setTime(hours, minutes)
        log("TurnOnAlarm Time", Pair(this.turnOnAlarm.hours, this.turnOnAlarm.minutes))
        return success
    }

    override fun setTurnOffAlarmTime(hours: Int, minutes: Int): Boolean {
        val success = this.turnOffAlarm.setTime(hours, minutes)
        log("TurnOffAlarm Time", Pair(this.turnOffAlarm.hours, this.turnOffAlarm.minutes))
        return success
    }

    override fun setTurnOnAlarmType(alarmType: AlarmType) {
        this.turnOnAlarm.type = alarmType
        log("TurnOnAlarm Type", this.turnOnAlarm.type)
    }

    override fun setTurnOffAlarmType(alarmType: AlarmType) {
        this.turnOffAlarm.type = alarmType
        log("TurnOffAlarm Type", this.turnOffAlarm.type)
    }

    // ...

    private fun <T> log(variable: String, value: T) {
        Log.d("LoggingAC", "Set $variable to $value")
    }
}
