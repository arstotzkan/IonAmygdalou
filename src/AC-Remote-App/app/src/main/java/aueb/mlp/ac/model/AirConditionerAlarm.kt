package aueb.mlp.ac.model

import java.time.LocalDateTime

interface AirConditionerAlarm {
    val on: Boolean
    val hours: Int
    val minutes: Int

    fun setTime(h: Int, m: Int)
    fun toggleOnOff()
    fun findNextAlarm(): LocalDateTime

}