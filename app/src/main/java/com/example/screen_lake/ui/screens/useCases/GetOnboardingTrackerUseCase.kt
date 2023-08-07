package com.example.screen_lake.ui.screens.useCases

import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.repository.OnboardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOnboardingTrackerUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getOnboardingTracker()))
        }catch (ex:Exception){
            emit(Resource.Error(ex.toString()))
        }
    }.catch {
        emit(Resource.Error(it.localizedMessage))
    }.flowOn(Dispatchers.IO)
}