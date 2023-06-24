package aueb.mlp.ac

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import aueb.mlp.ac.model.LoggingAirConditioner
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme
import aueb.mlp.ac.ui.theme.Red40
import aueb.mlp.ac.ui.theme.ACShapes
import aueb.mlp.ac.ui.theme.component.AcButtonColors
import aueb.mlp.ac.ui.theme.component.Icon
import aueb.mlp.ac.ui.theme.component.ModeButton
import aueb.mlp.ac.ui.theme.component.PlainButtonWithSwitchAndText
import aueb.mlp.ac.ui.theme.component.PlainIconButton
import aueb.mlp.ac.ui.theme.component.PlainTextButton
import aueb.mlp.ac.ui.theme.component.StatefulButton
import aueb.mlp.ac.ui.theme.component.StatefulTextButton
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val viewModel = MainActivityViewModel(LoggingAirConditioner())

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
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MicButton(){
    //I am not deleting this because of the code in
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
                containerColor = Color(0xFFDF6B00).copy(alpha= 0.7f),
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
                containerColor = Color(0xFF80AFB9).copy(alpha= 0.75f),
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
                containerColor = Color(0xFF57B9D8).copy(alpha= 0.75f),
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
fun TimerMenu(
    currentMenu: Menu,
    changeMenuCallback: (input: String) -> Unit,
    turnOnAlarm: Alarm,
    turnOffAlarm: Alarm,
    onTurnOnAlarmStateChanged: (Boolean) -> Unit,
    onTurnOffAlarmStateChanged: (Boolean) -> Unit,
    onTurnOnAlarmTimeChanged: (Time) -> Unit,
    onTurnOffAlarmTimeChanged: (Time) -> Unit,
    onTurnOnAlarmRepeatChanged: (AlarmRepeat) -> Unit,
    onTurnOffAlarmRepeatChanged: (AlarmRepeat) -> Unit,
    onToggleTurnOnAlarmDay: (DayOfWeek) -> Unit,
    onToggleTurnOffAlarmDay: (DayOfWeek) -> Unit,
) {
    when (currentMenu) {
        Menu.TIMER -> BothAlarmsMenu(
            turnOnAlarm = turnOnAlarm,
            turnOffAlarm = turnOffAlarm,
            onTurnOnAlarmStateChanged = onTurnOnAlarmStateChanged,
            onTurnOffAlarmStateChanged = onTurnOffAlarmStateChanged,
            changeMenuCallback = changeMenuCallback,
        )

        Menu.TIMER_ON -> SingleAlarmMenu(
            alarm = turnOnAlarm,
            onAlarmTimeChanged = onTurnOnAlarmTimeChanged,
            onAlarmRepeatChanged = onTurnOnAlarmRepeatChanged,
            onToggleAlarmDay = onToggleTurnOnAlarmDay,
            onNavigateBack = { changeMenuCallback("TIMER") },
        )

        Menu.TIMER_OFF -> SingleAlarmMenu(
            alarm = turnOffAlarm,
            onAlarmTimeChanged = onTurnOffAlarmTimeChanged,
            onAlarmRepeatChanged = onTurnOffAlarmRepeatChanged,
            onToggleAlarmDay = onToggleTurnOffAlarmDay,
            onNavigateBack = { changeMenuCallback("TIMER") },
        )

        else -> error("this was not supposed to happen")
    }
}

@Composable
private fun BothAlarmsMenu(
    turnOnAlarm: Alarm,
    turnOffAlarm: Alarm,
    onTurnOnAlarmStateChanged: (Boolean) -> Unit,
    onTurnOffAlarmStateChanged: (Boolean) -> Unit,
    changeMenuCallback: (input: String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        AlarmSurface(
            alarm = turnOnAlarm,
            isTurnOnAlarm = true,
            onAlarmStateChanged = onTurnOnAlarmStateChanged,
            onNavigateToSingleAlarm = { changeMenuCallback("TIMER_ON") }
        )
        AlarmSurface(
            alarm = turnOffAlarm,
            isTurnOnAlarm = false,
            onAlarmStateChanged = onTurnOffAlarmStateChanged,
            onNavigateToSingleAlarm = { changeMenuCallback("TIMER_OFF") }
        )
    }
}

@Composable
private fun AlarmSurface(
    alarm: Alarm,
    isTurnOnAlarm: Boolean,
    onAlarmStateChanged: (Boolean) -> Unit,
    onNavigateToSingleAlarm: () -> Unit,
) {
    Surface(
        shape = ACShapes.large,
        color = Color(0xFFFFFFFF),
        modifier = Modifier
            .wrapContentSize()
            .clickable { onNavigateToSingleAlarm() }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .padding(16.dp)
        ) {
            Text(if (isTurnOnAlarm) "ΑΝΟΙΞΕ" else "ΚΛΕΙΣΕ")
            Text(alarm.time.toString())
            Text(alarm.repeat.toString())
            Switch(
                checked = alarm.state,
                onCheckedChange = onAlarmStateChanged,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF0085FF),
                    checkedTrackColor = Color(0xFFD9D9D9),
                    checkedBorderColor = Color(0xFF000000),
                    uncheckedThumbColor = Color(0xFFBFBFBF),
                    uncheckedTrackColor = Color(0x80D9D9D9),
                    uncheckedBorderColor = Color(0xFF000000),
                )
            )
        }
    }
}

