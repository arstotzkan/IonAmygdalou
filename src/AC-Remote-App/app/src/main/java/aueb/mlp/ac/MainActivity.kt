package aueb.mlp.ac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aueb.mlp.ac.model.LoggingAirConditioner
import aueb.mlp.ac.ui.theme.component.ButtonWithIcon
import aueb.mlp.ac.ui.theme.component.ErrorLabel
import aueb.mlp.ac.ui.theme.component.LargeText
import aueb.mlp.ac.ui.theme.component.ButtonWithMediumText
import aueb.mlp.ac.ui.theme.component.ButtonWithText
import aueb.mlp.ac.ui.theme.component.MediumText
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme
import aueb.mlp.ac.ui.theme.component.ChoiceButtonWithText

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
    Text(currentMode.toString())
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        ChoiceButtonWithText(
            text = "ΘΕΡΜΑΝΣΗ" ,
            onClick = { modeCallback("HEAT") },
            selected = "HEAT" == currentMode.toString()
        )
        ChoiceButtonWithText(
            text = "ΨΥΞΗ" ,
            onClick = { modeCallback("COLD") },
            selected = "COLD" == currentMode.toString()
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        ChoiceButtonWithText(
            onClick = {modeCallback("DRY")  },
            text = "ΑΦΥΓΡΑΝΣΗ" ,
            selected = "DRY" == currentMode.toString()
        )
        ChoiceButtonWithText(
            onClick = {modeCallback("AUTO")  },
            text = "ΑΥΤΟΜΑΤΗ" ,
            selected = "AUTO" == currentMode.toString()
        )
    }
}


@Composable
fun FanMenu(
    fanCallback: (input: String) -> Unit,
    currentFanMode: Fan
) {
    Text(currentFanMode.toString())
    ChoiceButtonWithText(
        onClick = {fanCallback("SILENT")  },
        text = "ΣΙΩΠΗΛΗ" ,
        selected = "SILENT" == currentFanMode.toString()
    )
    ChoiceButtonWithText(
        onClick = {fanCallback("NORMAL")  },
        text = "ΚΑΝΟΝΙΚΗ" ,
        selected = "NORMAL" == currentFanMode.toString()
    )
    ChoiceButtonWithText(
        onClick = {fanCallback("TURBO")  },
        text = "TURBO" ,
        selected = "TURBO" == currentFanMode.toString()
    )
}

@Composable
fun ScreenMenu(
    changeMenuCallback: (input: String) -> Unit,
    currentMenu: Menu
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .wrapContentSize()

    ) {
        ChoiceButtonWithText(
            //TODO: ADD FUNCTION THAT CHANGES THE MENU...
            onClick = { changeMenuCallback("MODE") },
            text = "ΛΕΙΤΟΥΡΓΙΑ" ,
            selected = "MODE" == currentMenu.toString()
        )

    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        ChoiceButtonWithText(
            onClick = {  changeMenuCallback("FAN") },
            text = "ΕΝΤΑΣΗ" ,
            selected = "FAN" == currentMenu.toString()
        )

    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        ChoiceButtonWithText(
            onClick = { },
            text = "ΧΡΟΝΟΔΙΑΚΟΠΤΗΣ" ,
        )

    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        ChoiceButtonWithText(
            onClick = { },
            text = "ΠΕΡΣΙΔΕΣ" ,
        )

    }
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
        ButtonWithIcon(id =R.drawable.ic_placeholder, alt ="off", onClick = { onSwitchOnOff() })
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

                    ButtonWithIcon(
                        modifier = Modifier
                            .size(width = 150.dp, height = 150.dp),
                        id = R.drawable.ic_plus,
                        alt = "Increment Temperature",
                        onClick = { onIncrementTemperature() },
                    )
                    ButtonWithIcon(
                        modifier = Modifier
                            .size(width = 150.dp, height = 150.dp),
                        id = R.drawable.ic_minus,
                        alt = "Decrement Temperature",
                        onClick = onDecrementTemperature,
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
                    ScreenMenu(changeMenu, uiState.activeMenu)

                }
                Column(
                    verticalArrangement  = Arrangement.Center,
                    horizontalAlignment= Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)

                ) { //Main content column Idk how to make it
                    if (uiState.activeMenu== Menu.MODE)
                        ModeMenu(onModeChanged, uiState.mode)
                    if(uiState.activeMenu==Menu.FAN)
                        FanMenu(onFanChanged, uiState.fan)
                    if (uiState.activeMenu== Menu.MAIN)
                        MicButton()

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
                        ButtonWithMediumText(
                    text = "ECO",
                    onClick = { onEcoModeChanged() },
                )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        ButtonWithMediumText(
                            text = "ΑΛΛΑΞΕ ΣΥΣΚΕΥΗ",
                            onClick = {  },
                        )
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

