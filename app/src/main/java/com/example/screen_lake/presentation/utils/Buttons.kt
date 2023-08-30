package com.example.screen_lake.presentation.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    modifier: Modifier,
    onClickAction: ()->Unit
){
    Button(
        onClick = { if (isClickable) onClickAction() else null},
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        enabled = isClickable,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
    ) {
        Text(text = buttonText,
            color = textColor,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    }
}