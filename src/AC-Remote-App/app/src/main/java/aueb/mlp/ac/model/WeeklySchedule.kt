package aueb.mlp.ac.model

import java.time.DayOfWeek
import java.time.LocalDateTime

internal class WeeklySchedule {

    private val _days = Array(7) { true }
    val days get() = _days.copyOf()

    fun toggleDay(day : DayOfWeek) {
        if (getNumberOfActiveDays() == 1 && isEnabledOnDay(day))
            throw RuntimeException("A scheduler must be enabled at least once per week")
        else {
            val index = day.toInt()
            _days[index] = !_days[index]
        }
    }

    internal fun getNumberOfActiveDays() : Int = _days
        .map { day -> if (day) 1 else 0 }
        .reduce { a, b -> a + b }

    fun nextActiveDate(): LocalDateTime {
        var date = LocalDateTime.now()

        for (unused in 0..6) { //search in one week
            if (_days[date.dayOfWeek.ordinal])
                return date
            else
                date = date.plusDays(1)
        }
        // will never reach this line because at least one of the days will be true
        error("This line shouldn't have executed; at least one of the days must be true")
    }

    fun isEnabledOnDay(day: DayOfWeek) : Boolean = _days[day.toInt()]
}

private fun DayOfWeek.toInt() = when(this) {
    DayOfWeek.MONDAY -> 0
    DayOfWeek.TUESDAY -> 1
    DayOfWeek.WEDNESDAY -> 2
    DayOfWeek.THURSDAY -> 3
    DayOfWeek.FRIDAY -> 4
    DayOfWeek.SATURDAY -> 5
    DayOfWeek.SUNDAY -> 6
}
