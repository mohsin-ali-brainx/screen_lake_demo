package com.example.screen_lake.ui.screens.onboarding

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.ui.screens.onboarding.appListOnboarding.useCase.InstalledAppInfoUseCase
import com.example.screenlake.utils.Constants.StringConstants.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class OnboardingScreenState(
    val isLoading: Boolean = false,
    val disableButton:Boolean=true,
    val searchText:String = EMPTY,
    val installedApps:List<Pair<ApplicationInfo,AppInfo>> = arrayListOf(),
    val expandedList:Boolean=false
)

sealed class OnBoardingScreenUiEvent{
    data class SearchAppTextUpdated(val newText: String) : OnBoardingScreenUiEvent()
    data class OnAppSelected(val index:Int,val app:Pair<ApplicationInfo,AppInfo>):OnBoardingScreenUiEvent()
    data class OnExpandAppList(val expand:Boolean) : OnBoardingScreenUiEvent()
}

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getInstalledAppInfoUseCase: InstalledAppInfoUseCase
):ViewModel(){
    // region properties
    private val _state = MutableStateFlow(OnboardingScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<OnBoardingScreenUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    // end region
    init {
        getInstalledAppInfo()
    }
    // region public method
    fun onEventUpdate(event: OnBoardingScreenUiEvent){
        event.apply {
            when(this){
                is OnBoardingScreenUiEvent.OnAppSelected->{
                    val newList =  ArrayList<Pair<ApplicationInfo,AppInfo>>().apply {
                        clear()
                        addAll( _state.value.installedApps)
                    }
                    newList[index]=app
                    _state.value= _state.value.copy(installedApps = newList, disableButton = false)
                }
                is OnBoardingScreenUiEvent.SearchAppTextUpdated->{
                    _state.value = _state.value.copy(searchText = newText)
                }
                is OnBoardingScreenUiEvent.OnExpandAppList->{
                    _state.value = _state.value.copy(expandedList = expand)
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