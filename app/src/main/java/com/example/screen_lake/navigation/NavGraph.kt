package com.example.screen_lake.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.screen_lake.ui.screens.onboaridng.AppListOnboardingScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenLakeNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.AppListOnboardingScreenRoute.route ){
       composable(route= Screen.AppListOnboardingScreenRoute.route) {backStackEntry->
          AppListOnboardingScreen(navController)
       }
    }
}