package aueb.mlp.ac

enum class Menu {
    MODE, FAN, // ...
}

enum class Mode {
    HEAT, COLD, DRY, AUTO
}

enum class Fan {
    SILENT, NORMAL, TURBO,
}

enum class Blinds {
    HORIZONTAL, VERTICAL, FOLLOW_ME, OFF
}
// ...

data class MainActivityUiState(
    val error: String = "",
    val activeMenu: Menu = Menu.MODE,
    val acIsOn: Boolean = true,
    val temperature: Int = 25,
    val mode: Mode = Mode.HEAT,
    val fan: Fan = Fan.NORMAL,
    val blinds: Blinds = Blinds.OFF,
    val ecoMode: Boolean = false
    // ...
)
