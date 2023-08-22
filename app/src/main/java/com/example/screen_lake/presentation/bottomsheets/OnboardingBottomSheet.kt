package com.example.screen_lake.presentation.bottomsheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.screen_lake.presentation.utils.RoundedCorneredButton
import com.example.screen_lake.appUtils.Constants
import com.example.screen_lake.appUtils.Constants.TestTags.BOTTOMSHEET_BUTTON_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.ONBOARDING_BOTTOM_SHEET_TEST_TAG

@Composable
@ExperimentalMaterialApi
fun OnBoardingBottomSheet(
    image: Painter,
    title:String,
    description:String,
    buttonText:String,
    addBottomText:Boolean=false,
    bottomText:String?=null,
    onButtonClicked: () -> Unit,
    onBottomTextClicked: (() -> Unit)?=null
){
    Column(modifier = Modifier
        .testTag(ONBOARDING_BOTTOM_SHEET_TEST_TAG)
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ){
        Box(
            Modifier
                .width(96.dp)
                .height(96.dp)
                .background(
                    color = MaterialTheme.colors.background,
                    shape = CircleShape
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(painter = image, contentDescription = Constants.StringConstants.EMPTY)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = title,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = description,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(60.dp))
        RoundedCorneredButton(
            buttonText = buttonText,
            buttonColor = MaterialTheme.colors.surface,
            textColor =  MaterialTheme.colors.primary,
            modifier = Modifier.testTag(BOTTOMSHEET_BUTTON_TEST_TAG),
            onClickAction = onButtonClicked
        )
        if (addBottomText){
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = bottomText?: Constants.StringConstants.EMPTY,
                color = MaterialTheme.colors.onError,
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    if (onBottomTextClicked != null) {
                        onBottomTextClicked()
                    }
                }
            )
        }
    }
}