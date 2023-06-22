package aueb.mlp.ac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aueb.mlp.ac.model.LoggingAirConditioner
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme
import aueb.mlp.ac.ui.theme.component.AcButtonColors
import aueb.mlp.ac.ui.theme.component.Icon
import aueb.mlp.ac.ui.theme.component.ModeButton
import aueb.mlp.ac.ui.theme.component.PlainButtonWithSwitchAndText
import aueb.mlp.ac.ui.theme.component.PlainIconButton
import aueb.mlp.ac.ui.theme.component.PlainTextButton
import aueb.mlp.ac.ui.theme.component.StatefulButton
import aueb.mlp.ac.ui.theme.component.StatefulTextButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ACRemoteAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF74D0F8), Color(0xFFA6CCDD))
                            )
                        )
                ) {
                    MainScreen(
                        MainActivityViewModel(
                            LoggingAirConditioner()
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun MicButton(){
    Box(
        modifier = Modifier
            .background(color = Color.White, shape = CircleShape)
            // .clickable(onClick = onClick)
            .size(250.dp)
            .padding(300.dp),
    ) {
    }
}

@Composable
fun ModeMenu(
    modeCallback: (input: String) -> Unit,
    currentMode : Mode
) {
    //Text(currentMode.toString())
    // TODO: ### replace with grid https://developer.android.com/jetpack/compose/lists#lazy-grids ###
    // Maybe the buttons in this composable should always be enabled? We won't be able to access them if the AC is off
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        ModeButton(
            text = "ΘΕΡΜΑΝΣΗ" ,
            id = R.drawable.ic_mode_heat,
            alt = "mode heat",
            onClick = { modeCallback("HEAT") },
            enabled = true, // TODO: don't hardcode as true
            selected = currentMode == Mode.HEAT,
            selectedColors = AcButtonColors(
                containerColor = Color(0xFFDF6B00),
                contentColor = Color(0xFFEEEEEE), // TODO: turn to white?
            )
        )
        ModeButton(
            text = "ΨΥΞΗ" ,
            id = R.drawable.ic_mode_cold,
            alt = "mode cold",
            onClick = { modeCallback("COLD") },
            enabled = true, // TODO: don't hardcode as true
            selected = currentMode == Mode.COLD,
            selectedColors = AcButtonColors(
                containerColor = Color(0xFF80AFB9),
                contentColor = Color(0xFFEEEEEE), // TODO: turn to white?
            )
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        ModeButton(
            onClick = {modeCallback("DRY")  },
            id = R.drawable.ic_mode_humidity,
            alt = "mode humidity",
            text = "ΑΦΥΓΡΑΝΣΗ" ,
            enabled = true, // TODO: don't hardcode as true
            selected = currentMode == Mode.DRY,
            selectedColors = AcButtonColors(
                containerColor = Color(0xFF57B9D8),
                contentColor = Color(0xFFEEEEEE), // TODO: turn to white?
            )
        )
        ModeButton(
            onClick = {modeCallback("AUTO")  },
            id = R.drawable.ic_mode_auto,
            alt = "mode auto",
            text = "ΑΥΤΟΜΑΤΗ" ,
            enabled = true, // TODO: don't hardcode as true
            selected = currentMode == Mode.AUTO,
            selectedColors = AcButtonColors(
                containerColor = Color(0xFFB9B9B9),
                contentColor = Color(0xFFEEEEEE), // TODO: turn to white?
            )
        )
    }
}

@Composable
fun FanMenu(
    fanCallback: (input: String) -> Unit,
    currentFanMode: Fan
) {
    // TODO: ### enclose in a Column with proper spacing etc etc ###
    // Maybe the buttons in this composable should always be enabled? We won't be able to access them if the AC is off
    //Text(currentFanMode.toString())
    Column(
        modifier = Modifier
            .wrapContentSize()
    ){
        StatefulTextButton(
            onClick = {fanCallback("SILENT")  },
            text = "ΣΙΩΠΗΛΗ" ,
            enabled = true,
            selected = currentFanMode == Fan.SILENT
        )
        StatefulTextButton(
            onClick = {fanCallback("NORMAL")  },
            text = "ΚΑΝΟΝΙΚΗ" ,
            enabled = true,
            selected = currentFanMode == Fan.NORMAL
        )
        StatefulTextButton(
            onClick = {fanCallback("TURBO")  },
            text = "TURBO" ,
            enabled = true,
            selected = currentFanMode == Fan.TURBO
        )
    }

}

@Composable
fun TimerMenu(){
    Text("TODOOOOOOOOOOOOOO")
}
@Composable
fun BlindsMenu(
    blindCallback: (input: String) -> Unit,
    currentBlindMode: Blinds
){
    //TODO: ADD SWITCHES
    // Maybe the buttons in this composable should always be enabled? We won't be able to access them if the AC is off
    Column(
        modifier = Modifier
            .wrapContentSize()
    ){
        PlainButtonWithSwitchAndText(
            onClick = {
                if (currentBlindMode == Blinds.VERTICAL)
                    blindCallback("OFF")
                else
                    blindCallback("VERTICAL")
            },
            text = "ΠΑΝΩ-ΚΑΤΩ" ,
            enabled = true,
            switchChecked = currentBlindMode == Blinds.VERTICAL
        )
        PlainButtonWithSwitchAndText(
            onClick = {
                if (currentBlindMode == Blinds.HORIZONTAL)
                    blindCallback("OFF")
                else
                    blindCallback("HORIZONTAL")
            },
            text = "ΔΕΞΙΑ-ΑΡΙΣΤΕΡΑ" ,
            enabled = true,
            switchChecked = currentBlindMode == Blinds.HORIZONTAL
        )
    }
}

@Composable
fun ScreenMenu(
    changeMenuCallback: (input: String) -> Unit,
    uiState: MainActivityUiState

){
    Column(
        modifier = Modifier
            .wrapContentSize()
    ){
        StatefulTextButton(
            onClick = {
                changeMenuCallback("MODE")
            },
            text = "ΛΕΙΤΟΥΡΓΙΑ" ,
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.MODE,
        )
        StatefulTextButton(
            onClick = {
                changeMenuCallback("FAN")
            },
            text = "ΕΝΤΑΣΗ" ,
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.FAN,
        )
        StatefulTextButton(
            onClick = {
                changeMenuCallback("TIMER")
            },
            text = "ΧΡΟΝΟΔΙΑΚΟΠΤΗΣ",
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.TIMER,
        )
        StatefulTextButton(
            onClick = {
                changeMenuCallback("BLINDS")
            },
            text = "ΠΕΡΣΙΔΕΣ",
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.BLINDS,
        )
    }
}


@Composable
fun EcoButton(
    ecoToggleCallback: () -> Unit,
    uiState: MainActivityUiState,
){
    StatefulButton(
        onClick = { ecoToggleCallback() },
        enabled = uiState.acIsOn,
        selected = uiState.ecoMode,
        selectedColors = AcButtonColors(
            containerColor = Color(0xFF8CC640),
            contentColor = Color(0xFF000000),
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Icon(
                id = R.drawable.ic_eco,
                alt = "eco mode",
            )
            Text("ECO")
        }
    }
}


@Composable
fun ChangeDeviceButton(
    changeDeviceCallback: () -> Unit
){
    PlainTextButton(
        text = "ΑΛΛΑΞΕ ΣΥΣΚΕΥΗ",
        onClick = changeDeviceCallback,
        enabled = true, //panta tha prepei na mporeis na allakseis syskeuh
    )
}
@Composable
fun OffButton(
    onSwitchOnOff: () -> Unit,
){
    Box(
        modifier = Modifier
            .background(color = Color.Red, shape = CircleShape)
            .size(120.dp)
            .padding(200.dp),
    ) {
        PlainIconButton(id =R.drawable.ic_placeholder, alt ="off", onClick = { onSwitchOnOff() },
            enabled = true,
        )
    }
}


@Composable
fun ACDetails() {

    Box(
        modifier = Modifier
            .size(size = 1000.dp)
            .padding(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFEF9D),
                        Color(0xFFDCA96C)
                    )
                )
            ),
        contentAlignment = Alignment.Center,
    ) {

    }
}

