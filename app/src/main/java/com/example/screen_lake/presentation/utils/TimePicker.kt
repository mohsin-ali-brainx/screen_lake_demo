package com.example.screen_lake.presentation.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.AMPMHours
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ONE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO
import com.example.screen_lake.appUtils.Constants.StringConstants.DIVIDER_TEXT
import com.example.screen_lake.presentation.theme.Divider

@Composable
fun SiftTimePicker(
    modifier: Modifier=Modifier,
    is24Hours:Boolean=false,
    hours:Int=ONE,
    mint:Int= ZERO,
    dayTime: AMPMHours.DayTime =AMPMHours.DayTime.AM,
    hoursDividerText:String=DIVIDER_TEXT,
    mintDividerText:String=DIVIDER_TEXT,
    selectedTime:(Hours)->Unit
){

    var pickerValue by remember {
        if (is24Hours)
            mutableStateOf<Hours>(FullHours(hours,mint))
            else
        mutableStateOf<Hours>(AMPMHours(hours, mint, dayTime ))
    }

    HoursNumberPicker(
        modifier =modifier,
        dividersColor = Divider,
        value = pickerValue,
        onValueChange = {
            pickerValue = it
            selectedTime(it)
        },
        hoursDivider = {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                text = hoursDividerText
            )
        },
        minutesDivider = {
            if (is24Hours) {
                null
            } else {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center,
                    text = mintDividerText
                )
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium

    )


}