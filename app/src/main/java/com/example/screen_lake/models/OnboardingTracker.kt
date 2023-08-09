package com.example.screen_lake.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.screen_lake.navigation.Screen

@Entity(tableName = "onboarding")
data class OnboardingTracker(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("screen_id")
    var id: Int= 0,
    var started:Boolean=false,
    var step:Int=Screen.Splash.step,
    var finished:Boolean=false
)