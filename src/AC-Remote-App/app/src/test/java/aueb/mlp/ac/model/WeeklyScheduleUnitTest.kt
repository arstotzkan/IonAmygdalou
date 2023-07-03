package aueb.mlp.ac.model

import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException
import java.time.DayOfWeek

class WeeklyScheduleUnitTest {

    @Test(expected = RuntimeException::class)
    fun cannotDisableAllDays(){
        val ws = WeeklySchedule()
        ws.toggleDay(DayOfWeek.MONDAY)
        ws.toggleDay(DayOfWeek.TUESDAY)
        ws.toggleDay(DayOfWeek.WEDNESDAY)
        ws.toggleDay(DayOfWeek.THURSDAY)
        ws.toggleDay(DayOfWeek.FRIDAY)
        ws.toggleDay(DayOfWeek.SATURDAY)
        ws.toggleDay(DayOfWeek.SUNDAY)
    }

    @Test
    fun numberOfActiveDaysIsCorrect(){
        val ws1 = WeeklySchedule()
        ws1.toggleDay(DayOfWeek.MONDAY)
        ws1.toggleDay(DayOfWeek.TUESDAY)
        ws1.toggleDay(DayOfWeek.WEDNESDAY)
        ws1.toggleDay(DayOfWeek.THURSDAY)

        Assert.assertEquals(3, ws1.getNumberOfActiveDays())
    }
}
