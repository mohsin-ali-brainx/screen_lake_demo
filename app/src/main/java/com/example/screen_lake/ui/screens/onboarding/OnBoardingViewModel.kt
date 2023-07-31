package com.example.screen_lake.ui.screens.onboarding

import android.content.pm.ApplicationInfo
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.enums.AppDistractions
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.ui.screens.onboarding.appListOnboarding.useCase.InstalledAppInfoUseCase
import com.example.screenlake.utils.Constants.StringConstants.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class OnboardingScreenState(
    val isLoading: Boolean = false,
    val searchText:String = EMPTY,
    val installedApps:List<Pair<ApplicationInfo,AppInfo>> = emptyList()
)

sealed class OnBoardingScreenUiEvent{
    data class SearchAppTextUpdated(val newText: String) : OnBoardingScreenUiEvent()
    data class OnAppSelected(val app:ApplicationInfo,val distraction:AppDistractions):OnBoardingScreenUiEvent()
}

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getInstalledAppInfoUseCase: InstalledAppInfoUseCase
):ViewModel(){
    // region properties
    private val _state = mutableStateOf(OnboardingScreenState())
    val state : State<OnboardingScreenState>
        get()=_state

    private val _eventFlow = MutableSharedFlow<OnBoardingScreenUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    // end region
    init {
        getInstalledAppInfo()
    }
    // region public method
    fun onStateUpdate(state: OnboardingScreenState){
//        state.apply {
//            when(this){
//                is OnboardingScreenState.SearchAppTextUpdated->{
//                    _searchText.value =newText
//                }
//                else -> {}
//            }
//        }
    }

    fun onEventUpdate(event: OnBoardingScreenUiEvent){
        event.apply {
            when(this){
                is OnBoardingScreenUiEvent.OnAppSelected->{

                }
                is OnBoardingScreenUiEvent.SearchAppTextUpdated->{
                    _state.value = _state.value.copy(searchText = newText)
                }
                else -> {}
            }
        }
    }
    // end region
    // region private methods
    private fun getInstalledAppInfo() {
        getInstalledAppInfoUseCase().onEach {resource->
            when (resource){
                is Resource.Success->{
                    resource.data?.apply {
                        _state.value = _state.value.copy(isLoading = false, installedApps = this)
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

    // end region

}