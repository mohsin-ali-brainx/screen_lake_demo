package com.example.screen_lake.ui.screens.onboarding.questions.occupation.useCase

import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.repository.OnboardingRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOccupationList @Inject constructor(
    private val repository: OnboardingRepository
)  {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val data= repository.getOccupation()
            emit(Resource.Success(data))
        }catch (ex:Exception){
            emit(Resource.Error(ex.toString()))
        }
    }
}