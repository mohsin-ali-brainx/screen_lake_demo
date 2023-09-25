package com.example.screen_lake.di

import android.os.AsyncTask
import com.example.screen_lake.appUtils.Dispatcher
import com.example.screen_lake.appUtils.SiftDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher

// androidTest/projectPath/TestCoroutinesDispatchersModule.kt file

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatchersModule::class]
)
@Module
object TestCoroutinesDispatchersModule {

    @Dispatcher(SiftDispatchers.Default)
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher =
        AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    @Dispatcher(SiftDispatchers.IO)
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher =
        AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    @Dispatcher(SiftDispatchers.MAIN)
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}