package com.example.screen_lake.ui.screens.onboarding.behaviourOnboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.screen_lake.R
import com.example.screen_lake.enums.AppBehaviors
import com.example.screen_lake.enums.getAppBehaviorFromImportance
import com.example.screen_lake.enums.getAppBehaviorList
import com.example.screen_lake.models.Behavior
import com.example.screen_lake.navigation.Screen
import com.example.screen_lake.ui.utils.BottomButtonContent
import com.example.screen_lake.ui.utils.DropDownSelectionItem
import com.example.screen_lake.ui.utils.OptionSelectedItem
import com.example.screen_lake.ui.utils.TopBodyContent
import com.example.screenlake.utils.Constants
import kotlinx.coroutines.flow.collectLatest

@Composable
@ExperimentalMaterialApi
fun BehaviorOnboardingScreen(
    navHostController: NavHostController,
    onBoardingViewModel: BehaviorOnboardingViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val state by onBoardingViewModel.state.collectAsState()

    LaunchedEffect(key1 = true){
        onBoardingViewModel.eventFlow.collectLatest {
            when(it){
                is BehaviorOnBoardingScreenUiEvents.NavigateToWorkAppsOnboardingScreen->{
                    navigateToWorkAppsOnBoardingScreen(navHostController)
                }
                else->{}
            }
        }
    }

    Scaffold(
        scaffoldState=scaffoldState
    ){
        MainScreenContent(paddingValues = it,onBoardingViewModel,state)
    }
}

@Composable
private fun MainScreenContent(
    paddingValues: PaddingValues,
    onBoardingViewModel: BehaviorOnboardingViewModel,
    state: BehaviorOnboardingScreenState
) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize(1f)
            .padding(bottom = 16.dp, top = 0.dp)
    ){
        val (nextButton, topBody, body) = createRefs()

        TopBodyContent(
            progress=0.75f,
            title= stringResource(id = R.string.onboarding_distracting_behavior_title),
            description= stringResource(id = R.string.onboarding_distracting_behavior_description),
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
                onBoardingViewModel.onEventUpdate(BehaviorOnBoardingScreenEvent.OnNextClicked)
            }
        }

    }
}

@Composable
private fun MainBodyContent(
    onBoardingViewModel: BehaviorOnboardingViewModel,
    state: BehaviorOnboardingScreenState,
    modifier: Modifier,
) {
    LazyColumn(
        modifier= modifier,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        itemsIndexed(state.appBehaviors){index, item ->
            BehaviorItems(item){
                item.importance = it.importance
                onBoardingViewModel.onEventUpdate(BehaviorOnBoardingScreenEvent.OnBehaviorSelected(index,item))
            }
        }
    }

}

@Composable
private fun BehaviorItems(behavior: Behavior,onClick: (AppBehaviors)->Unit){
    behavior.apply {
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
        ){
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(vertical = 6.dp, horizontal = 12.dp)
            ) {
                val (title, action) = createRefs()
                Text(
                    text = name,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(action.start, margin = 4.dp)
                            width = Dimension.fillToConstraints
                        }
                        .padding(horizontal = 8.dp),
                    maxLines = 1,
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.End)
                        .constrainAs(action){
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    getAppBehaviorFromImportance(importance).apply{
                        OptionSelectedItem(text=behaviorName,background=background, textColor = color)
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = MaterialTheme.colors.primaryVariant,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                isContextMenuVisible = true
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = if (importance == AppBehaviors.NOT_DEFINED.importance) Icons.Default.Add else Icons.Outlined.Edit,
                            contentDescription = Constants.StringConstants.EMPTY,
                            tint = MaterialTheme.colors.surface,
                        )
                        BehaviorDropDownMenu(
                            isContextMenuVisible = isContextMenuVisible,
                            selectedKey = importance,
                            onClick = {visible, selectedItem ->
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
}

@Composable
private fun BehaviorDropDownMenu(
    isContextMenuVisible: Boolean,
    selectedKey: Int,
    onClick: (Boolean, AppBehaviors) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = isContextMenuVisible,
        onDismissRequest = { onDismiss(false) },
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.background,
            )
    ){
        getAppBehaviorList().forEach { behavior->
            DropDownSelectionItem(
                key=behavior.importance,
                text=behavior.behaviorName,
                background = behavior.color,
                selectedKey=selectedKey,
                onClick = { onClick(false,behavior) } )
        }
    }
}

private fun navigateToWorkAppsOnBoardingScreen(navController: NavController) {
    navController.popBackStack()
    navController.navigate(Screen.WorkAppsOnboardingScreenRoute.route)
}