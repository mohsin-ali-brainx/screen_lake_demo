package com.example.screen_lake.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.screen_lake.R
import com.example.screenlake.utils.Constants.StringConstants.EMPTY

@Composable
fun SplashScreen(
    navHostController: NavHostController
){
    Box(modifier = Modifier
        .fillMaxSize(1f)
        .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.iv_rocket),
            contentDescription =EMPTY
        )
    }
}