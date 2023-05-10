package aueb.mlp.ac.model

import java.time.LocalDateTime

class OneTimeAlarm(hours: Int, minutes: Int) : AirConditionerAlarm(hours, minutes) {

    override fun findNextAlarm(): LocalDateTime {
        val now = LocalDateTime.now()
        return now.plusDays(
                if (now.hour <= this.hours && now.minute <= this.minutes) 0 else 1
            )
            .withHour(this.hours)
            .withMinute(this.minutes)
            .withSecond(0)
            .withNano(0)
    }
}
