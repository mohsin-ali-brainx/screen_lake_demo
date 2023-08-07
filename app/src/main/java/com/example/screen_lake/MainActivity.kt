package com.example.screen_lake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.screen_lake.navigation.Screen
import com.example.screen_lake.navigation.ScreenLakeNavGraph
import com.example.screen_lake.repository.OnboardingRepository
import com.example.screen_lake.ui.theme.ScreenLakeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: OnboardingRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()
            var screenState by rememberSaveable() {
                mutableStateOf(Screen.Splash.route)
            }
            ScreenLakeTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = true){
                    withContext(Dispatchers.IO){
                        screenState = repository.getOnboardingTracker().firstOrNull()?.id?:Screen.Splash.route
                    }
                }
                ScreenLakeNavGraph(navController = navController,screenState)
            }
        }
    }
}