package com.example.screen_lake.ui.screens.onboarding.questions.occupation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.screen_lake.R
import com.example.screen_lake.ui.utils.BottomButtonContent
import com.example.screen_lake.ui.utils.SelectableItem
import com.example.screen_lake.ui.utils.TopBodyContent
import kotlinx.coroutines.flow.collectLatest

@Composable
@ExperimentalMaterialApi
fun OccupationQuestionnaireOnboardingScreen(
    navHostController: NavHostController,
    onBoardingViewModel: OccupationQuestionnaireViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val state by onBoardingViewModel.state.collectAsState()

    LaunchedEffect(key1 = true){
        onBoardingViewModel.eventFlow.collectLatest {
            when(it){
                is OccupationQuestionnaireScreenUiEvents.OpenAgeQuestionnaireScreen->{

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
    onBoardingViewModel: OccupationQuestionnaireViewModel,
    state: OccupationQuestionnaireScreenState
) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize(1f)
            .padding(bottom = 16.dp, top = 0.dp)
    ){
        val (nextButton, topBody, body) = createRefs()
        TopBodyContent(
            progress=0.5f,
            title= stringResource(id = R.string.what_occupation),
            description= stringResource(id = R.string.select_the_scope),
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

            }
        }
    }
}

@Composable
private fun MainBodyContent(
    onBoardingViewModel: OccupationQuestionnaireViewModel,
    state: OccupationQuestionnaireScreenState,
    modifier: Modifier,
) {
    LazyColumn(
        modifier= modifier,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        itemsIndexed(state.occupation){index, item ->
            SelectableItem(
                modifier=Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                bitmap = null,
                painter = painterResource(id = item.image),
                textTitle = item.text,
                isChecked = item.isChecked,
                imageContentScale = ContentScale.None
            ) {
                onBoardingViewModel.onEventUpdate(OccupationQuestionnaireScreenEvent.OnOccupationSelected(index,item.copy(isChecked = !item.isChecked)))
            }
        }
    }

}