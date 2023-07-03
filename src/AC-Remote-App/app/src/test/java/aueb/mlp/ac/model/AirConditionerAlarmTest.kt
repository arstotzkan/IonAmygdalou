package aueb.mlp.ac.model

import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException
import java.time.DayOfWeek
import java.time.LocalDateTime

class AirConditionerAlarmTest {

    @Test
    fun nextOneTimeAlarmIsCorrect0(){
        Assert.assertEquals(
            LocalDateTime.now().plusDays(1).dayOfMonth,
            AlarmType.OneTime.findNextAlarm(0, 0).dayOfMonth
        )
    }

    @Test
    fun nextOneTimeAlarmIsCorrect1(){
        Assert.assertEquals(
            LocalDateTime.now().plusDays(1).dayOfMonth,
            AlarmType.OneTime.findNextAlarm(0, 0).dayOfMonth
        )
    }

    @Test
    fun nextOneTimeAlarmIsCorrect2(){
        Assert.assertEquals(
            LocalDateTime.now().dayOfMonth,
            AlarmType.OneTime.findNextAlarm(23, 59).dayOfMonth
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun wrongInitThrowsException1(){
        var alarm1 = AirConditionerAlarm(25, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun wrongInitThrowsException2(){
        var alarm1 = AirConditionerAlarm(0, 67676)
    }

    @Test(expected = IllegalArgumentException::class)
    fun wrongInitThrowsException3(){
        var alarm1 = AirConditionerAlarm(0, 67676)
    }

    @Test(expected = IllegalArgumentException::class)
    fun wrongInitThrowsException4(){
        var alarm1 = AirConditionerAlarm(23230, 0)
    }

    @Test
    fun repeatWorks(){
        val alarm1 = AirConditionerAlarm(1, 0).also { it.type = AlarmType.Custom }
        (alarm1.type as AlarmType.Custom).toggleDay(DayOfWeek.WEDNESDAY)
        (alarm1.type as AlarmType.Custom).toggleDay(DayOfWeek.THURSDAY)
        (alarm1.type as AlarmType.Custom).toggleDay(DayOfWeek.FRIDAY)

        val next = alarm1.findNextAlarm()

        // This test is wrong. It assumes that Custom AlarmType has all days set to false by
        // default, so only WEDNESDAY, THURSDAY and FRIDAY would be toggled on. In reality, the
        // Custom AlarmType has all days set to true by default.
        Assert.assertEquals(DayOfWeek.SATURDAY, next.dayOfWeek)
        Assert.assertEquals(13, next.dayOfMonth)
    }
}
