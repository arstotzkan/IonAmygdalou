package aueb.mlp.ac.model

import java.time.DayOfWeek
import java.time.LocalDateTime

sealed class AlarmType {

    abstract fun findNextAlarm(hours: Int, minutes: Int): LocalDateTime

    object OneTime : AlarmType() {
        override fun findNextAlarm(hours: Int, minutes: Int): LocalDateTime {
            val now = LocalDateTime.now()
            return now.plusDays(
                if (now.hour <= hours && now.minute <= minutes) 0 else 1
            )
                .withHour(hours)
                .withMinute(minutes)
                .withSecond(0)
                .withNano(0)
        }
    }

    object Everyday : AlarmType() {

        private val schedule = WeeklySchedule()

        init {
            // turn each day on
            DayOfWeek.values().forEach {
                if (!schedule.isEnabledOnDay(it))
                    schedule.toggleDay(it)
            }
        }

        override fun findNextAlarm(hours: Int, minutes: Int): LocalDateTime {
            return this.schedule.nextActiveDate()
                .withHour(hours)
                .withMinute(minutes)
                .withSecond(0)
                .withNano(0)
        }
    }

    object Custom : AlarmType() {

        private val schedule = WeeklySchedule()

        val days = schedule.days

        fun toggleDay(day: DayOfWeek) = schedule.toggleDay(day)

        override fun findNextAlarm(hours: Int, minutes: Int): LocalDateTime {
            return this.schedule.nextActiveDate()
                .withHour(hours)
                .withMinute(minutes)
                .withSecond(0)
                .withNano(0)
        }
    }
}

class AirConditionerAlarm(
    hours: Int,
    minutes: Int
) {

    var on: Boolean = true
        private set

    var hours: Int = -1
        private set(value) {
            if (value in (0..23 ))
                field = value else throw IllegalArgumentException("Hours must be between 0 and 23");
        }

    var minutes: Int = -1
        private set(value) {
            if (value in (0..59 ))
                field = value else throw IllegalArgumentException("Minutes must be between 0 and 59");
        }

    var type: AlarmType = AlarmType.OneTime
        internal set

    init {
        this.hours = hours
        this.minutes = minutes
    }

    internal fun toggleOnOff() {
        on = !on
    }

    internal fun setTime(h: Int, m: Int): Boolean {
        try {
            hours = h
            minutes = m
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    internal fun findNextAlarm(): LocalDateTime = type.findNextAlarm(hours, minutes)
}
