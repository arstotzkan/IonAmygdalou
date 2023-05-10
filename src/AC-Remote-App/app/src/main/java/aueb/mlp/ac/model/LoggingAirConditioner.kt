package aueb.mlp.ac.model

import android.util.Log

class LoggingAirConditioner: AirConditioner {

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

    override var turnOnAlarm: AirConditionerAlarm = OneTimeAlarm(1, 12)
        private set

    override var turnOffAlarm: AirConditionerAlarm = OneTimeAlarm(10, 34)
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

    override fun setTurnOnAlarm(newAlarm: AirConditionerAlarm){
        this.turnOnAlarm = newAlarm
        log("Turn on alarm", this.turnOnAlarm.hours)
    }

    override fun setTurnOffAlarm(newAlarm: AirConditionerAlarm){
        this.turnOffAlarm = newAlarm
        log("Turn off alarm", this.turnOffAlarm.hours)
    }
    // ...

    private fun <T> log(variable: String, value: T) {
        Log.d("LoggingAC", "Set $variable to $value")
    }
}
