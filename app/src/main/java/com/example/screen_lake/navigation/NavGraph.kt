package com.example.screen_lake.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.screen_lake.ui.screens.onboarding.appListOnboarding.AppListOnboardingScreen
import com.example.screen_lake.ui.screens.onboarding.behaviourOnboarding.BehaviorOnboardingScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenLakeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.AppListOnboardingScreenRoute.route
    ) {
        composable(route = Screen.AppListOnboardingScreenRoute.route) { backStackEntry ->
            AppListOnboardingScreen(navController)
        }
        composable(route=Screen.BehaviorOnboardingScreenRoute.route){
            BehaviorOnboardingScreen(navHostController = navController)
        }
    }
}