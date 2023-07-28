package com.example.screen_lake.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "onboarding")
data class OnboardingTracker(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("screen_id")
    var id: String,
    var started:Boolean=false,
    var step:Int=0,
    var finished:Boolean=false
)