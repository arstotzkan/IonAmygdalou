package aueb.mlp.ac.model

import java.time.LocalDateTime

abstract class AirConditionerAlarm(
    hours: Int,
    minutes: Int
) {

    var on: Boolean = true
        private set
    var hours: Int = -1
        private set
    var minutes: Int = -1
        private set

    init {
        setTime(hours, minutes)
    }

    fun setTime(h: Int, m: Int) {
        if (h in (0..23 ))
            this.hours = h else throw RuntimeException("Hours must be in (0..23)!");
        if (m in (0..59 ))
            this.minutes = m else throw RuntimeException("Minutes must be in (0..59)!");
    }

    fun toggleOnOff() {
        on = !on
    }

    abstract fun findNextAlarm(): LocalDateTime
}