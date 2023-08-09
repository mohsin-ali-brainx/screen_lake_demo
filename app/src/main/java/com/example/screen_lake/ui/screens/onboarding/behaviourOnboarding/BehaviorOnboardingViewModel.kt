package com.example.screen_lake.ui.screens.onboarding.behaviourOnboarding

import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.base.BaseViewModel
import com.example.screen_lake.enums.AppBehaviors
import com.example.screen_lake.models.Behavior
import com.example.screen_lake.ui.screens.onboarding.behaviourOnboarding.useCase.AppBehaviorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BehaviorOnboardingScreenState(
    val isLoading: Boolean = false,
    val disableButton:Boolean=true,
    val appBehaviors:List<Behavior> = arrayListOf()
)

sealed class BehaviorOnBoardingScreenEvent{
    data class OnBehaviorSelected(val index:Int, val behavior: Behavior): BehaviorOnBoardingScreenEvent()
    object OnNextClicked : BehaviorOnBoardingScreenEvent()
}

sealed class BehaviorOnBoardingScreenUiEvents{
    object NavigateToWorkAppsOnboardingScreen:BehaviorOnBoardingScreenUiEvents()
}
@HiltViewModel
class BehaviorOnboardingViewModel @Inject constructor(
    private val getAppBehaviorsUseCase: AppBehaviorUseCase
): BaseViewModel(){
    // region properties
    private val _state = MutableStateFlow(BehaviorOnboardingScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<BehaviorOnBoardingScreenUiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()
    // end region
    init {
        appBehaviorList()
    }
    // region public methods
    fun onEventUpdate(event: BehaviorOnBoardingScreenEvent){
        event.apply {
            when(this){
                is BehaviorOnBoardingScreenEvent.OnBehaviorSelected->{
                    insertBehavior(behavior)
                   val newList = ArrayList<Behavior>().apply {
                       clear()
                       addAll(_state.value.appBehaviors)
                   }
                    newList[index]=behavior
                    _state.value=_state.value.copy(appBehaviors = newList, disableButton = false)
                }
                is BehaviorOnBoardingScreenEvent.OnNextClicked->{
                    insertOnboardingTracker()
                    viewModelScope.launch {
                        _eventFlow.emit(BehaviorOnBoardingScreenUiEvents.NavigateToWorkAppsOnboardingScreen)
                    }
                }
                else->{}
            }
        }
    }
    // end region
    // region private methods
    private fun appBehaviorList(){
        getAppBehaviorsUseCase().onEach { resource ->
            when (resource){
                is Resource.Success->{
                    resource.data?.apply {
                        _state.value = _state.value.copy(isLoading = false, appBehaviors = this, disableButton = filter { it.importance!=AppBehaviors.NOT_DEFINED.importance }.isEmpty())
                    }
                }
                is Resource.Error->{

                }
                else -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertBehavior(behavior: Behavior){
        viewModelScope.launch(Dispatchers.IO) {
            val existingBehavior = repository.getAppBehaviorByName(behavior.name)?.apply {
               importance = behavior.importance
            }
            repository.insertBehaviorInfo(existingBehavior?:behavior)
        }
    }
    // end region
}