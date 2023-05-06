package aueb.mlp.ac.model

import android.util.Log

class LoggingAirConditioner: AirConditioner {

    override var temperature: Int = 25
        private set

    override var acMode: ACMode = ACMode.HEAT
        private set

    // ...

    override fun incrementTemperature(): Boolean {
        temperature += 1
        val moreThan30 = temperature > 30

        if (moreThan30)
            temperature = 30

        log("temperature", temperature)

        return !moreThan30
    }

    override fun decrementTemperature(): Boolean {
        temperature -= 1
        val lessThan18 = temperature < 18

        if (lessThan18)
            temperature = 18

        log("temperature", temperature)

        return !lessThan18
    }

    override fun setMode(acMode: ACMode) {
        this.acMode = acMode
        log("mode", acMode)
    }

    // ...

    private fun <T> log(variable: String, value: T) {
        Log.d("LoggingAC", "Set $variable to $value")
    }
}
