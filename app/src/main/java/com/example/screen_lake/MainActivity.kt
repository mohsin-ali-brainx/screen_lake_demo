package com.example.screen_lake

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.screen_lake.appUtils.enums.OnboardingTrackStep
import com.example.screen_lake.dataSource.repositoryImp.OnboardingRepositoryImp
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.presentation.navigation.ScreenLakeNavGraph
import com.example.screen_lake.presentation.theme.ScreenLakeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: OnboardingRepositoryImp
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var onboardingTracker by remember {
                mutableStateOf(OnboardingTracker())
            }
            val navController = rememberNavController()
            LaunchedEffect(key1 = true) {
                withContext(Dispatchers.IO) {
                    onboardingTracker = repository.getOnboardingTracker().firstOrNull()
                        ?: OnboardingTracker(step = OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step)
                }
            }
            ScreenLakeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    ScreenLakeNavGraph(navController = navController,onboardingTracker)
                }
            }

        }
    }
}