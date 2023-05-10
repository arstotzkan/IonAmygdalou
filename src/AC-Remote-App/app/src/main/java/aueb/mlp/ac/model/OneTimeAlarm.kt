package aueb.mlp.ac.model

import java.time.DayOfWeek
import java.time.LocalDateTime

class OneTimeAlarm : AirConditionerAlarm {
    override var on: Boolean = true
        private set

    override var hours: Int = 12
        private set

    override var minutes: Int = 23
        private set

    constructor(hours: Int, minutes: Int) {
        this.on = true
        setTime(hours, minutes)
    }

    constructor(hours: Int, minutes: Int , isOn: Boolean) {
        this.on = isOn
        setTime(hours, minutes)
    }

    override fun toggleOnOff() {
        this.on = !this.on
    }

    override fun setTime(h: Int, m: Int) {
        if (h in (0..23 )) this.hours = h else throw RuntimeException("Hours must be in (0..23)!");
        if (m in (0..59 )) this.minutes = m else throw RuntimeException("Minutes must be in (0..59)!");
    }

    override fun findNextAlarm(): LocalDateTime {
        val now = LocalDateTime.now()
        if (now.hour <= this.hours && now.minute <= this.minutes){
            return now
                .withHour(this.hours)
                .withMinute(this.minutes)
                .withSecond(0)
                .withNano(0)
        } else {
            return now
                .plusDays(1)
                .withHour(this.hours)
                .withMinute(this.minutes)
                .withSecond(0)
                .withNano(0)
        }
    }
}