package com.example.screen_lake.ui.screens.onboarding

import android.content.pm.ApplicationInfo
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.screen_lake.R
import com.example.screen_lake.enums.AppDistractions
import com.example.screen_lake.enums.getAppDistractionList
import com.example.screen_lake.extensions.getAppIconBitmap
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
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {
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
        MainScreenContent(bottomSheetScaffoldState, appsList)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreenContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    appsList: List<ApplicationInfo>
) {
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
            MainBodyContent(
                appsList = appsList,
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(topBody.bottom)
                        bottom.linkTo(nextButton.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
            )
            Box(modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 0.dp)
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
private fun MainBodyContent(modifier: Modifier, appsList: List<ApplicationInfo>) {
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
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(appsList) { index, item ->
                AppItems(app = item) {

                }
            }
        }
    }
}

@Composable
private fun AppItems(app: ApplicationInfo?, onClick: () -> Unit) {
    app?.let { appInfo ->
        val packageManager = LocalContext.current.packageManager
        val appName = if (appInfo.labelRes != 0)
            packageManager.getResourcesForApplication(appInfo).getString(appInfo.labelRes)
        else
            appInfo.loadLabel(packageManager).toString()

        val appIcon = LocalContext.current.getAppIconBitmap(appInfo.packageName)

        var isContextMenuVisible by rememberSaveable {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(vertical = 6.dp, horizontal = 12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {

                    if (appIcon != null) {
                        Image(
                            bitmap = appIcon,
                            contentDescription = EMPTY,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(0.dp, Color.DarkGray, CircleShape)
                        )
                    } else {
                        Image(
                            painterResource(id = R.drawable.ic_android),
                            contentDescription = EMPTY,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(0.dp, Color.DarkGray, CircleShape)
                        )
                    }

                    Text(
                        text = appName,
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        maxLines = 1,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onClick() }
                ) {
                    DistractionItem()
                    Spacer(modifier = Modifier.width(2.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = MaterialTheme.colors.primaryVariant,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                isContextMenuVisible=true
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = EMPTY,
                            tint = MaterialTheme.colors.surface
                        )
                        DistractionDropDownMenu(
                            isContextMenuVisible,
                            onClick = {visible,selectedItem->
                                isContextMenuVisible = visible
                            },
                            onDismiss = {
                                isContextMenuVisible = it
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun DistractionItem(item: AppDistractions = AppDistractions.NOT_DEFINED) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = item.background,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = item.distraction,
            style = MaterialTheme.typography.body2,
            color = item.color,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun DistractionDropDownMenu(
    isContextMenuVisible:Boolean,
    onClick:(Boolean,AppDistractions)->Unit,
    onDismiss:(Boolean)->Unit
){
    DropdownMenu(
        expanded = isContextMenuVisible,
        onDismissRequest = { onDismiss(false)},
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.background,
            )
    ) {
        getAppDistractionList().forEach { distractionItem ->
            DropdownMenuItem(
                onClick = { onClick(false,distractionItem) }) {
                ConstraintLayout() {
                    val (iconStart, title) = createRefs()
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .constrainAs(iconStart) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                            .background(distractionItem.color, shape = CircleShape)
                    ) {

                    }
                    Text(
                        text = distractionItem.distraction,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .constrainAs(title) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(iconStart.end)
                            },
                        maxLines = 1,
                    )
                }
            }
        }
    }
}
