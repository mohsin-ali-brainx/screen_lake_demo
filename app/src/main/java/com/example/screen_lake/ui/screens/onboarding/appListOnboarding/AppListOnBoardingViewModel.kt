package com.example.screen_lake.ui.screens.onboarding.appListOnboarding

import android.content.pm.ApplicationInfo
import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.base.BaseViewModel
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.models.OnboardingTracker
import com.example.screen_lake.sharedPreference.SharedPreference
import com.example.screen_lake.ui.screens.onboarding.appListOnboarding.useCase.InstalledAppInfoUseCase
import com.example.screen_lake.ui.screens.useCases.GetOnboardingTrackerUseCase
import com.example.screenlake.utils.Constants.IntegerConstants.ZERO
import com.example.screenlake.utils.Constants.StringConstants.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppListOnboardingScreenState(
    val isLoading: Boolean = false,
    val disableButton:Boolean=true,
    val searchText:String = EMPTY,
    val installedApps:List<Pair<ApplicationInfo,AppInfo>> = arrayListOf(),
    val filteredList:List<Pair<ApplicationInfo,AppInfo>> = arrayListOf(),
    val expandedList:Boolean=false,
    val dismissBottomSheet:Boolean=false
)

sealed class AppListOnBoardingScreenEvent{
    data class SearchAppTextUpdated(val newText: String) : AppListOnBoardingScreenEvent()
    data class OnAppSelected(val index:Int,val app:Pair<ApplicationInfo,AppInfo>):
        AppListOnBoardingScreenEvent()
    data class OnExpandAppList(val expand:Boolean) : AppListOnBoardingScreenEvent()
    object OnNextClicked : AppListOnBoardingScreenEvent()

    data class UpdateOnBoardingTracker(val tracker: OnboardingTracker): AppListOnBoardingScreenEvent()

}

sealed class AppListOnBoardingScreenUiEvents{
    object NavigateToBehaviorOnboardingScreen:AppListOnBoardingScreenUiEvents()
    object DismissBottomSheet:AppListOnBoardingScreenUiEvents()
}

@HiltViewModel
class AppListOnBoardingViewModel @Inject constructor(
    private val sharedPreference: SharedPreference,
    private val getInstalledAppInfoUseCase: InstalledAppInfoUseCase,
    private val getOnboardingTrackerUseCase: GetOnboardingTrackerUseCase
): BaseViewModel(){
    // region properties
    private val _state = MutableStateFlow(AppListOnboardingScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<AppListOnBoardingScreenUiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()
    // end region
    init {
        getInstalledAppInfo()
    }
    // region public method
    fun onEventUpdate(event: AppListOnBoardingScreenEvent){
        event.apply {
            when(this){
                is AppListOnBoardingScreenEvent.OnAppSelected ->{
                    val newList =  ArrayList<Pair<ApplicationInfo,AppInfo>>().apply {
                        clear()
                        addAll(_state.value.installedApps)
                    }
                    val newFilteredList = ArrayList<Pair<ApplicationInfo,AppInfo>>().apply {
                        clear()
                        addAll(_state.value.filteredList)
                    }
                    if (_state.value.searchText.isEmpty()){
                        newList[index]=app
                        newFilteredList[index]=app
                    }else{
                        var position=ZERO
                         newList.filterIndexed { index, pair ->
                             position = index
                             pair.second.apk == app.second.apk
                        }
                        newList[position]=app
                        newFilteredList[index]=app
                    }
                    _state.value= _state.value.copy(installedApps = newList, filteredList = newFilteredList, disableButton = false)
                }
                is AppListOnBoardingScreenEvent.SearchAppTextUpdated ->{
                    val filteredList = _state.value.installedApps.filter { it.second.doesMatchSearchQuery(newText) }
                    _state.value = _state.value.copy(searchText = newText, filteredList = filteredList)
                }
                is AppListOnBoardingScreenEvent.OnExpandAppList ->{
                    _state.value = _state.value.copy(expandedList = expand)
                }
                is AppListOnBoardingScreenEvent.OnNextClicked ->{
                    viewModelScope.launch {
                        _eventFlow.emit(AppListOnBoardingScreenUiEvents.NavigateToBehaviorOnboardingScreen)
                    }
                }
                is AppListOnBoardingScreenEvent.UpdateOnBoardingTracker->{
                    deleteAndInsertOnboardingTracker(tracker)
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
                        _state.value = _state.value.copy(isLoading = false, installedApps = this, filteredList = this)
                    }
                }
                is Resource.Error->{}
                else -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    // end region

}