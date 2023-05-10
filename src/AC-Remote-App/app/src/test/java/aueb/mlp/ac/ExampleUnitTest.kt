package aueb.mlp.ac

import aueb.mlp.ac.model.OneTimeAlarm
import aueb.mlp.ac.model.RepeatedAlarm
import aueb.mlp.ac.model.WeeklySchedule
import org.junit.Test

import org.junit.Assert.*
import java.lang.RuntimeException
import java.time.DayOfWeek
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test (expected = RuntimeException::class)
    fun cannotDisableAllDays(){
        var ws = WeeklySchedule()
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
        var ws1 = WeeklySchedule()
        ws1.toggleDay(DayOfWeek.MONDAY)
        ws1.toggleDay(DayOfWeek.TUESDAY)
        ws1.toggleDay(DayOfWeek.WEDNESDAY)
        ws1.toggleDay(DayOfWeek.THURSDAY)

        assertEquals(ws1.getNumberOfActiveDays(), 3)
    }

    @Test
    fun nextOneTimeAlarmIsCorrect1(){
        var alarm1 = OneTimeAlarm(0, 0)
        assertEquals(
            LocalDateTime.now().plusDays(1).dayOfMonth,
            alarm1.findNextAlarm().dayOfMonth
        )
    }

    @Test
    fun nextOneTimeAlarmIsCorrect2(){
        var alarm2 = OneTimeAlarm(23, 59)
        assertEquals(
            LocalDateTime.now().dayOfMonth,
            alarm2.findNextAlarm().dayOfMonth
        )
    }

    @Test (expected = RuntimeException::class)
    fun wrongInitThrowsException1(){
        var alarm1 = OneTimeAlarm(25, 0)
    }

    @Test (expected = RuntimeException::class)
    fun wrongInitThrowsException2(){
        var alarm1 = OneTimeAlarm(0, 67676)
    }

    @Test (expected = RuntimeException::class)
    fun wrongInitThrowsException3(){
        var alarm1 = RepeatedAlarm(0, 67676, WeeklySchedule())
    }

    @Test (expected = RuntimeException::class)
    fun wrongInitThrowsException4(){
        var alarm1 = RepeatedAlarm(23230, 0, WeeklySchedule())
    }

//    Need to make more generic
//    @Test
//    fun repeatWorks(){
//        var alarm1 = RepeatedAlarm(1, 0, WeeklySchedule())
//        alarm1.schedule.toggleDay(DayOfWeek.WEDNESDAY)
//        alarm1.schedule.toggleDay(DayOfWeek.THURSDAY)
//        alarm1.schedule.toggleDay(DayOfWeek.FRIDAY)
//
//        var next = alarm1.findNextAlarm()
//
//        assertEquals(next.dayOfWeek, DayOfWeek.SATURDAY)
//        assertEquals(next.dayOfMonth, 13)
//    }
}