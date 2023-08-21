package com.example.screen_lake.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.appUtils.enums.AppDistractions
import com.example.screen_lake.base.BaseViewModel
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.domain.useCases.InstalledAppInfoWithDistractionUseCase
import com.example.screenlake.utils.Constants.IntegerConstants.ZERO
import com.example.screenlake.utils.Constants.StringConstants.EMPTY
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

data class AppListOnboardingScreenState(
    val isLoading: Boolean = false,
    val disableButton:Boolean=true,
    val searchText:String = EMPTY,
    val installedApps:List<AppInfo> = arrayListOf(),
    val filteredList:List<AppInfo> = arrayListOf(),
    val expandedList:Boolean=false,
    val dismissBottomSheet:Boolean=false,
    val progress:Float = 0.0f
)

sealed class AppListOnBoardingScreenEvent{
    data class SearchAppTextUpdated(val newText: String) : AppListOnBoardingScreenEvent()
    data class OnAppSelected(val index:Int,val app: AppInfo): AppListOnBoardingScreenEvent()
    data class OnExpandAppList(val expand:Boolean) : AppListOnBoardingScreenEvent()
    object OnNextClicked : AppListOnBoardingScreenEvent()
    object UpdateOnBoardingTracker: AppListOnBoardingScreenEvent()

}

sealed class AppListOnBoardingScreenUiEvents{
    object NavigateToBehaviorOnboardingScreen: AppListOnBoardingScreenUiEvents()
}

@HiltViewModel
class AppListOnBoardingViewModel @Inject constructor(
    private val getInstalledAppInfoWithDistractionUseCase: InstalledAppInfoWithDistractionUseCase,
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
                    insertAppInfo(app)
                    val newList =  ArrayList<AppInfo>().apply {
                        clear()
                        addAll(_state.value.installedApps)
                    }
                    val newFilteredList = ArrayList<AppInfo>().apply {
                        clear()
                        addAll(_state.value.filteredList)
                    }
                    if (_state.value.searchText.isEmpty()){
                        newList[index]=app
                        newFilteredList[index]=app
                    }else{
                        var position=ZERO
                         newList.filterIndexed { index, item ->
                             position = index
                             item.apk == app.apk
                        }
                        newList[position]=app
                        newFilteredList[index]=app
                    }
                    _state.value= _state.value.copy(installedApps = newList, filteredList = newFilteredList, disableButton = false, progress = 0.5f)
                }
                is AppListOnBoardingScreenEvent.SearchAppTextUpdated ->{
                    val filteredList = _state.value.installedApps.filter { it.doesMatchSearchQuery(newText) }
                    _state.value = _state.value.copy(searchText = newText, filteredList = filteredList)
                }
                is AppListOnBoardingScreenEvent.OnExpandAppList ->{
                    _state.value = _state.value.copy(expandedList = expand)
                }
                is AppListOnBoardingScreenEvent.OnNextClicked ->{
                    insertOnboardingTracker()
                    viewModelScope.launch {
                        _eventFlow.emit(AppListOnBoardingScreenUiEvents.NavigateToBehaviorOnboardingScreen)
                    }
                }
                is AppListOnBoardingScreenEvent.UpdateOnBoardingTracker ->{
                    insertOnboardingTracker()
                }
                else -> {}
            }
        }
    }
    // end region
    // region private methods
    private fun getInstalledAppInfo() {
        getInstalledAppInfoWithDistractionUseCase().onEach { resource->
            when (resource){
                is Resource.Success->{
                    resource.data?.apply {
                        val disableButton =  filter { it.distractionLevel!= AppDistractions.NOT_DEFINED.key }.isEmpty()
                        _state.value = _state.value.copy(isLoading = false, installedApps = this, filteredList = this, disableButton = disableButton, progress = if (disableButton) 0.0f else 0.5f)
                    }
                }
                is Resource.Error->{}
                else -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertAppInfo(appInfo: AppInfo){
        viewModelScope.launch(Dispatchers.IO) {
            val existingAppInfo =  repository.getAppInfoFromPackageName(appInfo.apk)?.apply {
                distractionLevel = appInfo.distractionLevel
            }
            repository.insertAppInfo(existingAppInfo?:appInfo)
        }
    }
    // end region

}