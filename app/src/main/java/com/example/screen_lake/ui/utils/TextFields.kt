package com.example.screen_lake.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.screen_lake.ui.theme.appFonts
import java.util.Stack

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeHolderText:String?=null,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (leadingIcon!=null)
            leadingIcon()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
        ) {
            BasicTextField(
                value = text,
                cursorBrush = SolidColor(MaterialTheme.colors.onSecondary),
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = TextStyle(color = MaterialTheme.colors.onSecondary,
                    fontWeight =  FontWeight(500),
                    fontFamily = appFonts,
                    fontSize = 14.sp,),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    capitalization = if (keyboardType == KeyboardType.Password) KeyboardCapitalization.None else KeyboardCapitalization.Sentences,
                    keyboardType = keyboardType,
                    imeAction = ImeAction.None
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.wrapContentSize()
                    ) {
                        if (text.isEmpty()) {
                            placeHolderText?.let {
                                Text(
                                    modifier = Modifier.padding(0.dp),
                                    text = it,
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.body2,
                                    color = LocalContentColor.current.copy(ContentAlpha.medium)
                                )
                            }
                        }
                    }
                    innerTextField()
                })
        }
        if (trailingIcon != null)
            trailingIcon()
    }

}