package aueb.mlp.ac

enum class Menu {
    MODE, FAN, // ...
}

enum class Mode {
    HEAT, COLD, // ...
}
// ...

data class MainActivityUiState(
    val error: String = "",
    val activeMenu: Menu = Menu.MODE,
    val temperature: Int = 25,
    val mode: Mode = Mode.HEAT,
    // ...
)
