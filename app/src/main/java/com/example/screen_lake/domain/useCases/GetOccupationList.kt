package com.example.screen_lake.domain.useCases

import com.example.screen_lake.appUtils.Dispatcher
import com.example.screen_lake.appUtils.Resource
import com.example.screen_lake.appUtils.SiftDispatchers
import com.example.screen_lake.domain.repository.OnboardingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOccupationList @Inject constructor(
    private val repository: OnboardingRepository,
    @Dispatcher(SiftDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
)  {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val data= repository.getOccupation()
            emit(Resource.Success(data))
        }catch (ex:Exception){
            emit(Resource.Error(ex.toString()))
        }
    }.flowOn(ioDispatcher)
}