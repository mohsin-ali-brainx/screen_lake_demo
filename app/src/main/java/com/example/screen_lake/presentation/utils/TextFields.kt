package com.example.screen_lake.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.screen_lake.appUtils.Constants
import com.example.screen_lake.appUtils.Constants.TestTags.TEXT_FIELD_PLACE_HOLDER_TEST_TAG
import com.example.screen_lake.presentation.theme.appFonts

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
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
        if (leadingIcon!=null)
            leadingIcon()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
        ) {
            BasicTextField(
                modifier=Modifier
                    .testTag(Constants.TestTags.CUSTOM_EDIT_TEXT_TEST_TAG)
                    .fillMaxWidth(),
                value = text,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSecondary),
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary,
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (text.isEmpty()) {
                            placeHolderText?.let {
                                Text(
                                    modifier = Modifier
                                        .testTag(TEXT_FIELD_PLACE_HOLDER_TEST_TAG)
                                        .padding(0.dp),
                                    text = it,
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onError
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