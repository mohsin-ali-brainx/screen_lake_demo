package com.example.screen_lake.ui.screens.onboarding.workAppsOnboarding.useCase

import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.repository.OnboardingRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkAppListUseCase @Inject constructor(
    private val repository: OnboardingRepository
)  {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val data= repository.getWorkAppList()
            emit(Resource.Success(data))
        }catch (ex:Exception){
            emit(Resource.Error(ex.toString()))
        }
    }
}