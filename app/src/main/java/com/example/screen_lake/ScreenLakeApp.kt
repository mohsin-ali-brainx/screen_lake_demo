package com.example.screen_lake

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScreenLakeApp : Application(){
    //region Properties
    companion object {
        lateinit var appInstance: ScreenLakeApp
        fun getApplication(): ScreenLakeApp = appInstance
        fun getContext(): Context = appInstance.applicationContext
    }
    //endregion

    //region LifeCycle
    override fun onCreate() {
        appInstance = this
        super.onCreate()
    }
    //endregion
}