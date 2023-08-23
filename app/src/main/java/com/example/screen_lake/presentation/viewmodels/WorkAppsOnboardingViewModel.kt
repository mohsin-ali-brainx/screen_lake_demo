package com.example.screen_lake.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Constants
import com.example.screen_lake.appUtils.Constants.IntegerConstants.THREE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.TWO
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.base.OnboardingBaseViewModel
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.domain.useCases.WorkAppListUseCase
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

data class WorkAppListOnboardingScreenState(
    val isLoading: Boolean = false,
    val disableButton:Boolean=true,
    val searchText:String = Constants.StringConstants.EMPTY,
    val workAppsList:List<AppInfo> = arrayListOf(),
    val filteredList:List<AppInfo> = arrayListOf(),
    val expandedList:Boolean=false,
    val checkedItems:Int=0,
    val allAppInfoList : List<AppInfo> = arrayListOf(),
    val progress:Float = 0.75f
)

sealed class WorkAppListOnBoardingScreenEvent{
    data class SearchAppTextUpdated(val newText: String) : WorkAppListOnBoardingScreenEvent()
    data class OnAppSelected(val index:Int,val app: AppInfo):
        WorkAppListOnBoardingScreenEvent()
    data class OnExpandAppList(val expand:Boolean) : WorkAppListOnBoardingScreenEvent()
    object OnNextClicked : WorkAppListOnBoardingScreenEvent()
    object OnAnswerQuestionsButtonClicked : WorkAppListOnBoardingScreenEvent()
}

sealed class WorkAppAppListOnBoardingScreenUiEvents{
    object OpenQuestionsBottomSheet: WorkAppAppListOnBoardingScreenUiEvents()
    object OpenOccupationQuestionnaireScreen: WorkAppAppListOnBoardingScreenUiEvents()
}
@HiltViewModel
class WorkAppsOnboardingViewModel @Inject constructor(
    private val getWorkAppListUseCase: WorkAppListUseCase
): OnboardingBaseViewModel() {
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
                    insertWorkApp(app)
                    val newList =  ArrayList<AppInfo>().apply {
                        clear()
                        addAll(_state.value.workAppsList)
                    }
                    val newFilteredList = ArrayList<AppInfo>().apply {
                        clear()
                        addAll(_state.value.filteredList)
                    }
                    if (_state.value.searchText.isEmpty()){
                        newList[index]=app
                        newFilteredList[index]=app
                    }else{
                        var position= ZERO
                        newList.filterIndexed { index, item ->
                            position = index
                            item.apk == app.apk
                        }
                        newList[position]=app
                        newFilteredList[index]=app
                    }
                    val checkedItems = newList.filter { it.isChecked }.size
                    _state.value= _state.value.copy(workAppsList = newList, filteredList = newFilteredList, disableButton = checkedItems==ZERO, checkedItems = checkedItems, progress =  getProgress(if (checkedItems== ZERO)TWO else THREE))
                }
                is WorkAppListOnBoardingScreenEvent.SearchAppTextUpdated ->{
                    val filteredList = _state.value.workAppsList.filter { it.doesMatchSearchQuery(newText) }
                    _state.value = _state.value.copy(searchText = newText, filteredList = filteredList)
                }
                is WorkAppListOnBoardingScreenEvent.OnExpandAppList ->{
                    _state.value = _state.value.copy(expandedList = expand)
                }
                is WorkAppListOnBoardingScreenEvent.OnNextClicked ->{
                    insertOnboardingTracker()
                    viewModelScope.launch { _eventFlow.emit(WorkAppAppListOnBoardingScreenUiEvents.OpenQuestionsBottomSheet) }
                }
                is WorkAppListOnBoardingScreenEvent.OnAnswerQuestionsButtonClicked ->{
                    insertOnboardingTracker()
                    viewModelScope.launch { _eventFlow.emit(WorkAppAppListOnBoardingScreenUiEvents.OpenOccupationQuestionnaireScreen) }
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
                        val checkedItems = this.filter { it.isChecked }.size
                        _state.value = _state.value.copy(isLoading = false, workAppsList = this, filteredList = this, checkedItems = checkedItems, disableButton =checkedItems== ZERO,
                            progress =  getProgress(if (checkedItems== ZERO)TWO else THREE))
                    }
                }
                is Resource.Error->{}
                else -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun insertWorkApp(appInfo: AppInfo){
        viewModelScope.launch(Dispatchers.IO) {
            val existingAppInfo =  repository.getAppInfoFromPackageName(appInfo.apk)?.apply{
                appPrimaryUse = appInfo.appPrimaryUse
                isChecked = appInfo.isChecked
            }
            repository.insertAppInfo(existingAppInfo?:appInfo)
        }
    }
    // end region
}