package com.example.screen_lake.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.screen_lake.R
import com.example.screen_lake.appUtils.Constants
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.presentation.viewmodels.SplashScreenUiEvents
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    onboardingTracker: OnboardingTracker,
    uiEvents : SharedFlow<SplashScreenUiEvents>,
){
    LaunchedEffect(key1 = true){
        uiEvents.collectLatest {
            when(it){
                is SplashScreenUiEvents.NavigateToOnboardingScreen->{
                    navigateToNextScreen(navHostController,it.route)
                }
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize(1f)
        .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .width(120.dp)
                .height(120.dp)
                .background(
                    color = MaterialTheme.colors.background,
                    shape = CircleShape
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(painter = painterResource(id = R.drawable.iv_rocket), contentDescription = Constants.StringConstants.EMPTY)
        }
    }
}

fun navigateToNextScreen(navHostController: NavHostController,route:String){
    navHostController.apply {
        popBackStack()
        navigate(route)
    }
}