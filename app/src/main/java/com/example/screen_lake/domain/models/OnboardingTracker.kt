package com.example.screen_lake.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.screen_lake.presentation.navigation.OnboardingScreen

@Entity(tableName = "onboarding")
data class OnboardingTracker(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("screen_id")
    var id: Int= 0,
    var started:Boolean=false,
    var step:Int= OnboardingScreen.Splash.step,
    var finished:Boolean=false
)