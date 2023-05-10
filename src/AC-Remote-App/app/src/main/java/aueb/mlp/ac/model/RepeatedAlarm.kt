package aueb.mlp.ac.model

import java.time.LocalDateTime

class RepeatedAlarm(hours: Int, minutes: Int) : AirConditionerAlarm(hours,  minutes) {

    var schedule: WeeklySchedule = WeeklySchedule()

    constructor(hours: Int, minutes: Int, schedule: WeeklySchedule) : this(hours, minutes) {
        this.schedule = schedule
    }

    override fun findNextAlarm(): LocalDateTime {
        return this.schedule.nextActiveDate()
            .withHour(this.hours)
            .withMinute(this.minutes)
            .withSecond(0)
            .withNano(0)
    }
}
