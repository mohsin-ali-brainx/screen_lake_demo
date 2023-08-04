package com.example.screen_lake.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.screen_lake.extensions.getInstalledApps
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.models.Behavior
import com.example.screen_lake.models.GenericSelectionModel
import com.example.screen_lake.models.getAppBehaviorList
import com.example.screen_lake.models.getOccupationList
import com.example.screen_lake.models.toAppInfoList
import com.example.screen_lake.models.toWorkAppInfoList
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OnboardingRepository @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun getInstalledAppList():ArrayList<Pair<ApplicationInfo,AppInfo>>{
        return getInstalledApps(context).toAppInfoList(context)
    }

    suspend fun getWorkAppList():ArrayList<Pair<ApplicationInfo,AppInfo>>{
        return getInstalledApps(context).toWorkAppInfoList(context)
    }

    suspend fun getAppBehaviourList():ArrayList<Behavior>{
        return getAppBehaviorList(context)
    }

    suspend fun getOccupation():ArrayList<GenericSelectionModel>{
        return getOccupationList(context)
    }

}