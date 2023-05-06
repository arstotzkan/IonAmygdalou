package aueb.mlp.ac.model

enum class ACMode {
    HEAT, COLD, // ...
}

enum class ACFan {
    SILENT, NORMAL, TURBO,
}

// ...

interface AirConditioner {

    val temperature: Int
    val acMode: ACMode

     // ...

    fun incrementTemperature(): Boolean

    fun decrementTemperature(): Boolean

    fun setMode(acMode: ACMode)

    // ...
}
