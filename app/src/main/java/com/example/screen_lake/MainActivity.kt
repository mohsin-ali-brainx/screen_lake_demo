package com.example.screen_lake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.screen_lake.enums.OnboardingTrackStep
import com.example.screen_lake.models.OnboardingTracker
import com.example.screen_lake.navigation.ScreenLakeNavGraph
import com.example.screen_lake.repository.OnboardingRepositoryImp
import com.example.screen_lake.ui.theme.ScreenLakeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: OnboardingRepositoryImp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var onboardingTracker by remember {
                mutableStateOf(OnboardingTracker())
            }
            ScreenLakeTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = true){
                    withContext(Dispatchers.IO){
                        onboardingTracker =  repository.getOnboardingTracker().firstOrNull()?: OnboardingTracker(step = OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step)
                    }
                }
                ScreenLakeNavGraph(navController = navController,onboardingTracker)
            }
        }
    }
}