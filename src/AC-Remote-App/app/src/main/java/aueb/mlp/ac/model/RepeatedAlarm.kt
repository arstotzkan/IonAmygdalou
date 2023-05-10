package aueb.mlp.ac.model

import java.time.DayOfWeek
import java.time.LocalDateTime

class RepeatedAlarm : AirConditionerAlarm {

    var schedule: WeeklySchedule = WeeklySchedule()
        private set

    override var on: Boolean = true
        private set

    override var hours: Int = 12
        private set

    override var minutes: Int = 23
        private set

    constructor(hours: Int, minutes: Int) {
        this.on = true
        setTime(hours, minutes)
        this.schedule = WeeklySchedule()
    }

    constructor(hours: Int, minutes: Int, schedule: WeeklySchedule) {
        this.on = true
        setTime(hours, minutes)
        this.schedule = schedule
    }

    constructor(hours: Int, minutes: Int, schedule: WeeklySchedule, isOn: Boolean) {
        this.on = isOn
        setTime(hours, minutes)
        this.schedule = schedule
    }

    override fun toggleOnOff() {
        this.on = !this.on
    }

    override fun setTime(h: Int, m: Int) {
        if (h in (0..23 )) this.hours = h else throw RuntimeException("Hours must be in (0..23)!");
        if (m in (0..59 )) this.minutes = m else throw RuntimeException("Minutes must be in (0..59)!");

    }

    fun setSchedule(s : WeeklySchedule){
        this.schedule = s
    }

    override fun findNextAlarm(): LocalDateTime {
        return this.nextActiveDate()
            .withHour(this.hours)
            .withMinute(this.minutes)
            .withSecond(0)
            .withNano(0)
    }

    private fun nextActiveDate(): LocalDateTime {
        var date = LocalDateTime.now()
        for (i in 0..6){ //search in one week
            var isActiveOnDay = when(date.dayOfWeek) { //check if the scheduler is active on selected day
                DayOfWeek.MONDAY -> this.schedule.monday
                DayOfWeek.TUESDAY -> this.schedule.tuesday
                DayOfWeek.WEDNESDAY -> this.schedule.wednesday
                DayOfWeek.THURSDAY -> this.schedule.thursday
                DayOfWeek.FRIDAY -> this.schedule.friday
                DayOfWeek.SATURDAY -> this.schedule.saturday
                DayOfWeek.SUNDAY -> this.schedule.sunday
            }

            if (isActiveOnDay) break else date = date.plusDays(1)
        }
        return date
    }
}