package com.example.screen_lake.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screen_lake.appUtils.Dispatcher
import com.example.screen_lake.appUtils.SiftDispatchers
import com.example.screen_lake.appUtils.enums.OnboardingTrackStep
import com.example.screen_lake.dataSource.repositoryImp.OnboardingRepositoryImp
import com.example.screen_lake.domain.models.OnboardingTracker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

open class OnboardingBaseViewModel : ViewModel() {
    @Inject
    lateinit var repository: OnboardingRepositoryImp

    @Inject
    @Dispatcher(SiftDispatchers.IO) lateinit var  ioDispatcher: CoroutineDispatcher

    fun insertOnboardingTracker(){
        viewModelScope.launch(ioDispatcher){
            repository.apply {
                getOnboardingTracker().firstOrNull().let {
                    if (it==null){
                        insertOnboardingTracker(OnboardingTracker(step = OnboardingTrackStep.APP_LIST_SCREEN_STEP.step, started = true))
                    }else{
                        val isFinished = it.step== OnboardingTrackStep.WORK_APP_BOTTOMSHEET_SCREEN_STEP.step
                        insertOnboardingTracker(it.apply { step++
                            started = !isFinished
                            finished = isFinished})
                    }
                }
            }
        }
    }

}