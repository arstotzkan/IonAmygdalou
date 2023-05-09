package aueb.mlp.ac.model

enum class ACMode {
    HEAT, COLD, DRY , AUTO,
}

enum class ACFan {
    SILENT, NORMAL, TURBO,
}

enum class ACBlinds {
    HORIZONTAL, VERTICAL, FOLLOW_ME, OFF
}
// ...

interface AirConditioner {
    val on: Boolean
    val temperature: Int
    val acMode: ACMode
    val acFan: ACFan
    val blinds: ACBlinds
    val ecoMode: Boolean
     // ...

    fun incrementTemperature(): Boolean

    fun decrementTemperature(): Boolean

    fun toggleOnOff()
    fun setMode(acMode: ACMode)
    fun setFan(acFan: ACFan)
    fun setBlinds(blinds: ACBlinds)
    fun toggleEcoMode()

    // ...
}
