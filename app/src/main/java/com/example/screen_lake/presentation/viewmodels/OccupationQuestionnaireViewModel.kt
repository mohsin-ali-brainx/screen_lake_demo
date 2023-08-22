package com.example.screen_lake.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.base.OnboardingBaseViewModel
import com.example.screen_lake.domain.models.GenericSelectionModel
import com.example.screen_lake.domain.useCases.GetOccupationList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class OccupationQuestionnaireScreenState(
    val isLoading: Boolean = false,
    val disableButton:Boolean=true,
    val occupation:List<GenericSelectionModel> = arrayListOf(),
    val progress : Float = 0f
)

sealed class OccupationQuestionnaireScreenEvent{
    data class OnOccupationSelected(val index:Int,val item: GenericSelectionModel):
        OccupationQuestionnaireScreenEvent()
}

sealed class OccupationQuestionnaireScreenUiEvents{
    object OpenAgeQuestionnaireScreen: OccupationQuestionnaireScreenUiEvents()
}
@HiltViewModel
class OccupationQuestionnaireViewModel @Inject constructor(
    private val getOccupationList: GetOccupationList
): OnboardingBaseViewModel() {
    // region properties
    private val _state = MutableStateFlow(OccupationQuestionnaireScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<OccupationQuestionnaireScreenUiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()
    // end region
    init {
        getOccupations()
    }
    // region public methods
    fun onEventUpdate(event: OccupationQuestionnaireScreenEvent){
        event.apply {
            when(this){
                is OccupationQuestionnaireScreenEvent.OnOccupationSelected ->{
                    val newList = ArrayList<GenericSelectionModel>().apply {
                        clear()
                        _state.value.occupation.forEach {
                            add(it.copy(isChecked = false))
                        }
                    }
                    newList[index]=item
                    _state.value = _state.value.copy(disableButton = !item.isChecked, occupation = newList, progress = if (item.isChecked) 0.5f else 0.0f)
                }
                else->{}
            }
        }
    }
    // end region
    // region private methods
    private fun getOccupations(){
        getOccupationList().onEach {
                resource->
            when (resource){
                is Resource.Success->{
                    resource.data?.apply {
                        _state.value = _state.value.copy(isLoading = false, occupation = this)
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