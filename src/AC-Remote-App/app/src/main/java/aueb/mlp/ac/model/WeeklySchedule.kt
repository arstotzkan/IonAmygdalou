package aueb.mlp.ac.model

import java.time.DayOfWeek
import java.time.LocalDateTime
import kotlin.reflect.KProperty1

class WeeklySchedule {
    var monday: Boolean = true
        private set

    var tuesday: Boolean = true
        private set

    var wednesday: Boolean = true
        private set

    var thursday: Boolean = true
        private set

    var friday: Boolean = true
        private set

    var saturday: Boolean = true
        private set

    var sunday: Boolean = true
        private set

    constructor(){
        this.monday = true;
        this.tuesday = true;
        this.wednesday = true;
        this.thursday = true;
        this.friday = true;
        this.saturday = true;
        this.sunday = true;
    }


    fun toggleDay(day : DayOfWeek){
        if (getNumberOfActiveDays() == 1 && isEnabledOnDay(day))
            throw RuntimeException("A scheduler must be enabled at least once per week")
        else
            when(day) { //check if the scheduler is active on selected day
                DayOfWeek.MONDAY -> this.monday = !this.monday
                DayOfWeek.TUESDAY -> this.tuesday = !this.tuesday
                DayOfWeek.WEDNESDAY -> this.wednesday = !this.wednesday
                DayOfWeek.THURSDAY -> this.thursday  = !this.thursday
                DayOfWeek.FRIDAY -> this.friday  = !this.friday
                DayOfWeek.SATURDAY -> this.saturday  = !this.saturday
                DayOfWeek.SUNDAY -> this.sunday  = !this.sunday
            }
    }

    private fun isEnabledOnDay(day: DayOfWeek) : Boolean{
        val enabled = when(day) { //check if the scheduler is active on selected day
            DayOfWeek.MONDAY -> this.monday
            DayOfWeek.TUESDAY -> this.tuesday
            DayOfWeek.WEDNESDAY -> this.wednesday
            DayOfWeek.THURSDAY -> this.thursday
            DayOfWeek.FRIDAY -> this.friday
            DayOfWeek.SATURDAY -> this.saturday
            DayOfWeek.SUNDAY -> this.sunday
        }

        return enabled
    }

    fun getNumberOfActiveDays() : Int{
        return this.monday.compareTo(false) +
                this.tuesday.compareTo(false) +
                this.wednesday.compareTo(false) +
                this.thursday.compareTo(false) +
                this.friday.compareTo(false) +
                this.saturday.compareTo(false) +
                this.sunday.compareTo(false)
    }
}