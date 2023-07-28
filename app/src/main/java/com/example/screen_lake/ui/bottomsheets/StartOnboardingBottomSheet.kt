package com.example.screen_lake.ui.bottomsheets

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.screen_lake.R
import com.example.screen_lake.ui.utils.RoundedCorneredButton
import com.example.screenlake.utils.Constants

@Composable
@ExperimentalMaterialApi
fun StartOnBoardingBottomSheet(
    onStartOnboarding: () -> Unit
){
    Column(modifier = Modifier
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
            Image(painter = painterResource(id = R.drawable.iv_rocket), contentDescription = Constants.StringConstants.EMPTY)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(id = R.string.shift_your_distraction),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(id = R.string.onboarding_shift_distraction_description),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(60.dp))
        RoundedCorneredButton(
            buttonText = stringResource(id = R.string.start_on_boarding),
            buttonColor = MaterialTheme.colors.surface,
            textColor =  MaterialTheme.colors.primary,
            onClickAction = onStartOnboarding
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewStartOnBoardingBottomSheet(){
    StartOnBoardingBottomSheet(){

    }
}