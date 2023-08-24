package com.example.screen_lake.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.presentation.screens.onboarding.appListOnBoardingScreen.AppListOnboardingScreen
import com.example.screen_lake.presentation.screens.onboarding.behaviourOnboarding.BehaviorOnboardingScreen
import com.example.screen_lake.presentation.screens.onboarding.workAppsOnboarding.WorkAppListOnboardingScreen
import com.example.screen_lake.presentation.screens.questions.occupation.OccupationQuestionnaireOnboardingScreen
import com.example.screen_lake.presentation.screens.splash.SplashScreen
import com.example.screen_lake.presentation.viewmodels.AppListOnBoardingViewModel
import com.example.screen_lake.presentation.viewmodels.BehaviorOnboardingViewModel
import com.example.screen_lake.presentation.viewmodels.OccupationQuestionnaireViewModel
import com.example.screen_lake.presentation.viewmodels.SplashViewModel
import com.example.screen_lake.presentation.viewmodels.WorkAppsOnboardingViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenLakeNavGraph(navController: NavHostController,onboardingTracker: OnboardingTracker) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SPLASH.route
    ) {
        composable(route = Screen.Splash.route,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            }) { backStackEntry ->
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController,onboardingTracker,viewModel.eventFlow)
        }

        composable(route = Screen.AppListOnboardingScreenRoute.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            },
        ) { backStackEntry ->
            val viewModel = hiltViewModel<AppListOnBoardingViewModel>()
            AppListOnboardingScreen(
                navController,
                onboardingTracker,
                viewModel.state,
                viewModel.eventFlow
            ) {
                viewModel.onEventUpdate(it)
            }
        }

        composable(route= Screen.BehaviorOnboardingScreenRoute.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            },
        ){
            val viewModel = hiltViewModel<BehaviorOnboardingViewModel>()
            BehaviorOnboardingScreen(
                navController,
                onboardingTracker,
                viewModel.state,
                viewModel.eventFlow
            ) {
                viewModel.onEventUpdate(it)
            }
        }

        composable(route= Screen.WorkAppsOnboardingScreenRoute.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            }
        ){
            val viewModel = hiltViewModel<WorkAppsOnboardingViewModel>()
            WorkAppListOnboardingScreen(
                navController,
                onboardingTracker,
                viewModel.state,
                viewModel.eventFlow
            ){
                viewModel.onEventUpdate(it)
            }
        }

        composable(route= Screen.OccupationScreenRoute.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700)
                )
            }
        ){
            val viewModel = hiltViewModel<OccupationQuestionnaireViewModel>()
            OccupationQuestionnaireOnboardingScreen(
                navController,
                viewModel.state,
                viewModel.eventFlow
            ){
                viewModel.onEventUpdate(it)
            }
        }
    }
}