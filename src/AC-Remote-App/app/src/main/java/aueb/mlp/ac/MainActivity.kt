package aueb.mlp.ac

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import aueb.mlp.ac.model.ACManagerImpl
import aueb.mlp.ac.ui.theme.ACRemoteAppTheme
import aueb.mlp.ac.ui.theme.Red40
import aueb.mlp.ac.ui.theme.ACShapes
import aueb.mlp.ac.ui.theme.Green40
import aueb.mlp.ac.ui.theme.component.AcButtonColors
import aueb.mlp.ac.ui.theme.component.AcText
import aueb.mlp.ac.ui.theme.component.Icon
import aueb.mlp.ac.ui.theme.component.ModeButton
import aueb.mlp.ac.ui.theme.component.PlainButton
import aueb.mlp.ac.ui.theme.component.PlainButtonWithSwitchAndText
import aueb.mlp.ac.ui.theme.component.PlainIconButton
import aueb.mlp.ac.ui.theme.component.PlainTextButton
import aueb.mlp.ac.ui.theme.component.RowButton
import aueb.mlp.ac.ui.theme.component.RowButtonWithIconCallback
import aueb.mlp.ac.ui.theme.component.SizeVariation
import aueb.mlp.ac.ui.theme.component.SimpleAlertDialogInGreek
import aueb.mlp.ac.ui.theme.component.StatefulButton
import aueb.mlp.ac.ui.theme.component.StatefulTextButton
import aueb.mlp.ac.ui.theme.component.TextSizeVariation
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val viewModel = MainActivityViewModel(ACManagerImpl(), HashMap<String, Menu>())

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
fun ModeMenu(
    modeCallback: (input: String) -> Unit,
    currentMode : Mode
) {
    //Text(currentMode.toString())
    // TODO: ### replace with grid https://developer.android.com/jetpack/compose/lists#lazy-grids ###
    // Maybe the buttons in this composable should always be enabled? We won't be able to access them if the AC is off
    Row(
        horizontalArrangement = Arrangement.spacedBy(72.dp),
        verticalAlignment= Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentSize()
    ) {
        ModeButton(
            text = " ΘΕΡΜΑΝΣΗ" ,
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
            text = "     ΨΥΞΗ     " ,
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
        Spacer(modifier = Modifier.height(48.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(72.dp),
        verticalAlignment= Alignment.CenterVertically,
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
            selected = currentFanMode == Fan.SILENT,
            modifier= Modifier
                .size(200.dp, 80.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        StatefulTextButton(
            onClick = {fanCallback("NORMAL")  },
            text = "ΚΑΝΟΝΙΚΗ" ,
            enabled = true,
            selected = currentFanMode == Fan.NORMAL,
                    modifier= Modifier
                    .size(200.dp, 80.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        StatefulTextButton(
            onClick = {fanCallback("TURBO")  },
            text = "TURBO" ,
            enabled = true,
            selected = currentFanMode == Fan.TURBO,
            modifier= Modifier
                .size(200.dp, 80.dp)
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
            switchChecked = currentBlindMode == Blinds.VERTICAL,
            modifier= Modifier
                .size(300.dp, 160.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        PlainButtonWithSwitchAndText(
            onClick = {
                if (currentBlindMode == Blinds.HORIZONTAL)
                    blindCallback("OFF")
                else
                    blindCallback("HORIZONTAL")
            },
            text = "ΔΕΞΙΑ-ΑΡΙΣΤΕΡΑ" ,
            enabled = true,
            switchChecked = currentBlindMode == Blinds.HORIZONTAL,
                    modifier= Modifier
                    .size(300.dp, 160.dp)
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
            onNavigateToSingleAlarm = { changeMenuCallback("TIMER_ON")
            }
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

    Box(
        modifier = Modifier.size(width = 250.dp, height = 450.dp)
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
                AcText(
                    text = if (isTurnOnAlarm) "ΑΝΟΙΞΕ" else "ΚΛΕΙΣΕ",
                    textSizeVariation = TextSizeVariation.BODY_LARGE,
                )
                AcText(
                    text = alarm.time.toString(),
                    textSizeVariation = TextSizeVariation.BODY_LARGE,
                )
                AcText(
                    text = alarm.repeat.toString(),
                )
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
            modifier = Modifier
                .padding(vertical=16.dp, horizontal = 32.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        PlainTextButton(
            text = alarm.repeat.toString(),
            onClick = { changeRepeatPopup = true },
            enabled = true,
            modifier = Modifier
                .padding(vertical=16.dp, horizontal = 32.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        PlainTextButton(
            text = "ΠΙΣΩ",
            onClick = onNavigateBack,
            enabled = true,
            modifier = Modifier
                .padding(vertical=16.dp, horizontal = 32.dp)
                .weight(1f)
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
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical=16.dp, horizontal = 32.dp)
        ,
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
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical=16.dp, horizontal = 32.dp)
                    )
                    StatefulTextButton(
                        text = "ΚΑΘΕ ΜΕΡΑ",
                        onClick = { onAlarmRepeatChanged(AlarmRepeat.EverydayRepeat) },
                        enabled = true,
                        selected = alarmRepeat is AlarmRepeat.EverydayRepeat,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical=16.dp, horizontal = 32.dp)
                    )
                    StatefulTextButton(
                        text = "ΠΡΟΧΩΡΗΜΕΝΕΣ",
                        onClick = { onAlarmRepeatChanged(AlarmRepeat.CustomRepeat(listOf())) },
                        enabled = true,
                        selected = alarmRepeat is AlarmRepeat.CustomRepeat,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical=16.dp, horizontal = 32.dp)
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
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical=16.dp, horizontal = 32.dp),
                    textSizeVariation = TextSizeVariation.BODY_LARGE,
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(start = 40.dp, bottom = 24.dp),
    ){
        StatefulTextButton(
            onClick = {
                changeMenuCallback("MODE")
            },
            text = "ΛΕΙΤΟΥΡΓΙΑ" ,
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.MODE,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f, fill = true),
        )
        StatefulTextButton(
            onClick = {
                changeMenuCallback("FAN")
            },
            text = "ΕΝΤΑΣΗ" ,
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.FAN,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f, fill = true),
        )
        StatefulTextButton(
            onClick = {
                changeMenuCallback("TIMER")
            },
            text = "ΧΡΟΝΟΔΙΑΚΟΠΤΗΣ",
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.TIMER,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f, fill = true),
        )
        StatefulTextButton(
            onClick = {
                changeMenuCallback("BLINDS")
            },
            text = "ΠΕΡΣΙΔΕΣ",
            enabled = uiState.acIsOn,
            selected = uiState.activeMenu == Menu.BLINDS,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f, fill = true),
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
        ),
        modifier = Modifier
        .size(150.dp, 80.dp)
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
                sizeVariation = SizeVariation.SMALL,
            )
            AcText(
                text = "ECO",
                textSizeVariation = TextSizeVariation.BODY_SMALL,
            )
        }
    }
}


@Composable
fun ChangeDeviceButton(
    changeDeviceCallback: (String) -> Unit
){
    PlainTextButton(

        text = "ΑΛΛΑΞΕ ΣΥΣΚΕΥΗ",
        onClick = { changeDeviceCallback("CHANGE") },
        enabled = true,
        modifier = Modifier
            .size(150.dp, 150.dp)
            .padding(16.dp),

        textSizeVariation = TextSizeVariation.BODY_SMALL,


    )
}
@Composable
fun OffButton(
    onSwitchOnOff: () -> Unit,
    isOpen : Boolean,
){

    Box(
        modifier = Modifier
            .background(color = if (isOpen) Red40 else Green40, shape = CircleShape)
            .size(110.dp)
            .padding(16.dp)
            .wrapContentHeight(),
        contentAlignment = Alignment.Center


    ) {
        PlainIconButton(id =R.drawable.ic_on_off, alt ="off", onClick = { onSwitchOnOff() },
            enabled = true,
            enabledColors = if (isOpen) AcButtonColors(containerColor =Red40, contentColor = Color.White) else AcButtonColors(containerColor =Green40, contentColor = Color.White),
            sizeVariation = SizeVariation.LARGE,


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
        listOf(Color(0xFF575CDA), Color(0xFF797DA8))
    }

    Box(
        modifier = Modifier
            .size(size = 1200.dp)
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
                        id = R.drawable.ic_sun,
                        alt = "Heat Mode",
                        size = 380.dp,
                    )
                    Mode.COLD->Icon(
                        modifier = Modifier
                            .padding(start = 12.dp),
                        id = R.drawable.ic_snow,
                        alt = "Cold Mode",
                        size = 350.dp,
                    )
                    Mode.DRY->Icon(
                        modifier = Modifier
                            .padding(end = 48.dp),
                        id = R.drawable.ic_humid,
                        alt = "Dry Mode",
                        size = 380.dp,
                    )
                    Mode.AUTO->Icon(
                        id = R.drawable.ic_auto,
                        alt = "Auto Mode",
                        size = 380.dp,
                    )
                }
            } else {
                Icon(
                modifier = Modifier
                    .padding(start = 72.dp, top=24.dp),
                id = R.drawable.ic_moon,
                alt = "Sleep Mode",
                    size = 280.dp,
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    //.fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            ) {
                if(uiState.acIsOn){
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            id = R.drawable.ic_fan,
                            alt = "Fan",
                            size = 40.dp,
                        ) //PLease do not execute me publically for this I had no other idea
                        when (uiState.fan) {
                            Fan.SILENT -> repeat(3) { index ->
                                Icon(
                                    id = R.drawable.ic_fan_square,
                                    alt = "Normal Mode",
                                    modifier = Modifier
                                        .alpha(if (index == 0) 1f else 0.5f),
                                    sizeVariation = SizeVariation.SMALL,
                                )
                            }
                            Fan.NORMAL -> repeat(3) { index ->
                                Icon(
                                    id = R.drawable.ic_fan_square,
                                    alt = "Normal Mode",
                                    modifier = Modifier
                                        .alpha(if (index == 2) 0.5f else 1f),
                                    sizeVariation = SizeVariation.SMALL,
                                )
                            }
                            Fan.TURBO -> repeat(3) {
                                Icon(
                                    id = R.drawable.ic_fan_square,
                                    alt = "Turbo Mode",
                                    sizeVariation = SizeVariation.SMALL,
                                )
                            }
                        }
                    }
                }
                //Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    val currentTime = LocalTime.now()
                    val format = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                    val color = if (uiState.acIsOn) Color.Black else Color.White
                    AcText(
                        text = format,
                        textSizeVariation = TextSizeVariation.DISPLAY_MEDIUM,
                        color = color,
                    )
                    AcText(
                        text = uiState.acName,
                        textSizeVariation = TextSizeVariation.DISPLAY_SMALL,
                        color = color,
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
                    AcText(
                        text = tempText,
                        textSizeVariation = TextSizeVariation.DISPLAY_LARGE,
                        color = Color.White,
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
                    AcText(
                        text = modeText,
                        textSizeVariation = TextSizeVariation.DISPLAY_SMALL,
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) { if( !uiState.turnOnAlarmState && !uiState.turnOffAlarmState){
                Spacer(modifier = Modifier.weight(3f))

            }else if (uiState.turnOnAlarmState && uiState.turnOffAlarmState) {
                Column () {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {

                        val time = uiState.turnOffAlarmTime
                        val currentTime = LocalDateTime.now()
                        val targetTime =
                            currentTime.withHour(time.hours).withMinute(time.minutes).withSecond(0)
                                .withNano(0)
                        val timeUntil = if (targetTime.isAfter(currentTime)) {
                            Duration.between(currentTime, targetTime)
                        } else {
                            Duration.between(currentTime, targetTime.plusDays(1))
                        }
                        val hours = timeUntil.toHours()
                        val minutes = timeUntil.toMinutes() % 60
                        AcText(
                            text = " ΚΛΕΙΣΙΜΟ ΣΕ " + hours + "Ω : " + minutes + "Λ",
                            textSizeVariation = TextSizeVariation.DISPLAY_SMALL,
                            color = if (uiState.acIsOn) Color.Black else Color.White,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {

                        val time = uiState.turnOnAlarmTime
                        val currentTime = LocalDateTime.now()
                        val targetTime =
                            currentTime.withHour(time.hours).withMinute(time.minutes).withSecond(0)
                                .withNano(0)
                        val timeUntil = if (targetTime.isAfter(currentTime)) {
                            Duration.between(currentTime, targetTime)
                        } else {
                            Duration.between(currentTime, targetTime.plusDays(1))
                        }
                        val hours = timeUntil.toHours()
                        val minutes = timeUntil.toMinutes() % 60
                        AcText(
                            text = "ΑΝΟΙΓΜΑ  ΣΕ " + hours + "Ω : " + minutes + "Λ",
                            textSizeVariation = TextSizeVariation.DISPLAY_SMALL,
                            color = if (uiState.acIsOn) Color.Black else Color.White,
                        )
                    }
                }
                } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    val timerState = if (uiState.turnOffAlarmState) "ΚΛΕΙΣΙΜΟ" else "ΑΝΟΙΓΜΑ"
                    AcText(
                        text = timerState,
                        textSizeVariation = TextSizeVariation.DISPLAY_SMALL,
                        color = if (uiState.acIsOn) Color.Black else Color.White,
                        )

                    val time =
                        if (uiState.turnOffAlarmState) uiState.turnOffAlarmTime else uiState.turnOnAlarmTime
                    val currentTime = LocalDateTime.now()
                    val targetTime =
                        currentTime.withHour(time.hours).withMinute(time.minutes).withSecond(0)
                            .withNano(0)
                    val timeUntil = if (targetTime.isAfter(currentTime)) {
                        Duration.between(currentTime, targetTime)
                    } else {
                        Duration.between(currentTime, targetTime.plusDays(1))
                    }
                    val hours = timeUntil.toHours()
                    val minutes = timeUntil.toMinutes() % 60
                    AcText(
                        text = "ΣΕ " + hours + "Ω : " + minutes + "Λ",
                        textSizeVariation = TextSizeVariation.DISPLAY_SMALL,
                        color = if (uiState.acIsOn) Color.Black else Color.White,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(1f))

                }
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
        sizeVariation = SizeVariation.LARGE,
    )
    PlainIconButton(
        modifier = Modifier
            .size(width = 150.dp, height = 150.dp),
        id = R.drawable.ic_minus,
        alt = "Decrement Temperature",
        onClick = onDecrementTemperatureFunc,
        enabled = uiState.acIsOn,
        sizeVariation = SizeVariation.LARGE,
    )
}

@Composable
fun MainScreen(
    mainActivityViewModel: MainActivityViewModel
) {
    val uiState = mainActivityViewModel.uiState
    val acListState = mainActivityViewModel.acListState

    if (uiState == null || uiState.activeMenu == Menu.CHANGE || uiState.activeMenu == Menu.ADDAC ) {

        ChangeAcScreen(
            startValue = if (uiState == null || uiState?.activeMenu == Menu.CHANGE) "CHANGE_AC" else "ADD_AC",
            currentAC = uiState?.acName,
            acList = acListState,
            addedDevicesList = uiState?.addedDevices ?: ArrayList<String>(),
            onSetCurrentAcByName = mainActivityViewModel::setCurrentAcByName,
            onCreateNewAc = mainActivityViewModel::createNewAc,
            onDeleteExistingAcByName = mainActivityViewModel::deleteAcByName,
        )
    } else {
        MainScreenContent(
            uiState = uiState,
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
                        .weight(5f)

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
                        .padding(end=8.dp)
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
                        .weight(2f)
                ) {
                    ScreenMenu(changeMenu, uiState)

                }
                Column(
                    verticalArrangement  = Arrangement.Center,
                    horizontalAlignment= Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f)

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
                        else -> {

                        }
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)

                ) { //Eco mode
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    ) {
                        EcoButton(onEcoModeChanged, uiState)
                    }
                    Column(
                        horizontalAlignment  = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)

                    ) {
                        ChangeDeviceButton(changeMenu)
                    }
                    Column(
                        horizontalAlignment  = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(bottom=32.dp)
                    ) { OffButton(onSwitchOnOff=onSwitchOnOff , uiState.acIsOn)
                    }

                }
            }
        }


    }
}

