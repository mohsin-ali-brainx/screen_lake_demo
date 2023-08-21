package com.example.screen_lake.domain.useCases

import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppBehaviorUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val data= repository.getAppBehaviourList()
            emit(Resource.Success(data))
        }catch (ex:Exception){
            emit(Resource.Error(ex.toString()))
        }
    }
}