@Composable
fun ChangeTempButtons(
    onIncrementTemperatureFunc: () -> Unit,
    onDecrementTemperatureFunc: () -> Unit,
    uiState: MainActivityUiState
){
    PlainIconButton(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp),
        id = R.drawable.ic_plus,
        alt = "Increment Temperature",
        onClick = onIncrementTemperatureFunc,
        enabled = uiState.acIsOn,
    )
    PlainIconButton(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp),
        id = R.drawable.ic_minus,
        alt = "Decrement Temperature",
        onClick = onDecrementTemperatureFunc,
        enabled = uiState.acIsOn,
    )
}

@Composable
fun MainScreen(
    mainActivityViewModel: MainActivityViewModel
) {
    MainScreenContent(
        uiState = mainActivityViewModel.uiState,
        onSwitchOnOff = {mainActivityViewModel.toggleOnOff()},
        onIncrementTemperature = { mainActivityViewModel.incrementTemperature() },
        onDecrementTemperature = { mainActivityViewModel.decrementTemperature() },
        onModeChanged = { mode: String -> mainActivityViewModel.setMode(mode) },
        onFanChanged = { mode: String -> mainActivityViewModel.setFan(mode) },
        changeMenu = {menu: String -> mainActivityViewModel.changeMenu(menu)},
        onBlindsChanged = { mode: String -> mainActivityViewModel.setBlinds(mode) },
        onEcoModeChanged = { mainActivityViewModel.toggleEcoMode() },
    )
}