@Composable
fun ChangeAcScreen(
    startValue: String,
    currentAC: String?,
    acList: List<String>,
    addedDevicesList: List<String>,
    onSetCurrentAcByName: (String) -> Unit,
    onCreateNewAc: (String) -> Unit,
    onDeleteExistingAcByName: (String) -> Unit,
) {

    var submenu by remember { mutableStateOf(startValue) }

    when (submenu) {
        "CHANGE_AC" -> ChangeAc(
            acList = acList,
            currentAC = currentAC,
            onSetCurrentAcByName = onSetCurrentAcByName,
            onDeleteExistingAcByName = onDeleteExistingAcByName,
            onNavigateToCreateNewAc = { submenu = "ADD_AC" },
            onNavigateBack = onSetCurrentAcByName,
        )

        "ADD_AC" -> AddAc(
            acList = acList,
            addedDevicesList = addedDevicesList,
            onCreateNewAc = onCreateNewAc,
            onNavigateBack = { submenu = "CHANGE_AC" },
        )
    }
}



@Composable
fun ChangeAc(
    acList: List<String>,
    currentAC: String?,
    onSetCurrentAcByName: (String) -> Unit,
    onDeleteExistingAcByName: (String) -> Unit,
    onNavigateToCreateNewAc: () -> Unit, // called when 'ΠΡΟΣΘΗΚΗ ΚΛΙΜΑΤΙΣΤΙΚΟΥ' button is clicked
    onNavigateBack: (String) -> Unit, // called when 'back' button is clicked
    // automatically goes back to main menu (for now)
) {
    // TODO: your stuff here...
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top=16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){

            if (currentAC != null){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.5f)
                        .padding(12.dp)

                ) {
                    BackButton(
                        onNavigateBack = { onNavigateBack(currentAC) }
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(8.5f)
                    .padding(top=16.dp),
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    items(acList) { acName ->
                        // read as: for each item in `acList`, create the following stuff,
                        // and refer to each item as `acName`
//                        Row(
//                            horizontalArrangement = Arrangement.spacedBy(8.dp),
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                .wrapContentSize()
//                        ) {
//
//                        }
                        ACRow(
                            acName = acName,
                            acID = acList.indexOf(acName) + 1,
                            setDeviceCallback = onSetCurrentAcByName,
                            deleteDeviceCallback = onDeleteExistingAcByName,
                        )
                    }
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround){

            RowButton(
                id = R.drawable.ic_plus_green,
                alt = "add ac",
                text="ΠΡΟΣΘΗΚΗ ΚΛΙΜΑΤΙΣΤΙΚΟΥ",
                enabled = true,
                onClick = onNavigateToCreateNewAc,
                modifier = Modifier
                    .fillMaxWidth(0.95f),
                sizeVariation = SizeVariation.LARGE,
                textSizeVariation = TextSizeVariation.BODY_LARGE,
            )
        }
    }
}

