package com.example.screen_lake.presentation.screens.onboarding.appListOnBoardingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.screen_lake.R
import com.example.screen_lake.appUtils.Constants.IntegerConstants.FIVE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO
import com.example.screen_lake.appUtils.Constants.StringConstants.EMPTY
import com.example.screen_lake.appUtils.Constants.TestTags.MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.SHOW_MORE_OR_LESS_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.TOP_BODY_CONTENT_TEST_TAG
import com.example.screen_lake.appUtils.enums.AppDistractions
import com.example.screen_lake.appUtils.enums.OnboardingTrackStep
import com.example.screen_lake.appUtils.enums.getAppDistractionFromKey
import com.example.screen_lake.appUtils.enums.getAppDistractionList
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.presentation.bottomsheets.OnBoardingBottomSheet
import com.example.screen_lake.presentation.navigation.Screen
import com.example.screen_lake.presentation.utils.BottomButtonContent
import com.example.screen_lake.presentation.utils.CustomTextField
import com.example.screen_lake.presentation.utils.DropDownSelectionItem
import com.example.screen_lake.presentation.utils.NoRippleInteractionSource
import com.example.screen_lake.presentation.utils.OptionSelectedItem
import com.example.screen_lake.presentation.utils.TopBodyContent
import com.example.screen_lake.presentation.viewmodels.AppListOnBoardingScreenEvent
import com.example.screen_lake.presentation.viewmodels.AppListOnBoardingScreenUiEvents
import com.example.screen_lake.presentation.viewmodels.AppListOnboardingScreenState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
fun AppListOnboardingScreen(
    navHostController: NavHostController,
    onboardingTracker: OnboardingTracker,
    dataState: StateFlow<AppListOnboardingScreenState>,
    uiEvents : SharedFlow<AppListOnBoardingScreenUiEvents>,
    onEvent : (AppListOnBoardingScreenEvent)->Unit
) {
    val state by dataState.collectAsState()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(
        initialValue = if (onboardingTracker.step== OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step) BottomSheetValue.Expanded else BottomSheetValue.Collapsed,
        animationSpec = SwipeableDefaults.AnimationSpec,
        confirmStateChange = { false },
    )
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)


    LaunchedEffect(key1 = true){
        uiEvents.collectLatest {
            when(it){
                is AppListOnBoardingScreenUiEvents.NavigateToBehaviorOnboardingScreen ->{
                    navigateToBehaviorOnBoardingScreen(navHostController)
                }
                else->{}
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            OnBoardingBottomSheet(
                image = painterResource(id = R.drawable.iv_rocket),
                title = stringResource(id = R.string.shift_your_distraction),
                description = stringResource(id = R.string.onboarding_shift_distraction_description),
                buttonText = stringResource(id = R.string.start_on_boarding),
                addBottomText = false,
                onButtonClicked = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                        onEvent(AppListOnBoardingScreenEvent.UpdateOnBoardingTracker)
                    }
                })
        },
        sheetElevation = 20.dp,
        sheetGesturesEnabled = false,
        sheetBackgroundColor = MaterialTheme.colors.secondary,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    ) {
        MainScreenContent(bottomSheetScaffoldState, state){
            onEvent(it)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreenContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    state: AppListOnboardingScreenState,
    onEvent : (AppListOnBoardingScreenEvent)->Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                enabled = !bottomSheetScaffoldState.bottomSheetState.isExpanded,
                interactionSource = NoRippleInteractionSource(),
                indication = null
            ) {

            }
            .alpha(if (bottomSheetScaffoldState.bottomSheetState.isExpanded) 0.5f else 1f)
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(bottom = 16.dp, top = 0.dp),
        ) {
            val (nextButton, topBody, body) = createRefs()
            TopBodyContent(
                progress=state.progress,
                title=stringResource(id = R.string.onboarding_distracting_apps_title),
                description=stringResource(id = R.string.onboarding_distracting_apps_description),
                modifier = Modifier
                    .testTag(TOP_BODY_CONTENT_TEST_TAG)
                    .constrainAs(topBody) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }
            )
            AppListMainBodyContent(
                state=state,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(topBody.bottom)
                        bottom.linkTo(nextButton.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
            ){
                onEvent(it)
            }
            BottomButtonContent(
                stateDisabled = state.disableButton,
                modifier = Modifier
                    .constrainAs(nextButton) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
                onClick = {
                    if (!state.disableButton){
                        onEvent(AppListOnBoardingScreenEvent.OnNextClicked)
                    }
                },
                onBottomTextClicked = {

                }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppListMainBodyContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    state: AppListOnboardingScreenState,
    modifier: Modifier,
    onEvent : (AppListOnBoardingScreenEvent)->Unit,
) {
    val context = LocalContext.current
    state.apply {
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
            text = searchText,
            placeHolderText = stringResource(id = R.string.search_app),
            onValueChange = {
                onEvent(AppListOnBoardingScreenEvent.SearchAppTextUpdated(it))
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
        AnimatedVisibility(visible = filteredList.isNotEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .testTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG)
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    itemsIndexed(
                        if (expandedList||searchText.isNotEmpty()||filteredList.size<=FIVE) filteredList else filteredList.subList(
                            ZERO,
                            FIVE
                        )
                    ) { index, item ->
                        InstalledAppItems(info = item,bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            if (!bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                                onEvent(
                                    AppListOnBoardingScreenEvent.OnAppSelected(
                                        index,
                                        item.copy(distractionLevel = it.key)
                                    )
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = searchText.isEmpty()&&filteredList.size>FIVE) {
                    Text(
                        text = if (expandedList) context.getString(R.string.show_less) else context.getString(R.string.show_more_apps, installedApps.size - FIVE),
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSecondary,
                        maxLines = 1,
                        modifier = Modifier
                            .testTag(SHOW_MORE_OR_LESS_TEST_TAG)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .clickable(
                                interactionSource = NoRippleInteractionSource(),
                                indication = null
                            ) {
                                onEvent(
                                    AppListOnBoardingScreenEvent.OnExpandAppList(
                                        !expandedList
                                    )
                                )
                            }
                    )
                }
            }
        }
    }
    }
}

@Composable
private fun InstalledAppItems(info: AppInfo,isClickable:Boolean ,onClick: (AppDistractions) -> Unit) {
        val appIcon = info.bitmapResource?.asImageBitmap()

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
                        text = info.realAppName ?: EMPTY,
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
                ) {
                    getAppDistractionFromKey(info.distractionLevel).apply{
                        OptionSelectedItem(text=distraction,background=background, textColor = color)
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = MaterialTheme.colors.primaryVariant,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable(
                                enabled =isClickable,
                                interactionSource = NoRippleInteractionSource(),
                                indication = null
                            ) {
                                isContextMenuVisible = true
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = if (info.distractionLevel == AppDistractions.NOT_DEFINED.key) Icons.Default.Add else Icons.Outlined.Edit,
                            contentDescription = EMPTY,
                            tint = MaterialTheme.colors.surface,
                        )
                        AppDistractionDropDownMenu(
                            isContextMenuVisible,
                            info.distractionLevel ?: AppDistractions.NOT_DEFINED.key,
                            onClick = { visible, selectedItem ->
                                isContextMenuVisible = visible
                                onClick(selectedItem)
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

@Composable
private fun AppDistractionDropDownMenu(
    isContextMenuVisible: Boolean,
    selectedKey: String,
    onClick: (Boolean, AppDistractions) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = isContextMenuVisible,
        onDismissRequest = { onDismiss(false) },
        modifier = Modifier
            .padding(0.dp)
            .background(
                color = MaterialTheme.colors.background,
            )
    ) {
        getAppDistractionList().forEach { distractionItem ->
            DropDownSelectionItem(
                key=distractionItem.key,
                text=distractionItem.distraction,
                background = distractionItem.color,
                selectedKey=selectedKey,
                onClick = { onClick(false,distractionItem) } )
        }
    }
}

private fun navigateToBehaviorOnBoardingScreen(navController: NavController) {
    navController.popBackStack()
    navController.navigate(Screen.BehaviorOnboardingScreenRoute.route)
}
