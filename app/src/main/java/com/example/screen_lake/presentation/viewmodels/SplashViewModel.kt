package com.example.screen_lake.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.enums.OnboardingTrackStep
import com.example.screen_lake.dataSource.repositoryImp.OnboardingRepositoryImp
import com.example.screen_lake.presentation.navigation.getScreenFromStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashScreenUiEvents{
    data class NavigateToOnboardingScreen(val route:String): SplashScreenUiEvents()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: OnboardingRepositoryImp
):ViewModel() {
    private val _eventFlow = MutableSharedFlow<SplashScreenUiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        checkOnboardingTracker()
    }
    private fun checkOnboardingTracker(){
        viewModelScope.launch(Dispatchers.IO) {
            updateEvent(SplashScreenUiEvents.NavigateToOnboardingScreen(getScreenFromStep(repository.getOnboardingTracker().firstOrNull()?.step?:OnboardingTrackStep.APP_LIST_SCREEN_STEP.step).route))
        }
    }

    private fun updateEvent(events: SplashScreenUiEvents){
        viewModelScope.launch {
            _eventFlow.emit(events)
        }
    }
}