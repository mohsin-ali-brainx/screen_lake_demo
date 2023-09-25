package com.example.screen_lake.appUtils

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val siftDispatchers: SiftDispatchers)

enum class SiftDispatchers {
    Default,
    IO,
    MAIN
}