@Composable
private fun SingleAlarmMenu(
    alarm: Alarm,
    onAlarmTimeChanged: (Time) -> Unit,
    onAlarmRepeatChanged: (AlarmRepeat) -> Unit,
    onToggleAlarmDay: (DayOfWeek) -> Unit,
    onNavigateBack: () -> Unit,
) {

    val context = LocalContext.current
    var changeRepeatPopup by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .wrapContentSize()
    ) {
        PlainTextButton(
            text = alarm.time.toString(),
            onClick = {
                TimePickerDialog(
                    context,
                    { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                        onAlarmTimeChanged(Time(selectedHour, selectedMinute))
                    },
                    alarm.time.hours,
                    alarm.time.minutes,
                    true,
                ).show()
            },
            enabled = true,
        )
        PlainTextButton(
            text = alarm.repeat.toString(),
            onClick = { changeRepeatPopup = true },
            enabled = true,
        )
        PlainTextButton(
            text = "ΠΙΣΩ",
            onClick = onNavigateBack,
            enabled = true
        )
        if (changeRepeatPopup) {
            ChangeRepeatPopup(
                onDismissRequest = { changeRepeatPopup = false },
                alarmRepeat = alarm.repeat,
                onAlarmRepeatChanged = onAlarmRepeatChanged,
                onToggleAlarmDay = onToggleAlarmDay,
            )
        }
    }
}

@Composable
private fun DayButton(
    day: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    StatefulTextButton(
        text = day,
        onClick = onClick,
        enabled = true,
        selected = selected,
        selectedColors = AcButtonColors(
            containerColor = Color(0xFFFFB800),
            contentColor = Color(0xFF000000),
        ),
        modifier = Modifier.wrapContentSize(),
    )
}

@Composable
private fun ChangeRepeatPopup(
    onDismissRequest: () -> Unit,
    alarmRepeat: AlarmRepeat,
    onAlarmRepeatChanged: (AlarmRepeat) -> Unit,
    onToggleAlarmDay: (DayOfWeek) -> Unit,
) {
    Popup(
        onDismissRequest = onDismissRequest,
        alignment = Alignment.Center,
    ) {
        Surface(
            shape = ACShapes.medium,
            color = Color(0xFFFFFFFF),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    StatefulTextButton(
                        text = "ΜΙΑ ΦΟΡΑ",
                        onClick = { onAlarmRepeatChanged(AlarmRepeat.OneTimeRepeat) },
                        enabled = true,
                        selected = alarmRepeat is AlarmRepeat.OneTimeRepeat,
                    )
                    StatefulTextButton(
                        text = "ΚΑΘΕ ΜΕΡΑ",
                        onClick = { onAlarmRepeatChanged(AlarmRepeat.EverydayRepeat) },
                        enabled = true,
                        selected = alarmRepeat is AlarmRepeat.EverydayRepeat,
                    )
                    StatefulTextButton(
                        text = "ΠΡΟΧΩΡΗΜΕΝΕΣ",
                        onClick = { onAlarmRepeatChanged(AlarmRepeat.CustomRepeat(listOf())) },
                        enabled = true,
                        selected = alarmRepeat is AlarmRepeat.CustomRepeat,
                    )
                }
                if (alarmRepeat is AlarmRepeat.CustomRepeat) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                    ) {
                        DayButton("Δε", alarmRepeat.days[0]) { onToggleAlarmDay(DayOfWeek.MONDAY) }
                        DayButton("Τρ", alarmRepeat.days[1]) { onToggleAlarmDay(DayOfWeek.TUESDAY) }
                        DayButton("Τε", alarmRepeat.days[2]) { onToggleAlarmDay(DayOfWeek.WEDNESDAY) }
                        DayButton("Πε", alarmRepeat.days[3]) { onToggleAlarmDay(DayOfWeek.THURSDAY) }
                        DayButton("Πα", alarmRepeat.days[4]) { onToggleAlarmDay(DayOfWeek.FRIDAY) }
                        DayButton("Σα", alarmRepeat.days[5]) { onToggleAlarmDay(DayOfWeek.SATURDAY) }
                        DayButton("Κυ", alarmRepeat.days[6]) { onToggleAlarmDay(DayOfWeek.SUNDAY) }
                    }
                }
                PlainTextButton(
                    text = "ΟΚ",
                    onClick = onDismissRequest,
                    enabled = true,
                )
            }
        }
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
            .background(color = Red40, shape = CircleShape)
            .size(120.dp)
            .padding(16.dp)
    ) { // HAHAHA Managed to add an icon but it has a white bg and idk what to  do :/
        PlainIconButton(id =R.drawable.ic_power_off, alt ="off", onClick = { onSwitchOnOff() },
            enabled = true,
        )
    }
}