@Composable
fun MainScreenContent(
    uiState: MainActivityUiState,
    onSwitchOnOff: () -> Unit,
    onIncrementTemperature: () -> Unit,
    onDecrementTemperature: () -> Unit,
    onModeChanged: (String) -> Unit,
    onFanChanged: (String) -> Unit,
    changeMenu:(String)-> Unit,
    onBlindsChanged: (String) -> Unit,
    onEcoModeChanged: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            //First row with two columns
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f)

                ) {//AC info column
                    ACDetails()
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .wrapContentSize()
                        .clip(RoundedCornerShape(20.dp)),

                    ) { //Increment buttons column

                    ChangeTempButtons(
                        onIncrementTemperatureFunc = onIncrementTemperature,
                        onDecrementTemperatureFunc = onDecrementTemperature,
                        uiState = uiState
                    )
                }
            }
            // Second row with three columns
            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) { //Menu column
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    ScreenMenu(changeMenu, uiState)

                }
                Column(
                    verticalArrangement  = Arrangement.Center,
                    horizontalAlignment= Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)

                ) { //Main content column Idk how to make it

                    when(uiState.activeMenu ){
                        Menu.MODE -> ModeMenu(onModeChanged, uiState.mode)
                        Menu.FAN -> FanMenu(onFanChanged, uiState.fan)
                        Menu.TIMER -> TimerMenu()
                        Menu.BLINDS -> BlindsMenu(onBlindsChanged, uiState.blinds)
                        //Maybe selecting/adding an AC needs its own activity??
                        else -> MicButton()
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)

                ) { //Eco mode
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        EcoButton(onEcoModeChanged, uiState)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        ChangeDeviceButton({})
                    }
                    Column(
                        horizontalAlignment  = Alignment.End,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) { OffButton(onSwitchOnOff={/*close AC I guess*/})
                    }

                }
            }
        }


    }