@Composable
fun AddAc(
    // add `acList` parameter if needed. maybe it's better to not show ac list in 'add ac' screen,
    // it might get confusing seeing the ac list in two places ('change ac' and 'add ac'), idk
    acList: List<String>,
    addedDevicesList: List<String>,
    onNavigateBack: () -> Unit, // called when 'back' button is clicked
    onCreateNewAc: (String) -> Unit,
) {
    var newAcName by remember { mutableStateOf("") }
    var selectedDevice  by remember { mutableStateOf("") }
    var addedDevices  by remember { mutableStateOf(addedDevicesList) }
    val context = LocalContext.current

    println("TEST TEST TEST $addedDevices , $acList")
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .padding(12.dp)
        ){
            BackButton(
                onNavigateBack = onNavigateBack
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.8f)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(7f),
            ) {
                var i = 1
                var j = 1
                while (i <= 3) {
                    val tempDeviceName = "ΚΛΙΜΑΤΙΣΤΙΚΟ $j"
                    if (acList.indexOf(tempDeviceName) == -1 && addedDevices.indexOf(tempDeviceName) == -1){
                        StatefulTextButton(text=tempDeviceName, onClick= {selectedDevice = tempDeviceName}, enabled =true, modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp), selected = (selectedDevice == tempDeviceName)  )
                        i += 1
                    }
                    j += 1
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(7f),
            ) {
                PlainButton(onClick = {}, enabled = true) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
                    ){
                        AcText(
                            text = "ΟΝΟΜΑ",
                            textSizeVariation = TextSizeVariation.BODY_LARGE,
                        )
                        BasicTextField(
                            value = newAcName,
                            textStyle = TextStyle.Default.copy(fontSize = 32.sp, textAlign = TextAlign.Center),
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            onValueChange = { newText ->
                                newAcName = newText
                            },

                        )
                        Divider (
                            color = Color.Black, //TODO: MAKE CONFIGURABLE (NOT HARDCODED)
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxHeight()
                                .fillMaxWidth(0.8f)

                        )
                    }
                }

                PlainTextButton(text="ΠΡΟΣΘΗΚΗ",
                    onClick= {
                        var message = "";

                        if (acList.indexOf(newAcName) == -1)
                            if (newAcName !== "" && selectedDevice !== ""){
                                onCreateNewAc(newAcName);
                                newAcName = "";
                                addedDevices += selectedDevice;
                                selectedDevice = "";
                                message = "Συνδεθήκαμε με το κλιματιστικό"
                            } else if (selectedDevice == ""){
                                message = "Δεν έχετε διαλέξει συσκευή"
                            } else{
                                message = "Δεν έχετε δώσει αναγνωριστικό στην συσκευή σας"
                            }
                        else
                            message = "Υπάρχει ήδη μία συσκευή με αυτό το αναγνωριστικό"


                        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                        toast.show()
                    },
                    enabled =true,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical=32.dp) )
            }
        }
    }


    // TODO: your stuff here...
}

