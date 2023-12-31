package com.example.screen_lake.presentation.screens.questions.occupation

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
import androidx.navigation.NavHostController
import com.example.screen_lake.R
import com.example.screen_lake.presentation.utils.BottomButtonContent
import com.example.screen_lake.presentation.utils.SelectableItem
import com.example.screen_lake.presentation.utils.TopBodyContent
import com.example.screen_lake.presentation.viewmodels.OccupationQuestionnaireScreenEvent
import com.example.screen_lake.presentation.viewmodels.OccupationQuestionnaireScreenState
import com.example.screen_lake.presentation.viewmodels.OccupationQuestionnaireScreenUiEvents
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
@ExperimentalMaterialApi
fun OccupationQuestionnaireOnboardingScreen(
    navHostController: NavHostController,
    dataState:StateFlow<OccupationQuestionnaireScreenState>,
    uiEvents:SharedFlow<OccupationQuestionnaireScreenUiEvents>,
    onEvent:(OccupationQuestionnaireScreenEvent)->Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val state by dataState.collectAsState()

    LaunchedEffect(key1 = true){
        uiEvents.collectLatest {
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
        MainScreenContent(paddingValues = it,state){
            onEvent(it)
        }
    }
}

@Composable
private fun MainScreenContent(
    paddingValues: PaddingValues,
    state: OccupationQuestionnaireScreenState,
    onEvent:(OccupationQuestionnaireScreenEvent)->Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize(1f)
            .padding(bottom = 16.dp, top = 0.dp)
    ){
        val (nextButton, topBody, body) = createRefs()
        TopBodyContent(
            progress=state.progress,
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
            state,
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

                }
            },
            onBottomTextClicked = {
                
            }
        )
    }
}

@Composable
private fun MainBodyContent(
    state: OccupationQuestionnaireScreenState,
    modifier: Modifier,
    onEvent:(OccupationQuestionnaireScreenEvent)->Unit
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
                onEvent(OccupationQuestionnaireScreenEvent.OnOccupationSelected(index,item.copy(isChecked = !item.isChecked)))
            }
        }
    }

}