//    Surface(
//        color = when(uiState.mode) {
//            Mode.HEAT -> Color(0xBBDF6B00)
//            Mode.COLD -> Color(0xBB80AFB9)
//            Mode.DRY -> Color(0xBB00BBCC)
//            Mode.AUTO -> Color(0xBBAAAAAA)
//        },
//        //malakisthka ligo sta teleutaia dyo
//        border = when(uiState.ecoMode){
//            true -> BorderStroke(10.dp, Color(0xBB00AA11))
//            false -> BorderStroke(0.dp, Color(0xBB00AA11))
//        },
//        contentColor = when(uiState.acIsOn){
//            true -> Color(0xBB222222)
//            false -> Color(0xBBEEEEEE)
//        }
//    ) {
//        Column(
//            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize(),
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ) {
//                LargeText(
//                    text = "Temperature: " + uiState.temperature.toString(),
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ) {
//                LargeText(
//                    text = "Mode: " + uiState.mode.toString(),
//                )
//
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                LargeText(
//                    text = "Fan: " + uiState.fan.toString(),
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                LargeText(
//                    text = "Blinds: " + uiState.blinds.toString(),
//                )
//
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                LargeText(
//                    text = "Eco mode is on: " + uiState.ecoMode.toString(),
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                LargeText(
//                    text = "AC is on: " + uiState.acIsOn.toString(),
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                MediumText(
//                    text = "Temp Control",
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ) {
//                ButtonWithIcon(
//                    id = R.drawable.ic_placeholder,
//                    alt = "Decrement Temperature",
//                    onClick = onDecrementTemperature,
//                    enabled = false,
//                )
//                ButtonWithIcon(
//                    id = R.drawable.ic_minus,
//                    alt = "Decrement Temperature",
//                    onClick = onDecrementTemperature,
//                )
//                ButtonWithIcon(
//                    id = R.drawable.ic_plus,
//                    alt = "Increment Temperature",
//                    onClick = { onIncrementTemperature() },
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                MediumText(
//                    text = "AC Modes",
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier
//                    .fillMaxWidth(),
//            ) {
//                ButtonWithMediumText(
//                    text = "HEAT",
//                    onClick = { onModeChanged("HEAT") },
//                )
//                ButtonWithMediumText(
//                    text = "COLD",
//                    onClick = { onModeChanged("COLD") },
//                )
//                ButtonWithMediumText(
//                    text = "DRY",
//                    onClick = { onModeChanged("DRY") },
//                )
//                ButtonWithMediumText(
//                    text = "AUTO",
//                    onClick = { onModeChanged("AUTO") },
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                MediumText(
//                    text = "Fans",
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier
//                    .fillMaxWidth(),
//            ) {
//                ButtonWithMediumText(
//                    text = "SILENT",
//                    onClick = { onFanChanged("SILENT") },
//                )
//                ButtonWithMediumText(
//                    text = "NORMAL",
//                    onClick = { onFanChanged("NORMAL") },
//                )
//                ButtonWithMediumText(
//                    text = "TURBO",
//                    onClick = { onFanChanged("TURBO") },
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                MediumText(
//                    text = "Blinds",
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier
//                    .fillMaxWidth(),
//            ) {
//                ButtonWithMediumText(
//                    text = "HORIZONTAL",
//                    onClick = { onBlindsChanged("HORIZONTAL") },
//                )
//                ButtonWithMediumText(
//                    text = "VERTICAL",
//                    onClick = { onBlindsChanged("VERTICAL") },
//                )
//                ButtonWithMediumText(
//                    text = "FOLLOW ME",
//                    onClick = { onBlindsChanged("FOLLOW ME") },
//                )
//                ButtonWithMediumText(
//                    text = "OFF",
//                    onClick = { onBlindsChanged("OFF") },
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .wrapContentSize()
//            ){
//                MediumText(
//                    text = "Misc",
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier
//                    .fillMaxWidth(),
//            ) {
//                ButtonWithMediumText(
//                    text = "TOGGLE ECO MODE",
//                    onClick = { onEcoModeChanged() },
//                )
//
//                ButtonWithMediumText(
//                    text = "SWITCH AC ON/OFF",
//                    onClick = { onSwitchOnOff() },
//                )
//            }
//            if (uiState.error != "") {
//                ErrorLabel(
//                    text = uiState.error,
//                )
//            }
//        }
//    }
}