@Composable
fun ACDetails(

    uiState: MainActivityUiState
) {


    val backgroundColor = if (uiState.acIsOn) {
        when (uiState.mode) {
            Mode.HEAT -> listOf(Color(0xFFFFEF9D), Color(0xFFE0B178))
            Mode.DRY -> listOf(Color(0xFFD0F1FF), Color(0xFF669ED3))
            Mode.COLD -> listOf(Color(0xFFD0DCEB), Color(0xFF80A6C9))
            Mode.AUTO -> listOf(Color(0xFFE8E8F7), Color(0xFF89D8DD))
        }
    } else {
        listOf(Color(0xFF575CDA), Color(0xFF5765E3))
    }

    Box(
        modifier = Modifier
            .size(size = 1000.dp)
            .padding(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColor
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(height=380.dp, width=380.dp)

        ) {

            if (uiState.acIsOn){
                when(uiState.mode){
                    Mode.HEAT->Icon(
                        modifier = Modifier
                            .size(380.dp),
                        id = R.drawable.ic_sun,
                        alt = "Heat Mode",
                    )
                    Mode.COLD->Icon(
                        modifier = Modifier
                            .size(350.dp)
                            .padding(start= 36.dp),
                        id = R.drawable.ic_snow,
                        alt = "Cold Mode",
                    )
                    Mode.DRY->Icon(
                        modifier = Modifier
                            .size(380.dp)
                            .padding(end= 48.dp),
                        id = R.drawable.ic_humid,
                        alt = "Dry Mode",
                    )
                    Mode.AUTO->Icon(
                        modifier = Modifier
                            .size(380.dp),
                        id = R.drawable.ic_auto,
                        alt = "Auto Mode",
                    )
                }
            } else {
                Icon(
                modifier = Modifier
                    .size(380.dp),
                id = R.drawable.ic_moon,
                alt = "Sleep Mode",
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            ) {
                if(uiState.acIsOn){
                Icon(
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp),
                    id = R.drawable.ic_fan,
                    alt = "Increment Temperature",
                ) //PLease do not execute me publically for this I had no other idea
                    when (uiState.fan){
                        Fan.SILENT-> repeat(3) { index ->
                            Icon(
                                id = R.drawable.ic_fan_square,
                                alt = "Normal Mode",
                                modifier = Modifier
                                    .size(width = 30.dp, height = 30.dp)
                                    .alpha(if (index==0) 1f else 0.5f)
                                    .padding(top = 8.dp)
                            )
                        }
                        Fan.NORMAL -> repeat(3) { index ->
                            Icon(
                                id = R.drawable.ic_fan_square,
                                alt = "Normal Mode",
                                modifier = Modifier
                                    .size(width = 30.dp, height = 30.dp)
                                    .alpha(if (index==2) 0.5f else 1f)
                                    .padding(top = 8.dp)
                            )
                        }
                        Fan.TURBO-> repeat(3) {
                            Icon(
                                modifier = Modifier
                                    .size(width = 30.dp, height = 30.dp)
                                    .padding(top = 8.dp),
                                id = R.drawable.ic_fan_square,
                                alt = "Turbo Mode",
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    val currentTime = LocalTime.now()
                    val format = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"))

                    Text(
                        text = format,
                        style = TextStyle(color = Color.Black),
                        fontSize = 36.sp,
                        fontWeight = FontWeight(500),

                    )
                    Text( //This is where we show current AC but Idk if we have that yet
                        text = "AC1: ΚΟΥΖΙΝΑ",
                        style = TextStyle(color = Color.Black),
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val tempText= if (uiState.acIsOn){
                        uiState.temperature.toString() + "°C"
                    } else {
                        " "
                    }
                    Text(
                        text = tempText,
                        style = TextStyle(color = Color.White),
                        fontSize = 60.sp,
                        fontWeight = FontWeight(500)
                    )
                    val modeText = if (uiState.acIsOn) {
                        when (uiState.mode) {
                            Mode.HEAT ->"ΘΕΡΜΑΝΣΗ"
                            Mode.DRY -> "ΑΦΥΓΡΑΝΣΗ"
                            Mode.COLD -> "ΨΥΞΗ"
                            Mode.AUTO -> "ΑΥΤΟΜΑΤH"
                        }
                    } else {
                        ""
                    }
                    Text(
                        text = modeText,
                        style = TextStyle(color = Color.White),
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500),
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start=8.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    val timerState = if (uiState.turnOffAlarmState) "ΚΛΕΙΣΙΜΟ" else "ΑΝΟΙΓΜΑ"
                    Text(
                        text = timerState,
                        style = TextStyle(color = Color.Black),
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500),

                        )
                    val time= if (uiState.turnOffAlarmState) uiState.turnOffAlarmTime else uiState.turnOnAlarmTime
                    val hours = time.hours
                    val minutes= time.minutes
                    Text(
                        text = "ΣΕ " + hours + "Ω : " + minutes + "Λ",
                        style = TextStyle(color = Color.Black),
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
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
        onTurnOnAlarmStateChanged = { _: Boolean -> mainActivityViewModel.toggleTurnOnAlarm() },
        onTurnOffAlarmStateChanged = { _: Boolean -> mainActivityViewModel.toggleTurnOffAlarm() },
        onTurnOnAlarmTimeChanged = mainActivityViewModel::setTurnOnAlarmTime,
        onTurnOffAlarmTimeChanged = mainActivityViewModel::setTurnOffAlarmTime,
        onTurnOnAlarmRepeatChanged = mainActivityViewModel::setTurnOnAlarmRepeat,
        onTurnOffAlarmRepeatChanged = mainActivityViewModel::setTurnOffAlarmRepeat,
        onToggleTurnOnAlarmDay = mainActivityViewModel::toggleTurnOnAlarmDay,
        onToggleTurnOffAlarmDay = mainActivityViewModel::toggleTurnOffAlarmDay,
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
    onTurnOnAlarmStateChanged: (Boolean) -> Unit,
    onTurnOffAlarmStateChanged: (Boolean) -> Unit,
    onTurnOnAlarmTimeChanged: (Time) -> Unit,
    onTurnOffAlarmTimeChanged: (Time) -> Unit,
    onTurnOnAlarmRepeatChanged: (AlarmRepeat) -> Unit,
    onTurnOffAlarmRepeatChanged: (AlarmRepeat) -> Unit,
    onToggleTurnOnAlarmDay: (DayOfWeek) -> Unit,
    onToggleTurnOffAlarmDay: (DayOfWeek) -> Unit,
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
                    ACDetails(uiState= uiState)
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
                        Menu.TIMER, Menu.TIMER_ON, Menu.TIMER_OFF ->
                            TimerMenu(
                                currentMenu = uiState.activeMenu,
                                changeMenuCallback = changeMenu,
                                turnOnAlarm = uiState.turnOnAlarm,
                                turnOffAlarm = uiState.turnOffAlarm,
                                onTurnOnAlarmStateChanged = onTurnOnAlarmStateChanged,
                                onTurnOffAlarmStateChanged = onTurnOffAlarmStateChanged,
                                onTurnOnAlarmTimeChanged = onTurnOnAlarmTimeChanged,
                                onTurnOffAlarmTimeChanged = onTurnOffAlarmTimeChanged,
                                onTurnOnAlarmRepeatChanged = onTurnOnAlarmRepeatChanged,
                                onTurnOffAlarmRepeatChanged = onTurnOffAlarmRepeatChanged,
                                onToggleTurnOnAlarmDay = onToggleTurnOnAlarmDay,
                                onToggleTurnOffAlarmDay = onToggleTurnOffAlarmDay,
                            )
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

