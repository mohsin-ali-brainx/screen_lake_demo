package com.example.screen_lake.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.example.screen_lake.domain.models.OnboardingTracker


enum class ScreenNavRoutes(val route: String){
    ONBOARDING("onboarding")
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
fun ScreenLakeNavGraph(navController: NavHostController,onboardingTracker: OnboardingTracker) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavRoutes.ONBOARDING.route
    ) {

        navigation(
            startDestination =OnboardingScreenRoutes.SPLASH.route ,
            route=ScreenNavRoutes.ONBOARDING.route,
        ){
            onboardingNavGraph(navController,onboardingTracker)
        }


    }
}