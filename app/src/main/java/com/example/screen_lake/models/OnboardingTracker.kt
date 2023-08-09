package com.example.screen_lake.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.screen_lake.navigation.Screen

@Entity(tableName = "onboarding")
data class OnboardingTracker(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("screen_id")
    var id: String= Screen.Splash.route,
    var started:Boolean=false,
    var step:Int=0,
    var finished:Boolean=false
)