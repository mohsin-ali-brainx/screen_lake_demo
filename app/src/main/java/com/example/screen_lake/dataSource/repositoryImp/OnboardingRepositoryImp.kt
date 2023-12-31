package com.example.screen_lake.dataSource.repositoryImp

import android.content.Context
import com.example.screen_lake.dataSource.db.repository.AppInfoRepository
import com.example.screen_lake.dataSource.db.repository.BehaviorRepository
import com.example.screen_lake.dataSource.db.repository.OnboardingTrackerRepository
import com.example.screen_lake.domain.repository.OnboardingRepository
import com.example.screen_lake.appUtils.extensions.getInstalledApps
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.domain.models.Behavior
import com.example.screen_lake.domain.models.GenericSelectionModel
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.domain.models.getAppBehaviorList
import com.example.screen_lake.domain.models.getOccupationList
import com.example.screen_lake.domain.models.getUpdatedBehaviorList
import com.example.screen_lake.domain.models.toAppList
import com.example.screen_lake.domain.models.toWorkAppList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnboardingRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val onboardingTrackerRepository: OnboardingTrackerRepository,
    private val appInfoRepository: AppInfoRepository,
    private val behaviorRepository: BehaviorRepository
) : OnboardingRepository {

    override suspend fun getInstalledAppListWithDistraction():ArrayList<AppInfo>{
        return getInstalledApps(context).toAppList(context,appInfoRepository.getAppInfoListWithDistractionItem())
    }

    override suspend fun getWorkAppList():ArrayList<AppInfo>{
        return getInstalledApps(context).toWorkAppList(context,appInfoRepository.getWorkAppInfoList())
    }

    override suspend fun getAllAppInfoList():List<AppInfo>{
        return appInfoRepository.getAppInfoList()
    }

    override suspend fun getAppInfoFromPackageName(packageManager: String): AppInfo?{
        return appInfoRepository.getAppInfoFromPackageName(packageManager)
    }

    override suspend fun getAppBehaviourList():ArrayList<Behavior>{
        return getAppBehaviorList(context).getUpdatedBehaviorList(behaviorRepository.getBehaviorList())
    }

    override suspend fun getAppBehaviorByName(name:String): Behavior?{
        return behaviorRepository.getBehaviourByName(name)
    }

    override suspend fun getOccupation():ArrayList<GenericSelectionModel>{
        return getOccupationList(context)
    }

    override suspend fun insertOnboardingTracker(onboardingTracker: OnboardingTracker){
        withContext(Dispatchers.IO){
            onboardingTrackerRepository.insertOnboardingTracker(onboardingTracker)
        }
    }

    override suspend fun getOnboardingTracker(): List<OnboardingTracker> {
        return onboardingTrackerRepository.getOnboardingTracker()
    }

    override suspend fun insertAppInfo(appInfo: AppInfo){
        appInfoRepository.insertAppInfo(appInfo)
    }

    override suspend fun insertBehaviorInfo(behavior: Behavior){
        behaviorRepository.upsertBehavior(behavior)
    }

}