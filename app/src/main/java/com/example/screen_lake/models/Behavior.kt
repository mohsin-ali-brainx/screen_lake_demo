package com.example.screen_lake.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.screen_lake.enums.BehaviorImportance

@Entity("behavior")
data class Behavior(
    @PrimaryKey(autoGenerate = false)
    var name:String,
    var importance:Int= BehaviorImportance.LOW.ordinal,
    var permissionGiven:Boolean=false,
)
