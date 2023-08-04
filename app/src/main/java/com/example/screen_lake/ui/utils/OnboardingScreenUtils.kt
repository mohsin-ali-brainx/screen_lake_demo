package com.example.screen_lake.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.screen_lake.R
import com.example.screenlake.utils.Constants

@Composable
fun TopBodyContent(
    progress: Float,
    title:String,
    description: String,
    modifier: Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progress,
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            backgroundColor = MaterialTheme.colors.background
        )
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h1
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = description,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun BottomButtonContent(
    buttonText:String?=null,
    stateDisabled:Boolean,
    modifier: Modifier,
    onClick:()->Unit
){
    Box(modifier = modifier) {
        RoundedCorneredButton(buttonText = buttonText?:stringResource(id = R.string.next),
            buttonColor = if (stateDisabled) MaterialTheme.colors.onPrimary else MaterialTheme.colors.surface,
            textColor = if (stateDisabled) MaterialTheme.colors.onError else MaterialTheme.colors.primary,
            onClickAction = {
                onClick()
            })
    }
}

@Composable
fun OptionSelectedItem(text:String,background:Color,textColor:Color) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = background,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            color = textColor,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun DropDownSelectionItem(
    key:Any,
    text:String,
    background: Color,
    selectedKey:Any,
    onClick: () -> Unit
){
    DropdownMenuItem(
        modifier = Modifier.widthIn(200.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        onClick = { onClick() }) {
        ConstraintLayout() {
            val (iconStart, title, checkedIcon) = createRefs()
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .constrainAs(iconStart) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .background(background, shape = CircleShape)
            ) {

            }
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                color = if (selectedKey == key) MaterialTheme.colors.onBackground else MaterialTheme.colors.onError,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(iconStart.end)
                        end.linkTo(checkedIcon.start)
                    },
                maxLines = 1,
            )
            if (selectedKey == key) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .constrainAs(checkedIcon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .background(MaterialTheme.colors.onBackground, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Default.Done,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = Constants.StringConstants.EMPTY
                    )
                }
            }
        }
    }
}
