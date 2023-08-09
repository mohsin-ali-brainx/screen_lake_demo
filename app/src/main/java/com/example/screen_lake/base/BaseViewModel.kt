package com.example.screen_lake.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_lake.enums.OnboardingTrackStep
import com.example.screen_lake.models.OnboardingTracker
import com.example.screen_lake.repository.OnboardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    @Inject
    lateinit var repository:OnboardingRepository

    fun insertOnboardingTracker(){
        viewModelScope.launch(Dispatchers.IO){
            repository.apply {
                getOnboardingTracker().firstOrNull().let {
                    if (it==null){
                        insertOnboardingTracker(OnboardingTracker(step = OnboardingTrackStep.APP_LIST_SCREEN_STEP.step, started = true))
                    }else{
                        insertOnboardingTracker(it.apply { step++ })
                    }
                }
            }
        }
    }
}