package com.example.screen_lake.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.screen_lake.extensions.getInstalledApps
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.models.toAppInfoList
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OnboardingRepository @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun getInstalledAppList():ArrayList<Pair<ApplicationInfo,AppInfo>>{
        return getInstalledApps().toAppInfoList(context)
    }

}