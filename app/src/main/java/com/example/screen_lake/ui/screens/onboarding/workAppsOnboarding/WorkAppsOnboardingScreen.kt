package com.example.screen_lake.ui.screens.onboarding.workAppsOnboarding

import android.content.pm.ApplicationInfo
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.screen_lake.R
import com.example.screen_lake.extensions.getAppIconBitmap
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.navigation.Screen
import com.example.screen_lake.ui.bottomsheets.OnBoardingBottomSheet
import com.example.screen_lake.ui.utils.BottomButtonContent
import com.example.screen_lake.ui.utils.CustomTextField
import com.example.screen_lake.ui.utils.SelectableItem
import com.example.screen_lake.ui.utils.TopBodyContent
import com.example.screenlake.utils.Constants.IntegerConstants.FIVE
import com.example.screenlake.utils.Constants.IntegerConstants.ZERO
import com.example.screenlake.utils.Constants.StringConstants.EMPTY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
fun WorkAppListOnboardingScreen(
    navHostController: NavHostController,
    onBoardingViewModel: WorkAppsOnboardingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = spring(Spring.DampingRatioNoBouncy),
        confirmStateChange = { false },
    )
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    val state by onBoardingViewModel.state.collectAsState()

    LaunchedEffect(key1 = true){
        onBoardingViewModel.eventFlow.collectLatest {
            when(it){
                is WorkAppAppListOnBoardingScreenUiEvents.OpenQuestionsBottomSheet->{
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
                is WorkAppAppListOnBoardingScreenUiEvents.OpenOccupationQuestionnaireScreen->{
                    navigateOccupationQuestionnaireScreen(navHostController)
                }
                else->{}
            }
        }
    }


    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            OnBoardingBottomSheet(
                image = painterResource(id = R.drawable.iv_light_bulb),
                title = stringResource(id = R.string.sift_ai_work_best_title),
                description = stringResource(id = R.string.you_can_answer_personalization_questions),
                buttonText = stringResource(id = R.string.answer_questions),
                addBottomText = true,
                bottomText = stringResource(id = R.string.skip_for_now),
                onButtonClicked = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                        onBoardingViewModel.onEventUpdate(WorkAppListOnBoardingScreenEvent.OnAnswerQuestionsButtonClicked)
                    }
                },
                onBottomTextClicked = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                })
        },
        sheetElevation = 20.dp,
        sheetGesturesEnabled = false,
        sheetBackgroundColor = MaterialTheme.colors.secondary,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    ) {
        MainScreenContent(onBoardingViewModel, bottomSheetScaffoldState, state)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainScreenContent(
    onBoardingViewModel: WorkAppsOnboardingViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    state: WorkAppListOnboardingScreenState
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
                progress=1f,
                title= stringResource(id = R.string.which_app_are_used_for_work),
                description= stringResource(id = R.string.select_work_apps),
                modifier = Modifier
                    .constrainAs(topBody) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        width = Dimension.fillToConstraints
                    }
            )
            MainBodyContent(
                onBoardingViewModel, state,
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

            BottomButtonContent(
                buttonText=if (state.checkedItems== ZERO) null else "${stringResource(id = R.string.next)} (${state.checkedItems})",
                stateDisabled = state.disableButton,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 0.dp)
                    .constrainAs(nextButton) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                if (!state.disableButton){
                    onBoardingViewModel.onEventUpdate(WorkAppListOnBoardingScreenEvent.OnNextClicked)
                }
            }
        }
    }
}

@Composable
private fun MainBodyContent(
    onBoardingViewModel: WorkAppsOnboardingViewModel,
    state: WorkAppListOnboardingScreenState,
    modifier: Modifier,
) {
    val context = LocalContext.current
    state.apply {
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ){
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
                    onBoardingViewModel.onEventUpdate(WorkAppListOnBoardingScreenEvent.SearchAppTextUpdated(it))
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
            if (filteredList.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    itemsIndexed(
                        if (expandedList||searchText.isNotEmpty()||filteredList.size<=FIVE) filteredList else filteredList.subList(
                            ZERO,
                            FIVE
                        )
                    ) {index, item ->
                        AppItems(app = item.first, info = item.second){selected->
                            onBoardingViewModel.onEventUpdate(
                                WorkAppListOnBoardingScreenEvent.OnAppSelected(
                                    index, Pair(item.first,item.second.copy(isChecked = selected))
                                )
                            )
                        }
                    }
                }
                if (searchText.isEmpty()&&filteredList.size> FIVE){
                    Text(
                        text = if (expandedList) context.getString(R.string.show_less) else context.getString(R.string.show_more_apps, workAppsList.size - FIVE),
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSecondary,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .clickable {
                                onBoardingViewModel.onEventUpdate(
                                    WorkAppListOnBoardingScreenEvent.OnExpandAppList(
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

@Composable
private fun AppItems(app: ApplicationInfo?, info: AppInfo, onClick: (Boolean) -> Unit) {
    app?.let { appInfo ->
        val appIcon = LocalContext.current.getAppIconBitmap(appInfo.packageName)
        SelectableItem(
            modifier=Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            bitmap = appIcon,
            painter = if (appIcon==null) painterResource(id = R.drawable.ic_android) else null,
            textTitle = info.realAppName ?: EMPTY,
            isChecked = info.isChecked
        ) {
            onClick(!info.isChecked)
        }
    }
}

private fun navigateOccupationQuestionnaireScreen(navController: NavController) {
    navController.popBackStack()
    navController.navigate(Screen.OccupationScreenRoute.route)
}