@Composable
fun BackButton(
    onNavigateBack: () -> Unit
){
    PlainIconButton(
        modifier = Modifier
            .size(width = 125.dp, height = 125.dp),
        id = R.drawable.ic_back,
        alt = "Decrement Temperature",
        onClick = onNavigateBack,
        enabled = true,
        sizeVariation = SizeVariation.LARGE,
    )
}

@Composable
fun ACRow(
    acName : String,
    acID: Int,
    setDeviceCallback: (String) -> Unit,
    deleteDeviceCallback: (String) -> Unit
){
    var openDialog by remember { mutableStateOf(false) }

    RowButtonWithIconCallback(
        text="ΚΛΙΜΑΤΙΣΤΙΚΟ $acID: $acName ",
        onClick= { setDeviceCallback(acName) } ,
        enabled = true,
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(16.dp),
        id = R.drawable.ic_x,
        alt = "delete ac",
        onIconClick = {
            openDialog = true
        },
        sizeVariation = SizeVariation.LARGE,
        textSizeVariation = TextSizeVariation.BODY_LARGE,
    )

    if (openDialog){
        SimpleAlertDialogInGreek(
            onAccept = {openDialog = false;deleteDeviceCallback(acName) },
            onDismiss = {openDialog = false},
            onReject = {openDialog = false},
            title = "Delete AC",
            text = "Είστε σίγουροι ότι θέλετε να διαγράψετε τη συσκευή $acName;"
        )
    }
}