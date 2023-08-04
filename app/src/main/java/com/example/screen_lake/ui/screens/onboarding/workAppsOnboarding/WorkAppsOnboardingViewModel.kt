package com.example.screen_lake.ui.screens.onboarding.workAppsOnboarding

import android.content.pm.ApplicationInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.ui.screens.onboarding.workAppsOnboarding.useCase.WorkAppListUseCase
import com.example.screenlake.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WorkAppListOnboardingScreenState(
    val isLoading: Boolean = false,
    val disableButton:Boolean=true,
    val searchText:String = Constants.StringConstants.EMPTY,
    val workAppsList:List<Pair<ApplicationInfo, AppInfo>> = arrayListOf(),
    val filteredList:List<Pair<ApplicationInfo, AppInfo>> = arrayListOf(),
    val expandedList:Boolean=false,
    val checkedItems:Int=0
)

sealed class WorkAppListOnBoardingScreenEvent{
    data class SearchAppTextUpdated(val newText: String) : WorkAppListOnBoardingScreenEvent()
    data class OnAppSelected(val index:Int,val app:Pair<ApplicationInfo,AppInfo>):
        WorkAppListOnBoardingScreenEvent()
    data class OnExpandAppList(val expand:Boolean) : WorkAppListOnBoardingScreenEvent()
    object OnNextClicked : WorkAppListOnBoardingScreenEvent()
}

sealed class WorkAppAppListOnBoardingScreenUiEvents{
    object OpenQuestionsBottomSheet:WorkAppAppListOnBoardingScreenUiEvents()
}
@HiltViewModel
class WorkAppsOnboardingViewModel @Inject constructor(
    private val getWorkAppListUseCase: WorkAppListUseCase
):ViewModel() {
    // region properties
    private val _state = MutableStateFlow(WorkAppListOnboardingScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()
    // end region
    init {
        getWorkAppListInfo()
    }
    // region public method
    fun onEventUpdate(event: WorkAppListOnBoardingScreenEvent){
        event.apply {
            when(this){
                is WorkAppListOnBoardingScreenEvent.OnAppSelected ->{
                    val newList =  ArrayList<Pair<ApplicationInfo,AppInfo>>().apply {
                        clear()
                        addAll(_state.value.workAppsList)
                    }
                    val newFilteredList = ArrayList<Pair<ApplicationInfo,AppInfo>>().apply {
                        clear()
                        addAll(_state.value.filteredList)
                    }
                    if (_state.value.searchText.isEmpty()){
                        newList[index]=app
                        newFilteredList[index]=app
                    }else{
                        var position= Constants.IntegerConstants.ZERO
                        newList.filterIndexed { index, pair ->
                            position = index
                            pair.second.apk == app.second.apk
                        }
                        newList[position]=app
                        newFilteredList[index]=app
                    }
                    val checkedItems = newList.filter { it.second.isChecked }.size
                    _state.value= _state.value.copy(workAppsList = newList, filteredList = newFilteredList, disableButton = false, checkedItems = checkedItems)
                }
                is WorkAppListOnBoardingScreenEvent.SearchAppTextUpdated ->{
                    val filteredList = _state.value.workAppsList.filter { it.second.doesMatchSearchQuery(newText) }
                    _state.value = _state.value.copy(searchText = newText, filteredList = filteredList)
                }
                is WorkAppListOnBoardingScreenEvent.OnExpandAppList ->{
                    _state.value = _state.value.copy(expandedList = expand)
                }
                is WorkAppListOnBoardingScreenEvent.OnNextClicked->{
                    viewModelScope.launch { _eventFlow.emit(WorkAppAppListOnBoardingScreenUiEvents.OpenQuestionsBottomSheet) }
                }
                else->{}
            }
        }
    }
    // end region
    // region private methods
    private fun getWorkAppListInfo(){
        getWorkAppListUseCase().onEach {resource->
            when (resource){
                is Resource.Success->{
                    resource.data?.apply {
                        _state.value = _state.value.copy(isLoading = false, workAppsList = this, filteredList = this)
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