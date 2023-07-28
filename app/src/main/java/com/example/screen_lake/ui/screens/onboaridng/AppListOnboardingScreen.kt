package com.example.screen_lake.ui.screens.onboaridng

import android.content.pm.ApplicationInfo
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.screen_lake.R
import com.example.screen_lake.extensions.getInstalledAppsList
import com.example.screen_lake.ui.bottomsheets.StartOnBoardingBottomSheet
import com.example.screen_lake.ui.utils.CustomTextField
import com.example.screen_lake.ui.utils.RoundedCorneredButton
import com.example.screenlake.utils.Constants.StringConstants.EMPTY
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
fun AppListOnboardingScreen(
     navHostController: NavHostController,
     onBoardingViewModel: OnBoardingViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Expanded,
        animationSpec = spring(Spring.DampingRatioNoBouncy),
        confirmStateChange = { false },
    )
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    val appsList by rememberUpdatedState(LocalContext.current.getInstalledAppsList())


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            StartOnBoardingBottomSheet() {
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        },
        sheetElevation = 20.dp,
        sheetGesturesEnabled = false,
        sheetBackgroundColor = MaterialTheme.colors.secondary,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    ) {
        MainScreenContent(bottomSheetScaffoldState,appsList)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreenContent(bottomSheetScaffoldState: BottomSheetScaffoldState,appsList: List<ApplicationInfo>){
    Box(
        modifier = Modifier
            .clickable(!bottomSheetScaffoldState.bottomSheetState.isExpanded) {

            }
            .alpha(if (bottomSheetScaffoldState.bottomSheetState.isExpanded) 0.5f else 1f)
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(bottom = 16.dp, top = 0.dp)
        ) {
            val (nextButton, topBody, body) = createRefs()
            TopBodyContent(
                modifier = Modifier
                    .constrainAs(topBody) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }
            )
            MainBodyContent(appsList = appsList,
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(topBody.bottom, margin = 20.dp)
                        bottom.linkTo(nextButton.top, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
            )
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(nextButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }) {
                RoundedCorneredButton(buttonText = stringResource(id = R.string.next),
                    buttonColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.primary,
                    onClickAction = {})
            }
        }
    }
}

@Composable
private fun TopBodyContent(modifier: Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = 0.5f,
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
                text = stringResource(id = R.string.onboarding_distracting_apps_title),
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h1
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = stringResource(id = R.string.onboarding_shift_distraction_description),
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
private fun MainBodyContent(modifier: Modifier,appsList:List<ApplicationInfo>) {
    val textState = remember { mutableStateOf("") }
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondaryVariant,
                    RoundedCornerShape(20.dp)
                )
                .background(MaterialTheme.colors.background)
                .padding(vertical = 2.dp, horizontal = 10.dp),
            text = textState.value,
            placeHolderText = stringResource(id = R.string.search_app),
            onValueChange = {
                textState.value = it
            },
            keyboardType = KeyboardType.Text,
            paddingLeadingIconEnd = 8.dp,
            paddingTrailingIconStart = 8.dp,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    tint = MaterialTheme.colors.onSecondary,
                    contentDescription = EMPTY
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(appsList.size) {appInfo ->
                NotDefinedAppItem()
            }
        }
    }
}

@Composable
private fun NotDefinedAppItem(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = MaterialTheme.colors.background, shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(R.drawable.iv_rocket),
                    contentDescription = EMPTY,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(0.dp, Color.DarkGray, CircleShape)
                )

                Text(text = "Instagram",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp))
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewStartOnBoardingBottomSheet() {
    AppListOnboardingScreen(rememberNavController())
}