package com.example.screen_lake.ui.utils

import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCorneredButton(
    buttonText:String,
    buttonColor:Color,
    textColor:Color,
    isClickable:Boolean=true,
    onClickAction: ()->Unit
){
    Button(onClick = { onClickAction() },
        enabled = isClickable,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
    ) {
        Text(text = buttonText,
            color = textColor,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}